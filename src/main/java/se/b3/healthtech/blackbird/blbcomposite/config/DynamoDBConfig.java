package se.b3.healthtech.blackbird.blbcomposite.config;
/*
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableDynamoDBRepositories (basePackages = "")
public class DynamoDBConfig {
/*
    @Value("${AWS_DYNAMODB_ENDPOINT}")
    private String amazonDynamoDBEndpoint;

    @Value("${AWS_ACCESS_KEY_ID:}")
    private String amazonAWSAccessKey;

    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String amazonAWSSecretKey;

    @Value("${AWS_REGION:}")
    private String awsRegion;


    @Bean
    public DynamoDB dynamoDB() {
        return new DynamoDB(amazonDynamoDB());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey)))
                .build();
    }

*/

//}
