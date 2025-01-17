package com.salsman.jarexplorerredux.model;

import com.salsman.jarexplorerredux.parser.MyClass;
import com.salsman.jarexplorerredux.parser.Parser;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ClassNode implements MyTreeNode {
    private final String className;
    private final String classPathDir;
    @Getter
    private final String path;
    @Getter
    private MyClass myClass;

    public ClassNode(String classPath) {
        path = classPath;
        classPathDir = classPath;
        className = Utilities.formatClassName(classPath);
        try {
            myClass = new Parser(Utilities.getJarPath(), Utilities.formatClassPathDir(classPath)).getMyClass();
        } catch (IOException iOException) {
            log.error("Error occurred while parsing class file", iOException);
            System.exit(1);
        }
    }
    @Override
    public Object getChild(Object var1, int var2) {
        return new ClassNodeLabel(classPathDir);
    }

    @Override
    public int getChildCount(Object var1) {
        return 1;
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
        return "ClassNode";
    }

    @Override
    public String toString() {
        return className;
    }
}
