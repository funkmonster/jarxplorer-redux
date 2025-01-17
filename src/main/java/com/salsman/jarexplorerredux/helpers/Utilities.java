package com.salsman.jarexplorerredux.helpers;

import com.salsman.jarexplorerredux.model.FileNode;
import de.schlichtherle.truezip.file.TFile;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Getter
@Log4j2
public class Utilities {
    private final Dimension screenSize;

    public Utilities() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        screenSize = toolkit.getScreenSize();
    }

    public int getFrameWidth() {
        return getScreenSize().width / 2 - 50;
    }

    public int getFrameHeight() {
        return getScreenSize().height / 2 + 100;
    }

    public int getScreenWidth() {
        return getScreenSize().width;
    }

    public int getScreenHeight() {
        return getScreenSize().height;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    public static void launchFile(final FileNode fileNode, final String archivePath) {
        Runnable task = () -> {
            try {
                // Create temporary directory for file extraction
                Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
                Path targetPath = tempDir.resolve(fileNode.toString());

                // Extract file from archive
                extractFileFromArchive(archivePath, fileNode.toString(), targetPath.toFile());

                targetPath.toFile().deleteOnExit();

                // Launch the file
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", targetPath.toAbsolutePath().toString());

            } catch (IOException e) {
                log.error("Something happened.", e);
            }

        }
    }

    private static void extractFileFromArchive(String archivePath, String entryName, File outputFile) throws IOException {
        try (ZipFile zipFile = new ZipFile(archivePath)) {
            ZipEntry entry = zipFile.getEntry(entryName);
            if (entry != null) {
                try (InputStream in = zipFile.getInputStream(entry);
                     FileOutputStream out = new FileOutputStream(outputFile)) {
                    in.transferTo(out);
                }
            }
        }
    }
}
