package br.com.geraldoferraz.copyobject.examples;

import br.com.geraldoferraz.copyobject.DestinationClass;

@DestinationClass(classe = Destiny.class)
public class ClassAnnotationSource {
	private String first;
	private String second;
	private String third;
	
	
	public ClassAnnotationSource() {
	}

	public ClassAnnotationSource(String first, String second, String third) {

		this.first = first;
		this.second = second;
		this.third = third;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}
	
	
}
