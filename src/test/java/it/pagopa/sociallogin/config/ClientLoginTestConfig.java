package it.pagopa.sociallogin.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import it.pagopa.sociallogin.config.ClientLoginConfig;

@TestConfiguration
@Import(ClientLoginConfig.class)
public class ClientLoginTestConfig {

}
