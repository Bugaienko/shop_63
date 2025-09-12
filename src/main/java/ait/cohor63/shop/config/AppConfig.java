package ait.cohor63.shop.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

@Configuration
public class AppConfig {

    @Bean
    public AmazonS3 doClient(DoProperties doProperties) {

        // Объект, который содержит ключи доступа
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                doProperties.getAccessKey(),
                doProperties.getSecretKey()
        );

        // Объект с информацией о подключении
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(
                        doProperties.getUrl(),
                        doProperties.getRegion()
                );
        // Сборка клиента для подключения к DO
        AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(endpointConfiguration);


        return clientBuilder.build();
    }
}
