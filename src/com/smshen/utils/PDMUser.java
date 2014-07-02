package com.smshen.utils;

import java.util.ArrayList;

public class PDMUser {
	private String id;
	private String name;
	private String code;
	private ArrayList<PDMTable> tables = new ArrayList<PDMTable>();

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

	public ArrayList<PDMTable> getTables() {
		return tables;
	}

	public void setTables(ArrayList<PDMTable> tables) {
		this.tables = tables;
	}

	public void addTable(PDMTable table) {
		tables.add(table);
	}
}
