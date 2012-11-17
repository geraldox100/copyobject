package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;
import br.com.geraldoferraz.copyobject.DestinationColumn;
import br.com.geraldoferraz.copyobject.DestinationColumns;

@DestinationClass(classe = Destiny.class)
public class InnerObjectMultipleObjectFieldsToFields {
	@DestinationColumns(columns = {
			@DestinationColumn(from = "object.first", to = "first"),
			@DestinationColumn(from = "object.second", to = "second") })
	private final NoAnnotationSource object;

	public InnerObjectMultipleObjectFieldsToFields(NoAnnotationSource object) {

		this.object = object;
	}

	public NoAnnotationSource getObject() {

		return object;
	}
}
