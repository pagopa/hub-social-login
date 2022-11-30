package it.pagopa.sociallogin.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import it.pagopa.sociallogin.config.WebConfig;

@TestConfiguration
@Import(WebConfig.class)
public class WebTestConfig {
}