package com.salsman.jarexplorerredux.gui.tree;

import com.salsman.jarexplorerredux.helpers.Utilities;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

@Log4j2
public class MainUI extends JFrame {

    public MainUI() {
        Utilities utilities = new Utilities();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            log.error("Error setting Windows look and feel", e);
            System.exit(1);
        }
        setTitle("Jar Explorer Redux");
        URL url = ClassLoader.getSystemResource("images/viewmag.png");
        setIconImage(new ImageIcon(url).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(utilities.getFrameWidth(), utilities.getFrameHeight());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }
}
