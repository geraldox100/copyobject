package br.com.geraldoferraz.copyobject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface DestinationColumns
{

	DestinationColumn[] columns() default {};

}
