package it.pagopa.sociallogin.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import it.pagopa.sociallogin.exceptions.DomainNotAllowedException;

@Service
public class EmailValidationService {

	@Value("${email.allowed.domains}")
	public String[] allowedDomains;

//	public void isAllowed(String emailStr) throws DomainNotAllowedException {
//		String allowedDomainsString = Arrays.toString(allowedDomains)
//				.replace("[", "")
//				.replace("]", "")
//				.replace(",","|");
//		Pattern VALID_EMAIL_DOMAIN_REGEX = Pattern.compile(("("+allowedDomainsString.trim()+")"), Pattern.CASE_INSENSITIVE);
//		Matcher matcher = VALID_EMAIL_DOMAIN_REGEX.matcher(emailStr);
//		if (!matcher.find())
//			throw new DomainNotAllowedException("domain not allowed");
//		
//	}
	public void isAllowedFromOauth2Token(OAuth2AuthenticationToken oauth2AuthenticationToken)
			throws DomainNotAllowedException {
		isAllowed(oauth2AuthenticationToken.getPrincipal().getAttribute("email"));
	}

	public void isAllowed(String emailStr) throws DomainNotAllowedException {
		boolean isFound = false;
		List<String> domains = Arrays.asList(allowedDomains);
		for (String domain : domains) {
			if (emailStr.contains(domain))
				isFound = true;
		}
		if (!isFound)
			throw new DomainNotAllowedException("domain not allowed");

	}
}
