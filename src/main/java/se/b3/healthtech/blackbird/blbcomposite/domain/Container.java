package se.b3.healthtech.blackbird.blbcomposite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import se.b3.healthtech.blackbird.blbcomposite.enums.ContentType;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@DynamoDbBean
public class Container extends BaseObject implements Cloneable {
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
    @NotNull
    private List<String> containerObjectsList;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Container clone = (Container) super.clone();
        List<String> cloneContainerObjectList = new ArrayList<>();
        if (!(this.getContainerObjectsList() == null) && !this.getContainerObjectsList().isEmpty()) {
            cloneContainerObjectList.addAll(this.getContainerObjectsList());
            clone.setContainerObjectsList(cloneContainerObjectList);
        }
        return clone;
    }


}
