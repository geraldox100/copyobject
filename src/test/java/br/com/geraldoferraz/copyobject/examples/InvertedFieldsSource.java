package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;
import br.com.geraldoferraz.copyobject.DestinationColumn;

@DestinationClass(classe = Destiny.class)
public class InvertedFieldsSource {

	@DestinationColumn(to = "second")
	public String first;
	@DestinationColumn(to = "first")
	public String second;
	public String otherr;

	public InvertedFieldsSource(String first, String second, String other) {

		this.first = first;
		this.second = second;
		this.otherr = other;
	}
}
