package br.com.geraldoferraz.copyobject;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.geraldoferraz.copyobject.examples.ClassAnnotationSource;
import br.com.geraldoferraz.copyobject.examples.Destiny;
import br.com.geraldoferraz.copyobject.examples.DestinyInnerObject;
import br.com.geraldoferraz.copyobject.examples.FieldAnnotationSource;
import br.com.geraldoferraz.copyobject.examples.InnerObjectMultipleFieldsToObjectFields;
import br.com.geraldoferraz.copyobject.examples.InnerObjectMultipleObjectFieldsToFields;
import br.com.geraldoferraz.copyobject.examples.InnerObjectMultipleObjectFieldsToObjectFields;
import br.com.geraldoferraz.copyobject.examples.InnerObjectSource;
import br.com.geraldoferraz.copyobject.examples.InvertedFieldsSource;
import br.com.geraldoferraz.copyobject.examples.NoAnnotationSource;

/**
 * @author Geraldo Ferraz
 */
public class CopierTest {

	private Copier copier;

	@Before
	public void beforeCopierTest() {
		copier = new Copier();
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenPassingNullToFromAnnotedObject() {
		copier.from(null);
	}

	@Test(expected = RuntimeException.class)
	public void testParseSemAnnotacao() {
		copier.from(new NoAnnotationSource("BB", "123321")).doCopy();
	}

	@Test
	public void testParseAtributosComMesmoNome() {

		ClassAnnotationSource de = new ClassAnnotationSource("BB", "123321", "M");
		Destiny para = copier.from(de).doCopy();

		assertEquals("BB", para.getFirst());
		assertEquals("123321", para.getSecond());
		assertEquals(null, para.getOther());
	}

	@Test
	public void testParseAtributosComAnnotacaoDefault() {

		FieldAnnotationSource de = new FieldAnnotationSource("BB", "123321", "M");
		Destiny para = copier.from(de).doCopy();

		assertEquals("BB", para.getFirst());
		assertEquals("123321", para.getSecond());
		assertEquals(null, para.getOther());
	}

	@Test
	public void testParseAtributosComAnnotacaoParaPreenchido() {

		InvertedFieldsSource de = new InvertedFieldsSource("BB", "123321", "M");
		Destiny para = copier.from(de).doCopy();

		assertEquals("123321", para.getFirst());
		assertEquals("BB", para.getSecond());
		assertEquals(null, para.getOther());
	}

	@Test
	public void testParseComObjetosInner() {

		InnerObjectSource de = new InnerObjectSource(new NoAnnotationSource("BB", "123321"));
		DestinyInnerObject para = copier.from(de).doCopy();

		assertEquals("BB", para.getObject().getFirst());
	}

	@Test
	public void testParseComObjetosInnerEVariosAtributos() {

		InnerObjectMultipleObjectFieldsToObjectFields de = new InnerObjectMultipleObjectFieldsToObjectFields(new NoAnnotationSource("BB", "123321"));
		DestinyInnerObject para = copier.from(de).doCopy();
		assertEquals("BB", para.getObject().getFirst());
		assertEquals("123321", para.getObject().getSecond());
	}

	@Test
	public void testParseComObjetosDeInnerEParaOuter() {

		InnerObjectMultipleObjectFieldsToFields de = new InnerObjectMultipleObjectFieldsToFields(new NoAnnotationSource("BB", "123321"));
		Destiny para = copier.from(de).doCopy();
		assertEquals("BB", para.getFirst());
		assertEquals("123321", para.getSecond());
	}

	@Test
	public void testParseComObjetosParaInnerEDeOuter() {

		InnerObjectMultipleFieldsToObjectFields de = new InnerObjectMultipleFieldsToObjectFields("BB", "123321");
		DestinyInnerObject para = copier.from(de).doCopy();
		assertEquals("BB", para.getObject().getFirst());
		assertEquals("123321", para.getObject().getSecond());
	}
	
	@Test
	public void testParseObjetoSimples() {
		
		ClassAnnotationSource de = new ClassAnnotationSource("BB", "123321",null);
		ClassAnnotationSource para = copier.from(de).doCopy(ClassAnnotationSource.class);
		
		assertEquals("BB", para.getFirst());
		assertEquals("123321", para.getSecond());
		assertEquals(null, para.getThird());
	}

}