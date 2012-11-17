package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;
import br.com.geraldoferraz.copyobject.DestinationColumn;

@DestinationClass(classe = DestinyInnerObject.class)
public class InnerObjectSource {
	@DestinationColumn(from = "object.first", to = "object.first")
	private final NoAnnotationSource object;

	public InnerObjectSource(NoAnnotationSource object) {

		this.object = object;
	}

	public NoAnnotationSource getObject() {

		return object;
	}
}
