package it.pagopa.sociallogin.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.pagopa.sociallogin.config.ClientLoginTestConfig;
import it.pagopa.sociallogin.config.WebTestConfig;
import it.pagopa.sociallogin.controller.LoginController;
import it.pagopa.sociallogin.service.EmailValidationService;
import it.pagopa.sociallogin.utils.JwtTokenUtil;

@WebMvcTest(value = { LoginController.class }, excludeAutoConfiguration = { OAuth2ClientAutoConfiguration.class,
		OAuth2ClientConfigurer.class, SecurityAutoConfiguration.class })
@ContextConfiguration(classes = { LoginController.class, WebTestConfig.class,OAuth2AuthenticationToken.class, ClientLoginTestConfig.class, EmailValidationService.class})
class LoginControllerTest {

	private static final String BASE_URL = "/login";

	@MockBean
	JwtTokenUtil jwtTokenUtilMock;

	@Autowired
	protected MockMvc mvc;
	
	@MockBean
	EmailValidationService emailValidationService;
	
	@MockBean
	OAuth2AuthenticationToken auth2AuthenticationToken;

	@Test
	void getToken_exists() throws Exception {
		// given
		when(jwtTokenUtilMock.createJWT(anyString(), Mockito.anyMap())).thenAnswer(invocationOnMock -> {
			String jwt = invocationOnMock.getArgument(0, String.class);
			return jwt;
		});
		
		 
		
		Mockito.doNothing().when(emailValidationService).isAllowedFromOauth2Token(any());
		
		// when
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/token")
				.contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful()).andReturn();
		// then
		assertNotNull(result);
	}

}
