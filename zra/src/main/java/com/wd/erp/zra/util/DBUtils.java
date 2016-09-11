package com.wd.erp.zra.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DBUtils {
	public static <T> T parseObj(ResultSet rs, Class<T> objClass) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		T obj = objClass.newInstance();
		Field[] fields = objClass.getDeclaredFields();

		for (int i = 0; i < fields.length; ++i) {
			Field field = fields[i];
			JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
			String fileCollName = "";
			if (jsonProperty != null) {
				fileCollName = jsonProperty.value();
			}
			if (fileCollName == null) {
				fileCollName = field.getName();
			}

			byte[] bFieldName = field.getName().getBytes();

			bFieldName[0] = (byte) ((char) bFieldName[0] - 'a' + 'A');
			;
			String filedName = new String(bFieldName);

			String setMethodName = "set" + filedName;
			Method setMethod = getMethodByName(setMethodName, objClass);// objClass.getMethod(setMethodName,
																		// String.class);
			if (setMethod == null)
				return null;
			Class<?> parameterType = setMethod.getParameterTypes()[0];
			String parameterTypeName = parameterType.getName();
			//System.out.println("type = " + parameterTypeName);

			Object fieldValue = null;
			try {
				fieldValue = getValue(parameterTypeName, rs, fileCollName);
			} catch (SQLException e) {
				// e.printStackTrace();
			}
			if (fieldValue == null)
				continue;

			setMethod.invoke(obj, fieldValue);
		}
		return obj;
	}
	
	public static Method getMethodByName(String name, Class<?> objClass){
		Method[] methods = objClass.getMethods();
		for(int i = 0 ; i < methods.length; ++i){
			Method method = methods[i];
			if(method.getName().equals(name))
				return method;
		}
		return null;		
	}
	
	public static Object getValue(String type, ResultSet rs, String fieldName) throws SQLException{
		if(type.equals("java.lang.String"))
			return rs.getString(fieldName);
		else if(type.equals("int"))
			return rs.getInt(fieldName);
		return null;				
	}
}
