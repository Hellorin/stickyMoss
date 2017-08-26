package com.hellorin.stickyMoss.translation.services;

import com.hellorin.stickyMoss.translation.domain.Translation;
import com.hellorin.stickyMoss.translation.repositories.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by hellorin on 09.07.17.
 */
@Service
@Transactional
public class TranslationService {
    private static Logger logger = Logger.getLogger(TranslationService.class.getName());

    @Autowired
    private TranslationRepository translationRepository;

    private Pattern r = Pattern.compile("__[A-Za-z_]*__");

    public Translation createTranslation(final String key, final Map<String, String> translationByLanguage) {
        return translationRepository.save(new Translation(key, translationByLanguage));
    }

    public Optional<Translation> getTranslations(final String key) {
        Optional<Translation> translation = translationRepository.findByKey(key.toLowerCase());
        return translation;
    }

    public boolean translationsExistsForKey(final String key) {
        Optional<Translation> translationsForKey = translationRepository.findByKey(key);

        return translationsForKey.isPresent();
    }

    @Scheduled(initialDelayString="${translationService.update.initialDelay}",
            fixedDelayString="${translationService.update.fixedDelay}")
    protected void updateTranslations() throws Exception {
        File folder = new File(Paths.get(".", "translations").toString());

        if (folder.exists()) {
            Set<File> listFiles = listFilesInFolder(folder);

            for (File file : listFiles) {
                parseTranslationsAndSaveThemInFile(file);
            }
        } else {
            logger.warning("The folder 'translations' doesn't exist !");
        }
    }

    private Set<File> listFilesInFolder(final File folder) {
        return Arrays.asList(folder.listFiles() != null ? folder.listFiles() : new File[]{}).stream()
                .filter(fileOrFolder -> fileOrFolder.isFile())
                .filter(file -> file.getName().toLowerCase().endsWith(".csv"))
                .collect(Collectors.toSet());
    }

    private void parseTranslationsAndSaveThemInFile(final File file) throws Exception {
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] translationAttributes = line.trim().split(",");
            if (translationAttributes.length == 3) {
                String key = translationAttributes[0];
                String frTranslation  = translationAttributes[1];
                String enTranslation  = translationAttributes[1];

                Map<String, String> translations = new HashMap<>();
                translations.put("fr", frTranslation);
                translations.put("en", enTranslation);

                if (!translationsExistsForKey(key)) {
                    createTranslation(key, translations);
                } else {
                    logger.severe(String.format("A translation for the key %s already exists. Discarding this line", key));
                }

            } else {
                logger.warning("The following line have too much or not enough attributes to build a translation " + line);
            }
        }

        scanner.close();
    }
}
