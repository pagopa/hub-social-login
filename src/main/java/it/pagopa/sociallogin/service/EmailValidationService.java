package it.pagopa.sociallogin.service;

import it.pagopa.sociallogin.exceptions.DomainNotAllowedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailValidationService {

	@Value("${email.allowed.domains}")
	public String[] allowedDomains;

	@Value("${email.allowed.emails}")
	public String[] allowedEmails;

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
		boolean isNotFoundMail = true;
		List<String> domains = Arrays.asList(allowedDomains);
		List<String> emails = Arrays.asList(allowedEmails);
		for (String domain : domains) {
			if (emailStr.endsWith(domain))
				isFound = true;
		}
		for (String email : emails) {
			if (!emailStr.equals(email))
				isNotFoundMail = false;
		}
		if (!isFound || !isNotFoundMail)
			throw new DomainNotAllowedException("domain not allowed");

	}
}
