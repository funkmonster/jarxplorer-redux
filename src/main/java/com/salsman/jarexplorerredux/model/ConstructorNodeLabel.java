package com.salsman.jarexplorerredux.model;

import com.salsman.jarexplorerredux.parser.MyMethod;
import com.salsman.jarexplorerredux.parser.Parser;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Log4j2
public class ConstructorNodeLabel implements MyTreeNode {
    private final String label;
    private MyMethod[] methods;

    public ConstructorNodeLabel(String label, String classPath) {
        this.label = label;
        try {
            methods = new Parser(Utilities.getJarPath(),
                    Utilities.formatClassPathDir(classPath)).getConstructors();
        } catch (IOException e) {
            log.error("Error occurred while parsing class file", e);
            System.exit(1);
        }
    }

    @Override
    public Object getChild(Object var1, int index) {
        // sort the methods array by name in descending order
        Arrays.sort(methods, Comparator.comparing(MyMethod::getName, Comparator.reverseOrder()));
        return new ConstructorNode(methods[index]);
    }

    @Override
    public int getChildCount(Object var1) {
        return this.methods.length;
    }

    @Override
    public boolean isLeaf(Object var1) {
        return this.methods.length == 0;
    }

    @Override
    public int getIndexOfChild(Object constructorNodeLabel, Object constructorNode) {
        ConstructorNodeLabel nodeLabel = (ConstructorNodeLabel) constructorNodeLabel;
        ConstructorNode node = (ConstructorNode) constructorNode;
        ArrayList<MyMethod> methodList = new ArrayList<>(Arrays.asList(nodeLabel.methods));
        return methodList.indexOf(node.getMethod());
    }

    @Override
    public String getType() {
        return "ConstructorNodeLabel";
    }

    public String toString() {
        return this.label;
    }
}
