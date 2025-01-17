package com.salsman.jarexplorerredux.model;

import com.salsman.jarexplorerredux.parser.MyMethod;
import com.salsman.jarexplorerredux.parser.Parser;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Log4j2
public class MethodNodeLabel implements MyTreeNode {
    private final String label;
    private MyMethod[] methods;

    public MethodNodeLabel(String label, String classPath) {
        this.label = label;
        try {
            methods = new Parser(Utilities.getJarPath(), Utilities.formatClassPathDir(classPath)).getMethods();
        } catch (IOException e) {
            log.error("Error parsing class: {}", e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public Object getChild(Object unused, int index) {
        Arrays.sort(methods, Comparator.comparing(MyMethod::getName));
        return new MethodNode(methods[index]);
    }

    @Override
    public int getChildCount(Object var1) {
        return methods.length;
    }

    @Override
    public boolean isLeaf(Object var1) {
        return methods.length == 0;
    }

    @Override
    public int getIndexOfChild(Object methodNodeLabel, Object methodNode) {
        MethodNodeLabel nodeLabel = (MethodNodeLabel) methodNodeLabel;
        MethodNode node = (MethodNode) methodNode;
        ArrayList<MyMethod> methodList = new ArrayList<>(Arrays.asList(nodeLabel.methods));
        return methodList.indexOf(node.getMethod());
    }

    @Override
    public String getType() {
        return "MethodNodeLabel";
    }

    @Override
    public String toString() {
        return label;
    }
}
