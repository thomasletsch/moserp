package org.moserp.common.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

//@Controller
public class ExceptionHandlingController {

    Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

//    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
        ResponseEntity<Void> responseEntity = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
