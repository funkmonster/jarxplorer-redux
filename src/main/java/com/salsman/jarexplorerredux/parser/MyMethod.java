package com.salsman.jarexplorerredux.parser;

import lombok.Getter;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

@Getter
public class MyMethod {
    private String name = "";
    private String modifiers = "";
    private String returnType = "";
    private String signature = "";
    private String exceptions = "";
    private boolean constructor = false;

    public MyMethod(String className, Method method) {
        name = method.getName();
        if (name.equals("<init>")) {
            if (className.contains(".")) {
                name = className.substring(className.lastIndexOf(".") + 1);
            } else {
                name = className;
            }

            constructor = true;
        }

        modifiers = Utility.accessToString(method.getAccessFlags());
        returnType = method.getReturnType().toString();
        setSignature(method);
        setExceptions(method);
    }

    private void setSignature(Method method) {
        String methodString = method.toString();
        signature = methodString.substring(methodString.indexOf('('), methodString.lastIndexOf(')') + 1);
    }

    private void setExceptions(Method method) {
        ExceptionTable exceptionTable = method.getExceptionTable();
        if (exceptionTable != null) {
            exceptions = exceptionTable.toString();
        }

    }

    public String dump() {
        return "Name: " + getName() + "\n" +
                      "Modifiers: " + getModifiers() + "\n" +
                      "Signature: " + getSignature() + "\n" +
                      "Exceptions: " + getExceptions() + "\n";
    }
}
