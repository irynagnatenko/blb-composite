package se.b3.healthtech.blackbird.blbcomposite.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

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
}
