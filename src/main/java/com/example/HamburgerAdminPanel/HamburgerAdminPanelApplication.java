package com.example.HamburgerAdminPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sai Milind
 */
@SpringBootApplication
public class HamburgerAdminPanelApplication {
	public static final Logger logger = LoggerFactory.getLogger(HamburgerAdminPanelApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(HamburgerAdminPanelApplication.class, args);
	}

}
