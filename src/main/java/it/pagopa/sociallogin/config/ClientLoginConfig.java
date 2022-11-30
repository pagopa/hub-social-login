package it.pagopa.sociallogin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/client-login.properties")
public class ClientLoginConfig {
}
