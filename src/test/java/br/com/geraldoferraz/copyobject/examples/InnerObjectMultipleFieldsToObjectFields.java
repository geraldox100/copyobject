package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;
import br.com.geraldoferraz.copyobject.DestinationColumn;
import br.com.geraldoferraz.copyobject.DestinationColumns;

@DestinationClass(classe = DestinyInnerObject.class)
public class InnerObjectMultipleFieldsToObjectFields {
	@DestinationColumns(columns = {
			@DestinationColumn(from = "first", to = "object.first"),
			@DestinationColumn(from = "second", to = "object.second") })
	private final String first;
	private final String second;

	public InnerObjectMultipleFieldsToObjectFields(String first, String second) {

		this.first = first;
		this.second = second;
	}

	public String getFirst() {

		return first;
	}

	public String getSecond() {

		return second;
	}
}
