package se.b3.healthtech.blackbird.blbcomposite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import se.b3.healthtech.blackbird.blbcomposite.enums.PublicationType;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import javax.validation.constraints.NotNull;

@Data
@DynamoDbBean
public class Publication extends BaseObject implements Cloneable{
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
    private long created;
    @NotNull
    private String createdBy;
    @NotNull
    private PublicationType publicationType;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getVersionKey() {
        return versionKey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
