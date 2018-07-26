package com.example.demo;

import org.apache.http.client.HttpClient;
import org.springframework.cloud.config.server.environment.VaultEnvironmentProperties;
import org.springframework.cloud.config.server.environment.VaultEnvironmentRepositoryFactory;
import org.springframework.cloud.config.server.support.HttpClientSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AppConfiguration {
	@Bean
    @Primary
    public VaultEnvironmentRepositoryFactory.VaultRestTemplateFactory myRestTemplateFactory() throws Exception {
        return new MyVaultRestTemplateFactory();
    }

    class MyVaultRestTemplateFactory implements VaultEnvironmentRepositoryFactory.VaultRestTemplateFactory {

        @Override
        public RestTemplate build(VaultEnvironmentProperties environmentProperties) throws Exception {
            HttpClient httpClient = HttpClientSupport.builder(environmentProperties).build();
            RestTemplate rest =  new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
            DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
            defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
            rest.setUriTemplateHandler(defaultUriBuilderFactory);
            return rest;
        }
    }
}
