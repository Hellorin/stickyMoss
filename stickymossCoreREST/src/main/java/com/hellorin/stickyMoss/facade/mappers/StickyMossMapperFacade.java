package com.hellorin.stickyMoss.facade.mappers;

import com.hellorin.stickyMoss.StickyMossDTO;
import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import ma.glasnost.orika.MapperFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by hellorin on 28.09.17.
 */
public class StickyMossMapperFacade {
    private static StickyMossMapperFacade instance;

    private MapperFacade facade;

    private Map<Class, Map<Class, Class>> hierarchicalMapping = new HashMap<>();

    private Map<Class, Class> simpleMappingToDTO = new HashMap<>();

    private Map<Class, Class> simpleMappingToBusiness = new HashMap<>();

    private Function<Class, Optional<Class>> getSimpleMappingForClass = clazz ->
            simpleMappingToDTO.entrySet().stream()
                    .filter(kv -> kv.getKey().isAssignableFrom(clazz))
                    .map(kv -> kv.getValue())
                    .findFirst();

    private Function<Class, Optional<Map<Class, Class>>> getHierarchicalMappingForClass = clazz ->
            hierarchicalMapping.entrySet().stream()
                    .filter(kv -> kv.getKey().isAssignableFrom(clazz))
                    .map(kv -> kv.getValue())
                    .findFirst();

    public synchronized static StickyMossMapperFacade getInstance(final MapperFacade facade) {
        if (instance == null) {
            instance = new StickyMossMapperFacade(facade);
        }
        return instance;
    }

    private StickyMossMapperFacade(final MapperFacade facade) {
        this.facade = facade;

        initHierarchicalMapping();
    }

    private void initHierarchicalMapping() {
        Map<Class, Class> documentSpecializedMapping = new HashMap<>(1);
        documentSpecializedMapping.put(CV.class, CVDTO.class);
        hierarchicalMapping.put(Document.class, documentSpecializedMapping);
    }

    public <S, D> D map(S var1, Class<D> var2) {
        return facade.map(var1, var2);
    }

    private <S> boolean isHierarchicalMapping(S var1) {
        return getHierarchicalMappingForClass.apply(var1.getClass()).isPresent();
    }

    public <S> StickyMossDTO intelligentMappingToDTO(S var1) {
        return (StickyMossDTO) intelligentMap(var1);
    }


    private <S> Object intelligentMap(S var1) {
        if (isHierarchicalMapping(var1)) {
            return intelligentHierarchicalMap(var1);
        }
        throw new NullPointerException("The intelligent mapping doesn't have a specific mapping defined and cannot be done !");
    }

    private <S> Object intelligentHierarchicalMap(S var1) {
        Optional<Map<Class, Class>> opAbstractClassMapping = getHierarchicalMappingForClass.apply(var1.getClass());
        if (opAbstractClassMapping.isPresent() && !opAbstractClassMapping.get().isEmpty()) {
            Map<Class, Class> abstractClassMapping = opAbstractClassMapping.get();

            Class classMapping = abstractClassMapping.entrySet().stream()
                    .filter(kv -> kv.getKey().isAssignableFrom(var1.getClass()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElseThrow(() -> new NullPointerException("The intelligent mapping doesn't have a specific mapping defined and cannot be done !"));

            return facade.map(var1, classMapping);

        } else {
            throw new NullPointerException();
        }
    }

}
