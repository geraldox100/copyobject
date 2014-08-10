package br.com.geraldoferraz.copyobject.util;

public class ValidationUtil {

	public static void argumentValidation(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Argument cant be null");
		}
	}

	public static void stateValidation(Object o) {
		if (o == null) {
			throw new IllegalStateException("Invalid State");
		}
	}


}
