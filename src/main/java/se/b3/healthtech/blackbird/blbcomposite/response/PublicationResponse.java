package se.b3.healthtech.blackbird.blbcomposite.response;

import lombok.Data;
import se.b3.healthtech.blackbird.blbcomposite.enums.CompositionType;
import se.b3.healthtech.blackbird.blbcomposite.enums.ContentType;

import java.time.LocalDateTime;

@Data
public class PublicationResponse {
    private String id;
    private String name;
    private String creator;
    private LocalDateTime created;
    private ContentType contentType;
    private CompositionType compositionType;

}
