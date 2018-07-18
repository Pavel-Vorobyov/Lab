package by.vorobyov.travelagency.annotation;

/**
 * Establishes which exceptions will be logged. In case THROWABLE all
 * exceptions will be logged.
 */
public enum ExceptionType {

    /**
     * {@link Error Error}         type, unchecked exception.
     */
    ERROR,

    /**
     * {@link Exception Exception} type, checked exception.
     */
    EXCEPTION,

    /**
     * {@link Throwable Throwable} type, checked exception. With this
     *                             type of exception suppose all exceptions
     *                             will be logged.
     */
    THROWABLE
}
