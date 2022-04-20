package se.b3.healthtech.blackbird.blbcomposite.response;

import lombok.Data;
import se.b3.healthtech.blackbird.blbcomposite.enums.ContentType;

@Data
public class ContainerObjectResponse {
    private String id;
    private String name;
    private String creator;
    private int ordinal;
    private ContentType contentType;

}
