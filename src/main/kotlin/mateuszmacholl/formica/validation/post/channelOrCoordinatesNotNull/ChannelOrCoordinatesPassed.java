package mateuszmacholl.formica.validation.post.channelOrCoordinatesNotNull;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OneOfThemNotNullValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelOrCoordinatesPassed {
    String message() default "Pass channel or coordinates";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
    String channel();
	String coordinates();
}