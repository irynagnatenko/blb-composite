package se.b3.healthtech.blackbird.blbcomposite.domain;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;

@Data
@DynamoDbBean
public class BaseObject {
    @NotNull
    private int versionNumber;
    @NotNull
    private int commitNumber;
    @NotNull
    private String createdBy;
    @NotNull
    private long created;

}
