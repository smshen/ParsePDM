package com.smshen.utils;

import java.util.ArrayList;

public class PDMTable {
	public String id;
	private String name;
	private String code;
	private PDMUser user;
	private ArrayList<PDMColumn> columns = new ArrayList<PDMColumn>();
	private ArrayList<PDMKey> keys = new ArrayList<PDMKey>();
	private PDMKey primaryKey;
	private ArrayList<PDMIndex> indexs = new ArrayList<PDMIndex>();

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

	public PDMUser getUser() {
		return user;
	}

	public void setUser(PDMUser user) {
		user.addTable(this);
		this.user = user;
	}

	public ArrayList<PDMColumn> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<PDMColumn> columns) {
		this.columns = columns;
		for (PDMColumn column : columns) {
			column.setTable(this);
		}
	}

	public ArrayList<PDMKey> getKeys() {
		return keys;
	}

	public void setKeys(ArrayList<PDMKey> keys) {
		this.keys = keys;
	}

	public PDMKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PDMKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public ArrayList<PDMIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(ArrayList<PDMIndex> indexs) {
		this.indexs = indexs;
	}

	public void addColumn(PDMColumn column) {
		columns.add(column);
		column.setTable(this);
	}

	public void addKey(PDMKey key) {
		keys.add(key);
	}

	public void addIndex(PDMIndex index) {
		indexs.add(index);
	}

	public PDMColumn getPDMColumn(String id) throws Exception {
		for (PDMColumn column : columns) {
			if (id.equals(column.getId())) {
				return column;
			}
		}
		throw new Exception("Id编号" + id + "没有找到");
	}

	public PDMKey getPDMKey(String id) throws Exception {
		for (PDMKey key : keys) {
			if (id.equals(key.getId())) {
				return key;
			}
		}
		throw new Exception("Id编号" + id + "没有找到");
	}
        
        @Override
        public String toString() {
            return this.name + "(" + this.code + ")";
        }
}
