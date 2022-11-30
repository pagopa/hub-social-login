package it.pagopa.sociallogin.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.pagopa.sociallogin.config.SwaggerConfig;
import it.pagopa.sociallogin.config.WebConfig;
import it.pagopa.sociallogin.utils.JwtTokenUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.constraints.Email;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { SwaggerConfig.class, WebConfig.class })
@EnableOpenApi
@EnableWebMvc
@ComponentScan({"it.pagopa.sociallogin.controller"," it.pagopa.sociallogin.utils","it.pagopa.sociallogin.service"})
@TestPropertySource(locations = "classpath:config/application.yml")
class SwaggerConfigTest {
	
//	@Autowired
//	JwtTokenUtil jwtTokenUtil;
//	
//	@Autowired
//	EmailValidationUtils emailValidationUtils;
	
	@Autowired
	WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void swaggerSpringPlugin() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs").accept(MediaType.APPLICATION_JSON))
				.andDo((result) -> {
					assertNotNull(result);
					assertNotNull(result.getResponse());
					final String content = result.getResponse().getContentAsString();
					assertFalse(content.isBlank());
					assertFalse(content.contains("${"), "Generated swagger contains placeholders");
					Object swagger = objectMapper.readValue(result.getResponse().getContentAsString(), Object.class);
					String formatted = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(swagger);
					Path basePath = Paths.get("src/main/resources/swagger/");
					Files.createDirectories(basePath);
					Files.write(basePath.resolve("api-docs.json"), formatted.getBytes());
				});
	}
}
