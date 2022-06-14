package se.b3.healthtech.blackbird.blbcomposite.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContainerObjectDbHandler {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<ContainerObject> containerObjectTable;

    public ContainerObjectDbHandler(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTable<ContainerObject> containerObjectTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.containerObjectTable = containerObjectTable;
    }

    public void insertContainerObjects(List<ContainerObject> containerObjectList){
        log.info("writeContainerObjects");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(ContainerObject.class).mappedTableResource(containerObjectTable);
        containerObjectList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }

    public List<ContainerObject> getContainerObjects(String partitionKey, String versionKey) {

        List<ContainerObject> containerObjectList = new ArrayList<>();

        QueryConditional queryConditional = QueryConditional.sortBeginsWith(Key.builder()
                .partitionValue(partitionKey)
                .sortValue(versionKey)
                .build());

        for (ContainerObject containerObject : containerObjectTable.query(queryConditional).items()) {
            containerObjectList.add(containerObject);
            log.info("ContainerObjectId: {}", containerObject.getUuid());
        }
        return containerObjectList;
    }
}
