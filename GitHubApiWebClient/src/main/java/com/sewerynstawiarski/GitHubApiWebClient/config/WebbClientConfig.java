package com.sewerynstawiarski.GitHubApiWebClient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebbClientConfig implements WebClientCustomizer {
    private final String rootUrl;

    public WebbClientConfig(@Value("${webclient.rooturl}") String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
    webClientBuilder.baseUrl(rootUrl).defaultHeader("Accept", "application/vnd.github+json");
    }
}
