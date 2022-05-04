package se.b3.healthtech.blackbird.blbcomposite.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Slf4j
@Configuration
public class DynamoDbConfig {

    private static final String FULL_TABLE_NAME = "blb_composition";

    @Value("${AWS_DYNAMODB_ENDPOINT:http://dynamodb-local:8002}")
    private String amazonDynamoDBEndpoint;
    @Value("${AWS_ACCESS_KEY_ID:foo}")
    private String amazonAWSAccessKey;
    @Value("${AWS_SECRET_ACCESS_KEY:bar}")
    private String amazonAWSSecretKey;
    @Value("${AWS_REGION:eu-north-1}")
    private String awsRegion;

    @Bean(name = "dynamoDbClient")
    public DynamoDbClient dynamoDbClient() {
        log.info("Creating DynamoDbClient");
        Region region = Region.of(awsRegion);
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(amazonDynamoDBEndpoint))
                // The region is meaningless for local DynamoDb but required for client builder validation
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey)))
                .build();
    }

    @Bean(name = "dynamoDbEnhancedClient")
    @DependsOn({"dynamoDbClient"})
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        log.info("Creating DynamoDbEnhancedClient");
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient())
                .build();
    }

    @Bean(name = "publicationTable")
    @DependsOn({"dynamoDbEnhancedClient"})
    public DynamoDbTable<Publication> dynamoPublicationTable() {
        log.info("Initializing dynamoCompositeTable");
        return dynamoDbEnhancedClient().table(FULL_TABLE_NAME, TableSchema.fromBean(Publication.class));
    }

    @Bean(name = "containerTable")
    @DependsOn({"dynamoDbEnhancedClient"})
    public DynamoDbTable<Container> dynamoContainerTable() {
        log.info("Initializing dynamoContainerTable");
        return dynamoDbEnhancedClient().table(FULL_TABLE_NAME, TableSchema.fromBean(Container.class));
    }

    @Bean(name = "containerObjectTable")
    @DependsOn({"dynamoDbEnhancedClient"})
    public DynamoDbTable<ContainerObject> dynamoContainerObjectTable() {
        log.info("Initializing dynamoContainerObjectTable");
        return dynamoDbEnhancedClient().table(FULL_TABLE_NAME, TableSchema.fromBean(ContainerObject.class));
    }


}
