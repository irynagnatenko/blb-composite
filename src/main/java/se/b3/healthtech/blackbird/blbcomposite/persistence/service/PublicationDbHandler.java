package se.b3.healthtech.blackbird.blbcomposite.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.List;

@Slf4j
@Service
public class PublicationDbHandler {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Publication> publicationTable;


    public PublicationDbHandler(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTable<Publication> publicationTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.publicationTable = publicationTable;
    }

    public void insertPublications(List<Publication> publicationList){
        log.info("writePublications");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Publication.class).mappedTableResource(publicationTable);
        publicationList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }

}