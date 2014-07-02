package com.smshen.utils;

public class PDMReferenceJoin {
	private String Id;
	private PDMColumn parentTable_Col;
	private PDMColumn childTable_Col;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public PDMColumn getParentTable_Col() {
		return parentTable_Col;
	}

	public void setParentTable_Col(PDMColumn parentTable_Col) {
		this.parentTable_Col = parentTable_Col;
	}

	public PDMColumn getChildTable_Col() {
		return childTable_Col;
	}

	public void setChildTable_Col(PDMColumn childTable_Col) {
		this.childTable_Col = childTable_Col;
	}

}
