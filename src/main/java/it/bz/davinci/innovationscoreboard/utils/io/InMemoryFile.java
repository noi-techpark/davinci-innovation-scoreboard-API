package it.bz.davinci.innovationscoreboard.utils.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InMemoryFile {
    private byte[] content;
    private String filename;

    /*
    HTTP content type, can be taken from a MediaType class
     */
    private String contentType;
}
