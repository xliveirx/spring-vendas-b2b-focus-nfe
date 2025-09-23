package br.com.joao.app.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class NfeClientConfig {

    @Value("${focus.nfe.token}")
    private String token;

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            String credentials = token + ":";
            String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            requestTemplate.header("Authorization", "Basic " + base64);

        };
    }
}
