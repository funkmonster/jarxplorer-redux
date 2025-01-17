package com.salsman.jarexplorerredux.model;

public class ClassNodeLabel implements MyTreeNode {
    private String name;
    private String className;
    private MyTreeNode[] children;

    public ClassNodeLabel(String className) {
        String formattedClassName = Utilities.formatClassName(className);
        this.name = formattedClassName.substring(0, formattedClassName.indexOf(".class"));
        this.className = className;
        this.children = new MyTreeNode[3];
        children[0] = new FieldNodeLabel("Fields", className);
        children[1] = new ConstructorNodeLabel("Constructors", className);
        children[2] = new MethodNodeLabel("Methods", className);
    }
    @Override
    public Object getChild(Object var1, int var2) {
        return null;
    }

    @Override
    public int getChildCount(Object var1) {
        return 0;
    }

    @Override
    public boolean isLeaf(Object var1) {
        return false;
    }

    @Override
    public int getIndexOfChild(Object var1, Object var2) {
        return 0;
    }

    @Override
    public String getType() {
        return "";
    }
}
