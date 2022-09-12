package com.project.indytskyi.tripsservice.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secret}")
    private String secret;
    @Value("${region}")
    private String region;

    @Bean
    public AmazonS3 s3() {

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(initializationCredentials())
                .build();
    }

    public AWSStaticCredentialsProvider initializationCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secret));

    }

}
