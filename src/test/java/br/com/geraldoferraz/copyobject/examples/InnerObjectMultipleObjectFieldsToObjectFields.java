package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;
import br.com.geraldoferraz.copyobject.DestinationColumn;
import br.com.geraldoferraz.copyobject.DestinationColumns;

@DestinationClass(classe = DestinyInnerObject.class)
public class InnerObjectMultipleObjectFieldsToObjectFields {

	@DestinationColumns(columns = {
			@DestinationColumn(from = "object.first", to = "object.first"),
			@DestinationColumn(from = "object.second", to = "object.second") })
	private final NoAnnotationSource object;

	public InnerObjectMultipleObjectFieldsToObjectFields(NoAnnotationSource object) {

		this.object = object;
	}

	public NoAnnotationSource getObject() {

		return object;
	}
}
