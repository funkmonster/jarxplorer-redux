package com.salsman.jarexplorerredux.model;

import com.salsman.jarexplorerredux.parser.MyMethod;
import lombok.Getter;

@Getter
public class MethodNode implements MyTreeNode {
    private MyMethod method;

    public MethodNode(MyMethod method) {
        this.method = method;
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
        return true;
    }

    @Override
    public int getIndexOfChild(Object var1, Object var2) {
        return -1;
    }

    @Override
    public String getType() {
        return "MethodNode";
    }

    @Override
    public String toString() {
        return method.getName();
    }
}
