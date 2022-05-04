package se.b3.healthtech.blackbird.blbcomposite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@DynamoDbBean
public class Publication extends BaseObject {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String versionKey;
    @NotNull
    private String uuid;
    @NotNull
    private String templateId;
    @NotNull
    private String title;
    @NotNull
    private String createdBy;
    @NotNull
    private long created;
    @NotNull
    private List<String> containersIdList;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getVersionKey() {
        return versionKey;
    }
}
