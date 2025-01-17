package com.salsman.jarexplorerredux.gui.tree;

public interface LifeCycle {
    void preLoading();

    void loading();

    void loaded();

    void closed();
}
