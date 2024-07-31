package com.sewerynstawiarski.GitHubApiWebClient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Value("${webclient.rooturl}")
    private String rootUrl;

    @Bean
    public WebClient gitHubWebClientConfig(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(rootUrl).defaultHeader("Accept", "application/vnd.github+json").build();
    }
}
