package se.b3.healthtech.blackbird.blbcomposite.response;

import lombok.Data;
import se.b3.healthtech.blackbird.blbcomposite.enums.ContentType;

import java.time.LocalDateTime;

@Data
public class ContainerResponse {
    private String id;
    private String name;
    private String creator;
    private LocalDateTime created;
    private int ordinal;
    private ContentType contentType;
}
