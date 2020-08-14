
package br.com.crdd.common.exception;

import java.util.HashMap;
import java.util.List;

public class UnauthorizedAcessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HashMap<String, String> valueObject;

    public UnauthorizedAcessException() {
    }

    public UnauthorizedAcessException(String message) {
        super(message);
    }

    public UnauthorizedAcessException(String message, String errorType) {
        this(message);
        getValueObject().put(getNameException(), errorType);
    }

    public UnauthorizedAcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAcessException(Throwable cause) {
        super(cause);
    }

    @SuppressWarnings("rawtypes")
    public UnauthorizedAcessException(Class clazz, String message) {
        super(clazz.getName() + " : " + message);
    }

    public UnauthorizedAcessException(List<String> messages) {
        super(getMessages(messages));
    }

    public static String getMessages(List<String> messages) {
        String msg = "";
        for (String message : messages) {
            msg += message + "\n";
        }
        return msg;
    }

    public String getNameException() {
        return UnauthorizedAcessException.class.getSimpleName();
    }

    /**
     * Método escrito para que parametros possam ser devolvidos para o front se
     * houver alguma exceção
     *
     * @return
     */
    public HashMap<String, String> getValueObject() {
        if (valueObject == null) {
            valueObject = new HashMap<String, String>();
        }
        return valueObject;
    }

    public void setValueObject(HashMap<String, String> valueObject) {
        this.valueObject = valueObject;
    }

    public void setErrorType(String errorType) {
        getValueObject().put(getNameException(), errorType);
    }

    public void setMessageKey(String messageKey) {
        getValueObject().put("messageKey", messageKey);
    }
}