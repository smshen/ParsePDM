package com.smshen.utils;

import java.util.ArrayList;

public class PDM {
	private String id;
	private String name;
	private String code;
	private String dBMSCode;
	private String dBMSName;
	private ArrayList<PDMPhysicalDiagram> physicalDiagrams = new ArrayList<PDMPhysicalDiagram>();
	private ArrayList<PDMUser> users = new ArrayList<PDMUser>();
	private ArrayList<PDMTable> tables = new ArrayList<PDMTable>();
	private ArrayList<PDMReference> references = new ArrayList<PDMReference>();

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

	public String getDBMSCode() {
		return dBMSCode;
	}

	public void setDBMSCode(String code) {
		dBMSCode = code;
	}

	public String getDBMSName() {
		return dBMSName;
	}

	public void setDBMSName(String name) {
		dBMSName = name;
	}

	public ArrayList<PDMPhysicalDiagram> getPhysicalDiagrams() {
		return physicalDiagrams;
	}

	public void setPhysicalDiagrams(
			ArrayList<PDMPhysicalDiagram> physicalDiagrams) {
		this.physicalDiagrams = physicalDiagrams;
	}

	public ArrayList<PDMUser> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<PDMUser> users) {
		this.users = users;
	}

	public ArrayList<PDMTable> getTables() {
		return tables;
	}

	public void setTables(ArrayList<PDMTable> tables) {
		this.tables = tables;
	}

	public ArrayList<PDMReference> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<PDMReference> references) {
		this.references = references;
	}

	public PDMUser getPDMUser(String id) throws Exception {
		for (PDMUser user : users) {
			if (id.equals(user.getId())) {
				return user;
			}
		}
		throw new Exception("Id编号" + id + ",USER没有找到");
	}

	public PDMTable getPDMTable(String id) throws Exception {
		for (PDMTable table : tables) {
			if (id.equals(table.getId())) {
				return table;
			}
		}
		throw new Exception("Id编号" + id + ",TABLE没有找到");
	}

	public PDMReference getPDMReference(String id) throws Exception {
		for (PDMReference reference : references) {
			if (id.equals(reference.getId())) {
				return reference;
			}
		}
		throw new Exception("Id编号" + id + ",REFERENCE没有找到");
	}
}
