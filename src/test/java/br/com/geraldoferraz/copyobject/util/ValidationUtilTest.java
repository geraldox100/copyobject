package br.com.geraldoferraz.copyobject.util;

import org.junit.Test;

import br.com.geraldoferraz.copyobjec.utilt.ValidationUtil;

public class ValidationUtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void whenUseArgumentValidationPassingNullArgument() {
		ValidationUtil.argumentValidation(null);
	}

	@Test(expected = IllegalStateException.class)
	public void whenUseStateValidationPassingNullArgument() {
		ValidationUtil.stateValidation(null);
	}

}
