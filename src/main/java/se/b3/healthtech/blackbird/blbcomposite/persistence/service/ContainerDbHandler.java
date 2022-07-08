package se.b3.healthtech.blackbird.blbcomposite.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
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
public class ContainerDbHandler {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Container> containerTable;

    public ContainerDbHandler(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTable<Container> containerTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.containerTable = containerTable;
    }

    public void insertContainers(List<Container> containerList) {
        log.info("writeContainers");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Container.class).mappedTableResource(containerTable);
        containerList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }

    // partitionKey är ID:t på compositionen. Det är det som kommer från Swagger, dvs det som returneras av cretePublication metoden
    // versionKey måste skapas
    public List<Container> getContainers(String partitionKey, String versionKey) {
        log.info("partitionKey: {}", partitionKey);
        log.info("versionKey: {}", versionKey);
        List<Container> containerList = new ArrayList<>();

        QueryConditional queryConditional = QueryConditional.sortBeginsWith(Key.builder()
                .partitionValue(partitionKey)
                .sortValue(versionKey)
                .build());

        for (Container container : containerTable.query(queryConditional).items()) {
            containerList.add(container);
            log.info("ContainerId: {}", container.getUuid());
        }
        return containerList;
    }

    public void deleteContainers(List<Container> containerList) {
        log.info("deleteContainers");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Container.class).mappedTableResource(containerTable);
        containerList.forEach(subBatchBuilder::addDeleteItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }

}
