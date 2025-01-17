package com.salsman.jarexplorerredux.parser;

import lombok.extern.log4j.Log4j2;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Log4j2
public class Parser {

    private final String zipFile;
    private final String fileName;
    private final JavaClass jClass;

    public Parser(String zipFilePath, String classFileName) throws IOException {
        this.zipFile = zipFilePath;
        this.fileName = classFileName;
        ClassParser classParser = new ClassParser(zipFilePath, classFileName);
        this.jClass = classParser.parse();
    }

    public MyClass getMyClass() {
        return new MyClass(this.zipFile + File.separator + this.fileName, this.fileName, this.jClass);
    }

    public MyField[] getFields() {
        return Arrays.stream(jClass.getFields())
                .filter(field -> !field.getName().contains("$"))
                .map(MyField::new)
                .toArray(MyField[]::new);
    }

    public MyMethod[] getMethods() {
        return Arrays.stream(jClass.getMethods())
                .filter(method -> !method.getName().equals("<init>")
                                  && !method.getName().equals("<clinit>")
                                  && !method.getName().contains("$"))
                .map(method -> new MyMethod(this.jClass.getClassName(), method))
                .toArray(MyMethod[]::new);
    }

    public MyMethod[] getConstructors() {
        return Arrays.stream(jClass.getMethods())
                .filter(method -> method.getName().equals("<init>")
                                  && !method.getName().contains("$"))
                .map(method -> new MyMethod(this.jClass.getClassName(), method))
                .toArray(MyMethod[]::new);
    }

    public static void dump(String jarFilePath, String classFilePath) throws IOException {
        File classFile = new File(jarFilePath + File.separator + classFilePath);
        if (classFile.isFile()) {
            log.info("file: {}", classFile.getAbsolutePath());
            log.info("file size: {}", classFile.length());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy k:m:s");
            log.info("file lastmodification: {}", dateFormat.format(new Date(classFile.lastModified())));
        }

        ClassParser classParser = new ClassParser(jarFilePath, classFilePath);
        JavaClass javaClass = classParser.parse();
        String sourceFileName = javaClass.getSourceFileName();
        String className = sourceFileName.substring(0, sourceFileName.length() - 5);
        log.info("Name: {}", className);
        log.info("className access: {}", Utility.accessToString(javaClass.getAccessFlags(), true));
        log.info("superClass: {}", javaClass.getSuperclassName());
        className = classFilePath.replace("/", ".");
        log.info("className file: {}", className.substring(0, className.length() - 6));
    }

    public static void main(String[] var0) {
        try {
            String jarFilePath = "C:\\JarExplorer.jar";
            String classFilePath = "jarxplorer/parser/MyMethod.class";
            dump(jarFilePath, classFilePath);
        } catch (IOException e) {
            log.error("Error occurred while dumping class file", e);
        }

    }
}
