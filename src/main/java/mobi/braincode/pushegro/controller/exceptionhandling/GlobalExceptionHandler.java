package mobi.braincode.pushegro.controller.exceptionhandling;

import mobi.braincode.pushegro.controller.RegistrationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleEverything(Exception e) {
        log.error("Error occurred!: Cause:", e);

        return String.format("Server error: Cause %s", e.getLocalizedMessage());
    }
}
