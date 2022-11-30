package it.pagopa.sociallogin.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;


	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.token.expire}")
	private String expire;
 

	public Map<String, Object> createClaimFromGoogleOauth2AuthenticationToken(
			OAuth2AuthenticationToken googleOauth2AuthenticationToken) {
		Map<String, Object> claims = new HashMap<>();
		Map<String, Object> attributes = googleOauth2AuthenticationToken.getPrincipal().getAttributes();
		claims.put("email", attributes.get("email"));
		claims.put("family_name", attributes.get("family_name"));
		//claims.put("fiscal_number", attributes.get(""));
		claims.put("name", attributes.get("given_name"));
		//claims.put("from_aa", Boolean.FALSE);
		claims.put("uid", attributes.get("sub"));
		//claims.put("level", attributes.get(""));

		return claims;
	}

 
	public String createJWT(String issuer, Map<String, Object> claims) {
		Long expireParsed = Long.parseLong(expire);
		Date exp = null;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// if it has been specified, let's add the expiration
		if (expireParsed > 0) {
			long expMillis = nowMillis + expireParsed;
			exp = new Date(expMillis);
			
		}
	 
		return Jwts.builder().setClaims(claims).setIssuedAt(now)
//				.setSubject(subject)
				.setIssuer(issuer).setExpiration(exp).signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(secret)).compact();
		// Builds the JWT and serializes it to a compact, URL-safe string
		
	}

	public  Claims decodeJWT(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.decode(secret)).parseClaimsJws(jwt).getBody();
		return claims;
	}
}