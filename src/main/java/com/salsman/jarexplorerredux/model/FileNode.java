package com.salsman.jarexplorerredux.model;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileComparator;
import lombok.Getter;

import java.util.*;

@Getter
public class FileNode implements MyTreeNode {
    private final TFile file;
    private static final HashMap<String, ArrayList<TFile>> memory = new HashMap<>();
    private final String type;

    public FileNode(TFile file) {
        this.file = file;
        this.type = "FileNode";
    }

    public FileNode(TFile paramFile, String unused) {
        this.file = paramFile;
        this.type = "RootNode";
    }

    public String toString() {
        return file.getName();
    }

    @Override
    public Object getChild(Object fileNodeParam, int index) {
        FileNode fileNode = (FileNode) fileNodeParam;
        ArrayList<TFile> children;

        if (memory.containsKey(fileNode.getFile().getAbsolutePath())) {
            children = memory.get(fileNode.getFile().getAbsolutePath());
        } else {
            TFile[] fileList = fileNode.getFile().listFiles(pathname -> !pathname.getName().contains("$"));
            assert fileList != null;
            children = new ArrayList<>(Arrays.asList(fileList));
            children.sort(new TFileComparator());
            memory.put(fileNode.getFile().getAbsolutePath(), children);
        }

        TFile childFile = children.get(index);
        if (childFile.getName().endsWith(".class")) {
            return new ClassNode(childFile.getAbsolutePath());
        }

        return new FileNode(childFile);
    }

    @Override
    public int getChildCount(Object fileNode) {
        FileNode fileNode1 = (FileNode) fileNode;
        return Objects.requireNonNull(fileNode1.getFile().listFiles(pathname -> !pathname.getName().contains("$"))).length;
    }

    @Override
    public boolean isLeaf(Object fileNode) {
        FileNode fileNode1 = (FileNode) fileNode;
        return fileNode1.getFile().isFile();
    }

    @Override
    public int getIndexOfChild(Object fn, Object mtn) {
        FileNode fileNode = (FileNode) fn;
        MyTreeNode myTreeNode = (MyTreeNode) mtn;
        ArrayList<TFile> fileList;
        if (memory.containsKey(fileNode.getFile().getAbsolutePath())) {
            fileList = memory.get(fileNode.getFile().getAbsolutePath());
        } else {
            fileList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(fileNode.getFile().listFiles())));
            fileList.sort(new TFileComparator());
            memory.put(fileNode.getFile().getAbsolutePath(), fileList);
        }

        List<String> fileNames = new ArrayList<>();

        for (TFile tFile : fileList) {
            fileNames.add(tFile.getName());
        }

        return fileNames.indexOf(myTreeNode.toString());
    }
}
