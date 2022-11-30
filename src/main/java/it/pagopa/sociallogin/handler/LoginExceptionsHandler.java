package it.pagopa.sociallogin.handler;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import it.pagopa.selfcare.commons.web.model.Problem;
import it.pagopa.selfcare.commons.web.model.mapper.ProblemMapper;
import it.pagopa.sociallogin.controller.LoginController;
import it.pagopa.sociallogin.exceptions.DomainNotAllowedException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(assignableTypes = LoginController.class)
@Slf4j
public class LoginExceptionsHandler {

	@ExceptionHandler({ DomainNotAllowedException.class })
	ResponseEntity<Problem> handleResourceNotFoundException(DomainNotAllowedException e) {
		log.warn(e.toString());
		return ProblemMapper.toResponseEntity(new Problem(BAD_REQUEST, e.getMessage()));
	}
}
