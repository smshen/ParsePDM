package com.smshen.utils;

import java.util.ArrayList;

public class PDMIndex {
	private String id;
	private String name;
	private String code;
	private ArrayList<PDMColumn> columns = new ArrayList<PDMColumn>();

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

	public ArrayList<PDMColumn> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<PDMColumn> columns) {
		this.columns = columns;
	}

	public void addColumn(PDMColumn column) {
		columns.add(column);
	}

}
