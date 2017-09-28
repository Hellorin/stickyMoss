package com.hellorin.stickyMoss.documents.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hellorin.stickyMoss.StickyMossDTO;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hellorin on 04.08.17.
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CVDTO.class, name = "cv"),
})
public abstract class DocumentDTO extends StickyMossDTO implements Resource {
    private Long id;

    private String name;

    private DocumentFileFormatDTO format;

    private byte[] content;

    private Date dateUploaded;

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean isReadable() {
        return true;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public String getDescription() {
        return "No description";
    }

    @Override
    public String getFilename() {
        return getName();
    }

    @Override
    public Resource createRelative(String s) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(getContent());
    }

    @Override
    public URL getURL() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getURI() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long contentLength() throws IOException {
        return getContent().length;
    }

    @Override
    public File getFile() throws IOException {
        File file = File.createTempFile("file" + UUID.randomUUID(), null);

        FileOutputStream fos = new FileOutputStream(file.getPath());
        fos.write(getContent());
        fos.close();

        return file;
    }

    @Override
    public long lastModified() throws IOException {
        return 0;
    }
}
