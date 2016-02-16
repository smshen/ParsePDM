package com.smshen.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Parser {

    private PDM pdm = new PDM();
    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public PDM pdmParser(String pdmFileName) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(pdmFileName);
              
        Node model = doc.selectSingleNode("//c:Children/o:Model");

        pdm.setId(((Element) model).attributeValue("Id"));
        pdm.setName(model.selectSingleNode("a:Name").getText());
        pdm.setCode(model.selectSingleNode("a:Code").getText());

        Node dbms = model.selectSingleNode("//o:Shortcut");
        pdm.setDBMSCode(dbms.selectSingleNode("a:Code").getText());
        pdm.setDBMSName(dbms.selectSingleNode("a:Name").getText());

        LOGGER.info("解析PDM为:" + pdm.getCode() + "(" + pdm.getName() + ")  DBMS为:" + pdm.getDBMSCode() + "(" + pdm.getDBMSName() + ")");

        pdm.setUsers(pdmUserParser(model));
        pdm.setTables(pdmTableParser(model));
        pdm.setPhysicalDiagrams(pdmPhysicalDiagramParser(model));
        pdm.setReferences(pdmReferenceParser(model));

        return pdm;
    }

    public ArrayList<PDMPhysicalDiagram> pdmPhysicalDiagramParser(Node node) throws Exception {
        ArrayList<PDMPhysicalDiagram> physicalList = new ArrayList<PDMPhysicalDiagram>();
        for (Object o : node.selectNodes("c:PhysicalDiagrams/o:PhysicalDiagram")) {
            Node physicalNode = (Node) o;
            PDMPhysicalDiagram pdmPhysical = new PDMPhysicalDiagram();
            pdmPhysical.setId(((Element) physicalNode).attributeValue("Id"));
            pdmPhysical.setName(physicalNode.selectSingleNode("a:Name").getText());
            pdmPhysical.setCode(physicalNode.selectSingleNode("a:Code").getText());
            // 添加Table
            for (Object table : physicalNode.selectNodes("c:Symbols/o:TableSymbol/c:Object/o:Table")) {
                String id = ((Element) table).attributeValue("Ref");
                pdmPhysical.addTable(pdm.getPDMTable(id));
            }
            physicalList.add(pdmPhysical);
        }
        return physicalList;
    }

    public ArrayList<PDMTable> pdmTableParser(Node node) throws Exception {
        ArrayList<PDMTable> tableList = new ArrayList<PDMTable>();
        ArrayList l = new ArrayList();
        List ll = node.selectNodes("c:Packages/o:Package/c:Tables/o:Table");
        if (!ll.isEmpty()) {
            l.addAll((ArrayList) node.selectNodes("c:Packages/o:Package/c:Tables/o:Table"));
        }
        List lll = node.selectNodes("c:Tables/o:Table");
        if (!lll.isEmpty()) {
            l.addAll(node.selectNodes("c:Tables/o:Table"));
        }

//		List l = node.selectNodes("c:Packages/o:Package/c:Tables/o:Table");
        for (Object o : l) {
            Node tableNode = (Node) o;
            PDMTable pdmTable = new PDMTable();
            pdmTable.setId(((Element) tableNode).attributeValue("Id"));
            pdmTable.setName(tableNode.selectSingleNode("a:Name").getText());
            pdmTable.setCode(tableNode.selectSingleNode("a:Code").getText());
            // 添加Columns
            pdmTable.setColumns(pdmColumnParser(tableNode));
            // 添加key
            for (Object key : tableNode.selectNodes("c:Keys/o:Key")) {
                Node keyNode = (Node) key;
                PDMKey pdmKey = new PDMKey();
                pdmKey.setId(((Element) keyNode).attributeValue("Id"));
                pdmKey.setName(keyNode.selectSingleNode("a:Name").getText());
                pdmKey.setCode(keyNode.selectSingleNode("a:Code").getText());
                for (Object column : keyNode.selectNodes("c:Key.Columns/o:Column")) {
                    String id = ((Element) column).attributeValue("Ref");
                    pdmKey.addColumn(pdmTable.getPDMColumn(id));
                }
                pdmTable.addKey(pdmKey);
            }
            // 添加PrimaryKey
            if (null != tableNode.selectSingleNode("c:PrimaryKey/o:Key")) {
                String keyId = ((Element) tableNode.selectSingleNode("c:PrimaryKey/o:Key")).attributeValue("Ref");
                pdmTable.setPrimaryKey(pdmTable.getPDMKey(keyId));
            }

            // 添加Indexes
            for (Object index : tableNode.selectNodes("c:Indexes/o:Index")) {
                Node indexNode = (Node) index;
                PDMIndex pdmIndex = new PDMIndex();
                pdmIndex.setId(((Element) indexNode).attributeValue("Id"));
                pdmIndex.setName(indexNode.selectSingleNode("a:Name").getText());
                pdmIndex.setCode(indexNode.selectSingleNode("a:Code").getText());
                /*
                 for (Object column : indexNode.selectNodes("//c:Column/o:Column")) {
                 String id = ((Element) column).attributeValue("Ref");
                 pdmIndex.addColumn(pdmTable.getPDMColumn(id));
                 }
                 */
                pdmTable.addIndex(pdmIndex);
            }
            // 添加User
            Element userElement = (Element) tableNode.selectSingleNode("c:Owner/o:User");
            if (userElement != null) {
                String userId = userElement.attributeValue("Ref");
                pdmTable.setUser(pdm.getPDMUser(userId));
            }

            tableList.add(pdmTable);
        }
        return tableList;
    }

    public ArrayList<PDMColumn> pdmColumnParser(Node node) {
        ArrayList<PDMColumn> columnList = new ArrayList<PDMColumn>();

        try {
            for (Object o : node.selectNodes("c:Columns/o:Column")) {
                Node columnNode = (Node) o;
                if (columnNode == null) {
                    break;
                }
                PDMColumn pdmColumn = new PDMColumn();
                pdmColumn.setId(((Element) columnNode).attributeValue("Id"));
                pdmColumn.setName(columnNode.selectSingleNode("a:Name") == null ? "" : columnNode.selectSingleNode("a:Name").getText());
                pdmColumn.setCode(columnNode.selectSingleNode("a:Code") == null ? "" : columnNode.selectSingleNode("a:Code").getText());
                pdmColumn.setDataType(columnNode.selectSingleNode("a:DataType") == null ? "" : columnNode.selectSingleNode("a:DataType").getText());
                pdmColumn.setLength(selectSingleNodeIntText(columnNode, "a:Length"));
                pdmColumn.setPrecision(selectSingleNodeIntText(columnNode, "a:Precision"));
                pdmColumn.setMandatory(selectSingleNodeIntText(columnNode, "a:Mandatory"));
                pdmColumn.setDefaultValue(selectSingleNodeStringText(columnNode, "a:DefaultValue"));
                pdmColumn.setLowValue(selectSingleNodeStringText(columnNode, "a:LowValue"));
                pdmColumn.setHighValue(selectSingleNodeStringText(columnNode, "a:HighValue"));
                pdmColumn.setComment(selectSingleNodeStringText(columnNode, "a:Comment"));
                columnList.add(pdmColumn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error:" + e.getMessage());            
        }

        return columnList;
    }

    public ArrayList<PDMUser> pdmUserParser(Node node) {
        ArrayList<PDMUser> userList = new ArrayList<PDMUser>();
        for (Object o : node.selectNodes("c:Users/o:User")) {
            Node userNode = (Node) o;
            PDMUser pdmUser = new PDMUser();
            pdmUser.setId(((Element) userNode).attributeValue("Id"));
            pdmUser.setName(userNode.selectSingleNode("a:Name").getText());
            pdmUser.setCode(userNode.selectSingleNode("a:Code").getText());

            userList.add(pdmUser);
        }
        return userList;
    }

    public ArrayList<PDMReference> pdmReferenceParser(Node node) throws Exception {
        ArrayList<PDMReference> referenceList = new ArrayList<PDMReference>();
        for (Object reference : node.selectNodes("c:References/o:Reference")) {
            Node referenceNode = (Node) reference;
            PDMReference pdmReference = new PDMReference();
            pdmReference.setId(((Element) referenceNode).attributeValue("Id"));
            pdmReference.setName(referenceNode.selectSingleNode("a:Name").getText());
            pdmReference.setCode(referenceNode.selectSingleNode("a:Code").getText());
            pdmReference.setConstraintName(selectSingleNodeStringText(referenceNode, "ForeignKeyConstraintName"));
            pdmReference.setUpdateConstraint(selectSingleNodeIntText(referenceNode, "UpdateConstraint"));
            pdmReference.setDeleteConstraint(selectSingleNodeIntText(referenceNode, "DeleteConstraint"));
            pdmReference.setImplementationType(selectSingleNodeStringText(referenceNode, "ImplementationType"));
            // 添加ParentTable
            String parentTableId = ((Element) referenceNode.selectSingleNode("c:ParentTable/o:Table")).attributeValue("Ref");
            pdmReference.setParentTable(pdm.getPDMTable(parentTableId));
            // 添加ChildTable
            String childTableId = ((Element) referenceNode.selectSingleNode("c:ChildTable/o:Table")).attributeValue("Ref");
            pdmReference.setChildTable(pdm.getPDMTable(childTableId));
            // 添加Joins
            for (Object jion : referenceNode.selectNodes("c:Joins/o:ReferenceJoin")) {
                Node referenceJoinNode = (Node) jion;
                PDMReferenceJoin pdmReferenceJoin = new PDMReferenceJoin();
                pdmReferenceJoin.setId(((Element) referenceJoinNode).attributeValue("Id"));

                String id = ((Element) referenceJoinNode.selectSingleNode("c:Object1/o:Column")).attributeValue("Ref");
                pdmReferenceJoin.setParentTable_Col(pdmReference.getParentTable().getPDMColumn(id));

                id = ((Element) referenceJoinNode.selectSingleNode("c:Object2/o:Column")).attributeValue("Ref");
                pdmReferenceJoin.setChildTable_Col(pdmReference.getChildTable().getPDMColumn(id));

                pdmReference.addReferenceJoin(pdmReferenceJoin);
            }

            referenceList.add(pdmReference);
        }
        return referenceList;
    }

    private String selectSingleNodeStringText(Node parentNode, String childNodeName) {
        Node childNode = parentNode.selectSingleNode(childNodeName);
        if (childNode != null) {
            return childNode.getText();
        } else {
            return null;
        }
    }

    private int selectSingleNodeIntText(Node parentNode, String childNodeName) {
        Node childNode = parentNode.selectSingleNode(childNodeName);
        if (childNode != null) {
            return Integer.parseInt(childNode.getText());
        } else {
            return 0;
        }
    }
}
