package br.com.geraldoferraz.copyobject;

import static br.com.geraldoferraz.copyobject.util.ValidationUtil.argumentValidation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

;

public class Copier {

	private Object objectToCopy;
	String[] tipos = { "java.lang.Integer", "", "java.lang.Short", "java.lang.Character", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean" };

	public Copier from(Object objectToCopy) {

		argumentValidation(objectToCopy);

		this.objectToCopy = objectToCopy;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T doCopy() {
		checkIfClassHasAnnotation(objectToCopy);
		Class<T> classe = (Class<T>) objectToCopy.getClass().getAnnotation(DestinationClass.class).classe();
		return doCopy(classe);
	}

	
	public <T> T doCopy(Class<T> to) {
		try {
			T destination = to.newInstance();
			
			Field[] objectFields = objectToCopy.getClass().getDeclaredFields();

			for (Field field : objectFields) {
				if (checkIfFieldHasAnnotation(field)) {
					transferValueByAnnotation(objectToCopy, destination, field);
				} else {
					transferValue(objectToCopy, destination, field);
				}
			}
			return destination;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean checkIfFieldHasAnnotation(Field field) {

		return field.isAnnotationPresent(DestinationColumn.class) || field.isAnnotationPresent(DestinationColumns.class);
	}

	private void checkIfClassHasAnnotation(Object object) {

		if (!object.getClass().isAnnotationPresent(DestinationClass.class)) {
			throw new RuntimeException("Should be annoted with @DestinationClass");
		}
	}
	
	private <T> void transferValueByAnnotation(Object object, T destination, Field field) throws NoSuchFieldException, IllegalAccessException, SecurityException, IllegalArgumentException, InstantiationException {

		if (field.isAnnotationPresent(DestinationColumn.class)) {
			DestinationColumn column = field.getAnnotation(DestinationColumn.class);
			tratarAnotacao(column.from(), column.to(), object, destination, field);
		} else {
			for (DestinationColumn column : field.getAnnotation(DestinationColumns.class).columns()) {
				tratarAnotacao(column.from(), column.to(), object, destination, field);
			}
		}
	}

	private <T> void transferValue(Object object, T destination, Field field) throws IllegalAccessException {

		try {
			Field destinationField = destination.getClass().getDeclaredField(field.getName());
			destinationField.setAccessible(true);
			field.setAccessible(true);
			if (!Modifier.toString(destinationField.getModifiers()).contains("final")) {
				destinationField.set(destination, field.get(object));
			}
		} catch (NoSuchFieldException e) {

		}
	}

	

	private <T> void tratarAnotacao(String from, String to, Object object, T destination, Field field) throws IllegalAccessException, NoSuchFieldException, InstantiationException {

		Object valor = tratarDe(object, from);
		tratarPara(object, destination, field, to, valor);
	}

	private <T> void tratarPara(Object objectToCopyPassado, T historico, Field attributoDoObjetoPassado, String para, Object valor) throws NoSuchFieldException, IllegalAccessException,
			InstantiationException {

		Field atributoDoHistorico = null;
		if (!"".equals(para)) {
			if (para.contains(".")) {
				String[] paras = para.split("\\.");
				montarArvoreDeObjetos(historico, paras, 0, valor);
			} else {
				atributoDoHistorico = historico.getClass().getDeclaredField(para);
			}
		} else {
			atributoDoHistorico = historico.getClass().getDeclaredField(attributoDoObjetoPassado.getName());
		}

		if (atributoDoHistorico != null) {
			atributoDoHistorico.setAccessible(true);
			attributoDoObjetoPassado.setAccessible(true);
			if (!Modifier.toString(atributoDoHistorico.getModifiers()).contains("final")) {
				try {
					atributoDoHistorico.set(historico, attributoDoObjetoPassado.get(objectToCopyPassado));
				} catch (RuntimeException e) {
					atributoDoHistorico.set(historico, valor);
				}
			}
		}
	}

	private Object tratarDe(Object objectToCopyPassado, String de) throws IllegalAccessException, NoSuchFieldException {

		Object valor = null;
		if (!"".equals(de)) {
			if (de.contains(".")) {
				String[] des = de.split("\\.");
				valor = buscarValorRecursivamente(objectToCopyPassado, des, 0);
			} else {
				Field field = objectToCopyPassado.getClass().getDeclaredField(de);
				field.setAccessible(true);
				valor = field.get(objectToCopyPassado);
			}

		}
		return valor;
	}

	private Object montarArvoreDeObjetos(Object obj, String[] paras, int index, Object valor) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		Field field = obj.getClass().getDeclaredField(paras[index++]);
		field.setAccessible(true);
		Object object = field.get(obj);
		if (object == null) {
			if (notWrapper(field.getType())) {
				object = field.getType().newInstance();
			} else {
				object = getWrapper(field.getType());
			}
		}
		if (!Modifier.toString(field.getModifiers()).contains("final")) {
			field.set(obj, object);
		}

		if (index < paras.length) {
			return montarArvoreDeObjetos(object, paras, index, valor);
		} else {
			if (!Modifier.toString(field.getModifiers()).contains("final")) {
				field.set(obj, valor);
			}
			return null;
		}
	}

	

	private boolean notWrapper(Class<?> type) {

		for (String tipo : tipos) {
			if (type.getName().equals(tipo)) {
				return false;
			}
		}
		return true;
	}

	private Object getWrapper(Class<?> type) {

		if ("java.lang.Integer".equals(type.getName()) || "java.lang.Byte".equals(type.getName()) || "java.lang.Short".equals(type.getName()) || "java.lang.Long".equals(type.getName())) {
			return 0;
		}
		if ("java.lang.Character".equals(type.getName())) {
			return Character.valueOf(' ');
		}
		if ("java.lang.Float".equals(type.getName()) || "java.lang.Double".equals(type.getName())) {
			return 0.0;
		}
		if ("java.lang.Float".equals(type.getName())) {
			return false;
		}

		return null;
	}

	private Object buscarValorRecursivamente(Object obj, String[] paras, int index) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {

		Field field = obj.getClass().getDeclaredField(paras[index++]);
		field.setAccessible(true);
		Object object = field.get(obj);
		if (index < paras.length) {
			return buscarValorRecursivamente(object, paras, index);
		} else {
			return object;
		}
	}
	
	public static void main(String[] args) {
		int num1 = 0;
		int num2 = 1;
		
		for (int i = 0; i < 30; i++) {
			System.out.println(num1+num2);
			num1 = num1+num2;
			num2 = num1-num2;
		}
	}

	

}
