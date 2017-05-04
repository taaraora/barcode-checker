package org.tsapko.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    //currently is used only for bloom filter filling
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        logger.error("error while bloomfilter loading application works incorrect!!!! {}", throwable);
        logger.error("throwable: {}", throwable.getMessage());
            logger.error("method: {}", method.getName());
            logger.error("objects: {}", objects);
    }
}
