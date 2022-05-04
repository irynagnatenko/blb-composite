package se.b3.healthtech.blackbird.blbcomposite.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.List;

@Slf4j
@Service
public class ContainerDbHandler {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Container> containerTable;

    public ContainerDbHandler(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTable<Container> containerTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.containerTable = containerTable;
    }

    public void insertContainers(List<Container> containerList){
        log.info("writeContainers");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Container.class).mappedTableResource(containerTable);
        containerList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }
}
