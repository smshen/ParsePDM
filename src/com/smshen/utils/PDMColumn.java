package com.smshen.utils;

public class PDMColumn {
	private String id;
	private String name;
	private String code;
	private String dataType; // 数据类型
	private int length; // 数据长度
	private int precision; // 精度
	private int mandatory = 0; // 约束
	private String defaultValue; // 默认值
	private String lowValue;
	private String highValue;
	private String comment;
	private PDMTable table;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getMandatory() {
		return mandatory;
	}

	public void setMandatory(int mandatory) {
		this.mandatory = mandatory;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getLowValue() {
		return lowValue;
	}

	public void setLowValue(String lowValue) {
		this.lowValue = lowValue;
	}

	public String getHighValue() {
		return highValue;
	}

	public void setHighValue(String highValue) {
		this.highValue = highValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PDMTable getTable() {
		return table;
	}

	public void setTable(PDMTable table) {
		this.table = table;
	}
}
