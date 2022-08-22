package africa.semicolon.goodreads.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3ClientConfig {

    private final String accessKeyId;
    private final String accessKeySecret;


    public AmazonS3ClientConfig() {
        accessKeyId = System.getenv("AWSAccessKeyId");
        System.out.println("access----> "+accessKeyId);

        accessKeySecret = System.getenv("AWSSecretKey");
        System.out.println("secret----> "+accessKeySecret);
    }

    @Bean
    public AmazonS3 getAmazonS3Client(){
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.DEFAULT_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
     }
}
