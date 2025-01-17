package com.salsman.jarexplorerredux.model;

import com.salsman.jarexplorerredux.parser.MyField;
import com.salsman.jarexplorerredux.parser.Parser;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class FieldNodeLabel implements MyTreeNode {
    private String label;
    private Parser myParser;
    private MyField[] fields;

    public FieldNodeLabel(String label, String classPath) {
        this.label = label;
        String formatClassPathDir = Utilities.formatClassPathDir(classPath);

        try {
            this.myParser = new Parser(Utilities.getJarPath(), formatClassPathDir);
            this.fields = this.myParser.getFields();
        } catch (IOException e) {
            log.error("Error occurred while parsing class file", e);
            System.exit(1);
        }
    }

    @Override
    public Object getChild(Object var1, int index) {
        Arrays.sort(this.fields, (field1, field2) -> {
            String field1Name = field1.getName();
            String field2Name = field2.getName();
            return field1Name.compareTo(field2Name);
        });
        return new FieldNode(this.fields[index]);
    }

    @Override
    public int getChildCount(Object var1) {
        return fields.length;
    }

    @Override
    public boolean isLeaf(Object var1) {
        return fields.length == 0;
    }

    @Override
    public int getIndexOfChild(Object fieldNodeLabel, Object fieldNode) {
        FieldNodeLabel nodeLabel = (FieldNodeLabel) fieldNodeLabel;
        FieldNode fieldNode1 = (FieldNode) fieldNode;
        List<MyField> fieldList = new ArrayList<>(Arrays.asList(nodeLabel.fields));
        return fieldList.indexOf(fieldNode1.getField());
    }

    @Override
    public String getType() {
        return "FieldNodeLabel";
    }

    @Override
    public String toString() {
        return this.label;
    }
}
