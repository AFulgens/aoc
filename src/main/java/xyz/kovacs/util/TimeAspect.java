package xyz.kovacs.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static xyz.kovacs.util.AocUtils.getLogger;

@Aspect
public class TimeAspect {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface TrackTime {
		// marker annotation
	}

	@Around("@annotation(xyz.kovacs.util.TimeAspect.TrackTime) && execution(* *(..))")
	public Object executionTime(ProceedingJoinPoint point) throws Throwable {
		long start = System.nanoTime();
		Object obj = point.proceed();
		long end = System.nanoTime();

		getLogger(point.getSignature().getClass()).info("{}ms for {} in {}",
		          TimeUnit.NANOSECONDS.toMillis(end - start),
		          point.getSignature().getName(),
		          point.getSignature().getClass());

		return obj;
	}
}