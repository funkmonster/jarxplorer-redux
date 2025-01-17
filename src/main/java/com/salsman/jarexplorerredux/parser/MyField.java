package com.salsman.jarexplorerredux.parser;

import lombok.Getter;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Attribute;

@Getter
public class MyField {

    private final String name;
    private final String modifiers;
    private final String signature;
    private String value = "";

    public MyField(Field field) {
        modifiers = Utility.accessToString(field.getAccessFlags());
        signature = Utility.signatureToString(field.getSignature());
        name = field.getName();
        setValue(field);
    }

    private void setValue(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        ConstantValue constantValue = field.getConstantValue();
        Attribute[] arrayOfAttribute = field.getAttributes();
        int i = (field.getAttributes()).length;
        if (constantValue != null) {
            stringBuilder.append(constantValue);
            for (byte b = 0; b < i; b++) {
                Attribute attribute = arrayOfAttribute[b];
                if (!(attribute instanceof ConstantValue))
                    stringBuilder.append(" [").append(attribute.toString()).append("]");
            }
        }
        value = stringBuilder.toString().replace("\"", "");
    }

    public String dump() {
        return "Name: " + getName() + "\n" +
                              "Modifiers: " + getModifiers() + "\n" +
                              "Signature: " + getSignature() + "\n" +
                              "Value: " + getValue() + "\n";
    }
}
