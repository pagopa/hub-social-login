package it.pagopa.sociallogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.sociallogin.exceptions.DomainNotAllowedException;
import it.pagopa.sociallogin.service.EmailValidationService;
import it.pagopa.sociallogin.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "login")
public class LoginController {

	@Autowired
	JwtTokenUtil JwtTokenUtil;
	
	@Autowired
	EmailValidationService emailValidationService;

	public static final String ISSU = "GOOGLE";

	@GetMapping("token")
	@ApiOperation(value = "", notes = "${swagger.api.login.getToken}")
	public Object getToken(
			@ApiParam("${swagger.model.login.authenticationToken}") OAuth2AuthenticationToken oauth2AuthenticationToken)
			throws DomainNotAllowedException {
			log.debug("[getToken] start");
			emailValidationService.isAllowedFromOauth2Token(oauth2AuthenticationToken);
			String token = JwtTokenUtil.createJWT(ISSU,
					JwtTokenUtil.createClaimFromGoogleOauth2AuthenticationToken(oauth2AuthenticationToken));
			log.debug("[getToken] end");
			return token;

	}

	@GetMapping("decode")
	@ApiOperation(value = "", notes = "${swagger.api.login.verify}")
	public Object verify(@ApiParam("${swagger.model.login.token}") @RequestParam String token) {
		String res = null;
		try {
			res = JwtTokenUtil.decodeJWT(token).toString();
		} catch (ExpiredJwtException e) {
			res = "token expired";
		}
		return res;
	}
	

	@GetMapping("/test")
	public String test(@RequestParam String email) throws DomainNotAllowedException {
		emailValidationService.isAllowed(email);
		return "true";
	}

}
