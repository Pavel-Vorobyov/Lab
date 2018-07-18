package by.vorobyov.travelagency.aspect;

import by.vorobyov.travelagency.annotation.ExceptionType;
import by.vorobyov.travelagency.annotation.Loggable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logging aspect which logs throwable exceptions in methods are announced
 * with {@link Loggable Loggable} annotation.
 * <p>
 * Also logs all method in
 * {@link by.vorobyov.travelagency.service.Service Service} interface
 * which are not announced with Loggable annotation.
 */
@Aspect
@Component
public class LoggingAspect {
    static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs all throwable exceptions in methods from {@link by.vorobyov.travelagency.service.Service
     * Service} if they are announced without {@link Loggable Loggable} annotation.
     *
     * @param joinPoint  place of injecting aspect.
     * @param ex         exception which has been thrown.
     */
    @AfterThrowing(
            value = "execution(* by.vorobyov.travelagency.service.Service.*(..))"
            , throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        if (!methodSignature.getMethod().isAnnotationPresent(Loggable.class)) {
            logger.info(joinPoint.getTarget().getClass().toString(), ex);
        }
    }

    /**
     * Logs the method signature before method calling in case <code>logMethodSignature()</code> - true.
     *
     * @param joinPoint  place of injecting aspect.
     */
    @Before(value = "@annotation(by.vorobyov.travelagency.annotation.Loggable)")
    public void logMethodName(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = methodSignature.getMethod().getAnnotation(Loggable.class);

        if (loggable.logMethodSignature()) {
            logger.info(methodSignature.getMethod().toString());
        }
    }

    /**
     * Logs all types of exceptions which are enumerated in <code>value()</code>.
     *
     * @param joinPoint  place of injecting aspect.
     * @param ex         exception which has been thrown.
     */
    @AfterThrowing (
            pointcut = "@annotation(by.vorobyov.travelagency.annotation.Loggable))"
            , throwing = "ex")
    public void logByAnnotation(JoinPoint joinPoint, Throwable ex) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = methodSignature.getMethod().getAnnotation(Loggable.class);

        if (exceptionTypeIsPresent(loggable, ExceptionType.THROWABLE)) {
            logger.info(joinPoint.getTarget().getClass().toString(), ex);
        } else {

            for (int i = 0; i< loggable.value().length; i++) {
                switch (loggable.value()[i]){
                    case EXCEPTION:
                        if (ex instanceof Exception) {
                            logger.info(joinPoint.getTarget().getClass().toString(), ex);
                        }
                        break;
                    case ERROR:
                        if (ex instanceof Error) {
                            logger.info(joinPoint.getTarget().getClass().toString(), ex);
                        }
                        break;
                }
            }
        }
    }

    /**
     * @param loggable       currant Loggable annotation.
     * @param exceptionType  type of the exception which have to be verified.
     * @return               true in case current type of Exception is present in
     *                       Loggable annotation. false - in other cases.
     *
     * @see Loggable
     */
    private boolean exceptionTypeIsPresent(Loggable loggable, ExceptionType exceptionType) {
        for (int i = 0; i< loggable.value().length; i++) {
            if (loggable.value()[i].equals(exceptionType)) {
                return true;
            }
        }
        return false;
    }
}
