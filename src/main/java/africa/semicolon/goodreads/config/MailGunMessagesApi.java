//package africa.semicolon.goodreads.config;
//
//import com.mailgun.api.v3.MailgunMessagesApi;
//import com.mailgun.client.MailgunClient;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//
//@Configuration
//@AllArgsConstructor
//public class MailGunMessagesApi {
//
//    private String PRIVATE_API;
//
//
//    public MailGunMessagesApi() {
//        PRIVATE_API = System.getenv("MAILGUN_PRIVATE_KEY");
//    }
//
//    @Bean
//    public MailgunMessagesApi mailgunMessagesApi(){
//        return MailgunClient.config(PRIVATE_API)
//                .createApi(MailgunMessagesApi.class);
//    }
//}
