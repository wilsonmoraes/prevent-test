

package br.com.crdd.web.exception;

import br.com.crdd.common.exception.BusinessException;
import br.com.crdd.common.exception.UnauthorizedAcessException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(UnauthorizedAcessException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BodyErrorMessage handleUnauthorizedAcessException(UnauthorizedAcessException e, WebRequest request) {
        return new BodyErrorMessage(HttpStatus.UNAUTHORIZED.value(), e, request.getContextPath());
    }


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public BodyErrorMessage handleBusinessException(BusinessException e) {
        return new BodyErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), e);
    }


    class BodyErrorMessage {
        @Getter
        @Setter
        private Integer status;
        @Getter
        @Setter
        private String error;
        @Getter
        @Setter
        private String exception;
        @Getter
        @Setter
        private String message;
        @Getter
        @Setter
        private String path;
        @Getter
        @Setter
        private Timestamp timestamp;

        public BodyErrorMessage(Integer status, RuntimeException exception) {
            this(status, exception, null);
        }

        public BodyErrorMessage(Integer status, RuntimeException exception, String path) {
            this.error = exception.getMessage();
            this.exception = exception.getClass().getSimpleName();
            this.message = exception.getMessage();
            this.status = status;
            this.timestamp = Timestamp.valueOf(LocalDateTime.now());
            this.path = path;
        }
    }
}