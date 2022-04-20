package se.b3.healthtech.blackbird.blbcomposite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import se.b3.healthtech.blackbird.blbcomposite.enums.ContentType;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import javax.validation.constraints.NotNull;

@Data
@DynamoDbBean
public class ContainerObject extends BaseObject{
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String versionKey;
    @NotNull
    private String uuid;
    @NotNull
    private int ordinal;
    @NotNull
    private ContentType contentType;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
