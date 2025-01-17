package com.salsman.jarexplorerredux;

import com.salsman.jarexplorerredux.gui.tree.GuiManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.swing.*;

@SpringBootApplication
@Log4j2
public class JarExplorerReduxApplication {

	public static void main(String[] args) {
		SpringApplication.run(JarExplorerReduxApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startGui() {
		String vendor = System.getProperty("java.vendor");
		String version = System.getProperty("java.version");
		log.info("Vendor: {}", vendor);
		log.info("Version: {}", version);

		SwingUtilities.invokeLater(() -> {
			try {
				new GuiManager();
			} catch (Exception e) {
				log.error("Error initializing GUI: {}", e.getMessage(), e);
				System.exit(1);
			}
		});
	}

}
