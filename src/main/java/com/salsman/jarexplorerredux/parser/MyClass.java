package com.salsman.jarexplorerredux.parser;

import de.schlichtherle.truezip.file.TFile;
import lombok.Getter;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class MyClass {

    private final String path;
    private final String fileSize;
    private final String modificationTime;
    private final String name;
    private final String access;
    private final String superClass;
    private final String className;

    public MyClass(String filePath, String classFileName, JavaClass javaClass) {
        TFile file = new TFile(filePath);
        this.path = file.getAbsolutePath();
        this.fileSize = (Long.toString(file.length()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy k:m:s");
        this.modificationTime = simpleDateFormat.format(new Date(file.lastModified()));
        String javaClassName = javaClass.getClassName();
        int i = javaClassName.lastIndexOf(".") + 1;
        this.name = javaClassName.substring(i);
        this.superClass = javaClass.getSuperclassName();
        String className = classFileName.replace("/", ".");
        this.className = className.substring(0, className.length() - 6);
        this.access = Utility.accessToString(javaClass.getAccessFlags(), true);
    }

}
