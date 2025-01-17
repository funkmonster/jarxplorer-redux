package com.salsman.jarexplorerredux.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    @Getter @Setter
    private static String jarPath;
    private static final String PATTERN_STR = "(\\w+\\.class)";
    private static final Pattern pattern = Pattern.compile(PATTERN_STR);

    private Utilities() {}

    public static String formatClassPathDir(String path) {
        if (path.contains(".jar")) {
            String classPathDir = path.substring(path.indexOf(".jar") + 5);
            return classPathDir.replace(File.separator, "/");
        }

        return path;
    }

    public static String formatImportPathDir(String importPathDirectory) {
        if (importPathDirectory.contains(".jar")) {
            String importPathDir = importPathDirectory.substring(importPathDirectory.indexOf(".jar") + 5, importPathDirectory.indexOf(".class"));
            return importPathDir.replace(File.separator, ".");
        }

        return importPathDirectory;
    }

    public static String formatClassName(String className) {
        Matcher matcher = pattern.matcher(className);
        boolean found = matcher.find();
        return found ? matcher.group() : className;
    }
}
