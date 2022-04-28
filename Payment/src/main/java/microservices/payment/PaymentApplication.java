package microservices.payment;

//import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentApplication {

   // @Bean
   // public Sampler defaultSampler(){return Sampler.ALWAYS_SAMPLE;}

    public static void main(String[] args){
        SpringApplication.run(PaymentApplication.class, args);
    }

    @RestController
    @RequestMapping("/config")
    public static class ConfigController {

        @Value("${server.port}")
        private String port;

        @Value("${spring.application.name}")
        private String name;

        @GetMapping
        public String ConfigController(Principal principal) {
            return name+":"+port;
        }
    }

}
