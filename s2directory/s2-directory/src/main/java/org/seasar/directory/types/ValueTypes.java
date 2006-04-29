/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.directory.types;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 値の型の集合クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ValueTypes {
	public static final ValueType STRING = new StringType();
	public static final ValueType LIST = new ListType();
	// public static final ValueType SHORT = new ShortType();
	// public static final ValueType INTEGER = new IntegerType();
	// public static final ValueType LONG = new LongType();
	// public static final ValueType FLOAT = new FloatType();
	// public static final ValueType DOUBLE = new DoubleType();
	// public static final ValueType BIGDECIMAL = new BigDecimalType();
	// public static final ValueType TIME = new TimeType();
	// public static final ValueType SQLDATE = new SqlDateType();
	// public static final ValueType TIMESTAMP = new TimestampType();
	// public static final ValueType BINARY = new BinaryType();
	// public static final ValueType BOOLEAN = new BooleanType();
	public static final ValueType OBJECT = new ObjectType();
	private static final Class BYTE_ARRAY_CLASS = new byte[0].getClass();
	/** 値の型の集合を現します。 */
	private static Map types = Collections.synchronizedMap(new HashMap());
	/**
	 * 初期化します。
	 */
	static {
		registerValueType(String.class, STRING);
		registerValueType(List.class, LIST);
		// TODO: インタフェースを取得するようにし不要にする
		registerValueType(ArrayList.class, LIST);
		// registerValueType(short.class, SHORT);
		// registerValueType(Short.class, SHORT);
		// registerValueType(int.class, INTEGER);
		// registerValueType(Integer.class, INTEGER);
		// registerValueType(long.class, LONG);
		// registerValueType(Long.class, LONG);
		// registerValueType(float.class, FLOAT);
		// registerValueType(Float.class, FLOAT);
		// registerValueType(double.class, DOUBLE);
		// registerValueType(Double.class, DOUBLE);
		// registerValueType(BigDecimal.class, BIGDECIMAL);
		// registerValueType(java.sql.Date.class, SQLDATE);
		// registerValueType(java.sql.Time.class, TIME);
		// registerValueType(java.util.Date.class, TIMESTAMP);
		// registerValueType(Timestamp.class, TIMESTAMP);
		// registerValueType(Calendar.class, TIMESTAMP);
		// registerValueType(BYTE_ARRAY_CLASS, BINARY);
		// registerValueType(boolean.class, BOOLEAN);
		// registerValueType(Boolean.class, BOOLEAN);
	}

	/**
	 * インスタンス化を禁止します。
	 */
	private ValueTypes() {
		super();
	}

	public static void registerValueType(Class clazz, ValueType valueType) {
		types.put(clazz, valueType);
	}

	public static ValueType getValueType(Object obj) {
		if (obj == null) {
			return OBJECT;
		}
		return getValueType(obj.getClass());
	}

	public static ValueType getValueType(Class clazz) {
		if (clazz == null) {
			return OBJECT;
		}
		ValueType valueType = getValueType0(clazz);
		if (valueType != null) {
			return valueType;
		}
		return OBJECT;
	}

	private static ValueType getValueType0(Class clazz) {
		return (ValueType)types.get(clazz);
	}

	public static ValueType getValueType(int type) {
		switch (type) {
			case Types.TINYINT:
			case Types.SMALLINT:
				return getValueType(Short.class);
			case Types.INTEGER:
				return getValueType(Integer.class);
			case Types.BIGINT:
				return getValueType(Long.class);
			case Types.REAL:
			case Types.FLOAT:
				return getValueType(Float.class);
			case Types.DOUBLE:
				return getValueType(Double.class);
			case Types.DECIMAL:
			case Types.NUMERIC:
				return getValueType(BigDecimal.class);
			case Types.DATE:
				return getValueType(Timestamp.class);
			case Types.TIME:
				return getValueType(java.sql.Time.class);
			case Types.TIMESTAMP:
				return getValueType(Timestamp.class);
			case Types.BINARY:
			case Types.VARBINARY:
			case Types.LONGVARBINARY:
				return getValueType(BYTE_ARRAY_CLASS);
			case Types.CHAR:
			case Types.LONGVARCHAR:
			case Types.VARCHAR:
				return getValueType(String.class);
			case Types.BOOLEAN:
				return getValueType(Boolean.class);
			default:
				return OBJECT;
		}
	}
}
