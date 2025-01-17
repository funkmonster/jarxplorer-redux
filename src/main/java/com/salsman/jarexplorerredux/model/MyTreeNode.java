package com.salsman.jarexplorerredux.model;

public interface MyTreeNode {
    Object getChild(Object var1, int var2);

    int getChildCount(Object var1);

    boolean isLeaf(Object var1);

    int getIndexOfChild(Object var1, Object var2);

    String getType();
}
