package br.com.crdd.utils;


import br.com.crdd.common.exception.BusinessException;
import br.com.crdd.common.exception.UnauthorizedAcessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


@Component
public class BusinessValidation {

    @Autowired
    private Environment env;

    public String getMessage(String key) {
        return env.getProperty(key);
    }


    public void dateTimeRange(LocalDateTime start, LocalDateTime end, boolean required) {
        throwIf(required && (Objects.isNull(start) || Objects.isNull(end)),
                "É obrigatório preencher o campo %s");
        if ((Objects.nonNull(start) || Objects.nonNull(end))) {
            startIsAfterEnd(start, end, "inicial", "final");
        }
    }


    public void dateTimeRange(LocalDate start, LocalDate end, boolean required) {
        throwIf(required && (Objects.isNull(start) || Objects.isNull(end)),
                "É obrigatório preencher o campo %s");
        if ((Objects.nonNull(start) || Objects.nonNull(end))) {
            startIsAfterEnd(start, end, "inicial", "final");
        }
    }


    public void timeRange(LocalTime start, LocalTime end, boolean required) {
        throwIf(required && (Objects.isNull(start) || Objects.isNull(end)),
                "É obrigatório preencher o campo %s");
        if ((Objects.nonNull(start) || Objects.nonNull(end))) {
            startIsAfterEnd(start, end, "inicial", "final");
        }

    }

    void startIsAfterEnd(LocalDateTime start, LocalDateTime end, String labelStart, String labelEnd) {
        throwIf(start != null && end != null && start.isAfter(end), "A data %s não pode ser maior que a data %s",
                labelStart, labelEnd);
    }


    void startIsAfterEnd(LocalDate start, LocalDate end, String labelStart, String labelEnd) {
        throwIf(start != null && end != null && start.isAfter(end), "A data %s não pode ser maior que a data %s",
                labelStart, labelEnd);
    }


    public void startIsAfterEnd(LocalTime start, LocalTime end, String labelStart, String labelEnd) {
        throwIf(start != null && end != null && start.isAfter(end), "A hora %s não pode ser maior que a hora %s",
                labelStart, labelEnd);
    }

    public void required(Object bean, String propertyDescription) {
        throwIf(Objects.isNull(bean) || (bean instanceof String && StringUtils.isEmpty((String) bean)),
                "É obrigatório preencher o campo %s", propertyDescription);
    }


    public void throwIf(boolean isNotValid, String propertyOrMessage, Object... messageParts) {
        if (isNotValid) {
            String _message = resolveMessageParts(propertyOrMessage, messageParts);
            throw new BusinessException(_message);
        }
    }


    public void throwUnauthorizedIf(boolean isNotValid, String propertyOrMessage, Object... messageParts) {
        if (isNotValid) {
            String _message = resolveMessageParts(propertyOrMessage, messageParts);
            throw new UnauthorizedAcessException(_message);
        }
    }

    /**
     * Resolve a mensagem procurando-a no arquivo de mensagens.
     *
     * @param propertyOrMessage
     * @param messageParts
     * @return
     */
    private String resolveMessageParts(String propertyOrMessage, Object[] messageParts) {
        String _message = env.getProperty(propertyOrMessage);
        if (_message == null) {
            _message = propertyOrMessage;
        } else {
            _message = String.format(_message, messageParts);
        }
        return _message;
    }


    public void throwIfNull(Object object, String propertyOrMessage, Object... messageParts) {
        throwIf(Objects.isNull(object), propertyOrMessage, messageParts);
    }


    public void throwIfNoNull(Object object, String propertyOrMessage, Object... messageParts) {
        throwIf(Objects.nonNull(object), propertyOrMessage, messageParts);
    }


}
