package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;

@DestinationClass(classe = Destiny.class)
public class ClassAnnotationSource {
	public String first;
	public String second;
	public String third;

	public ClassAnnotationSource(String first, String second, String third) {

		this.first = first;
		this.second = second;
		this.third = third;
	}
}
