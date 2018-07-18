package by.vorobyov.travelagency.annotation;

import java.lang.annotation.*;

/**
 * The annotation which enables to log methods with different configuration.
 * value() - Enumeration which contains typ of the exception which supposed
 * to be logged. logMethodSignature() - enable to logging method signature.
 *
 * @see  ExceptionType ExceptionType
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Loggable {

     /**
      * Establishes which exceptions will be logged. As default value determines
      * {@link Throwable Throwable} type of exceptions which means all exceptions.
      *
      * @return  Exception type enumeration.
      * @see     ExceptionType ExceptionType
      */
     ExceptionType[] value() default ExceptionType.THROWABLE;

     /**
      * Enabling  to include logging of a method signature.
      *
      * @return   boolean value.
      */
     boolean logMethodSignature() default false;
}
