package com.example.HamburgerAdminPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Sai Milind
 */
@SpringBootApplication
@EntityScan(value = "com.example.HamburgerAdminPanel.Entity")
@EnableMongoRepositories(value = "com.example.HamburgerAdminPanel.Repository")
@EnableJpaRepositories(value = "com.example.HamburgerAdminPanel.Repository")
public class HamburgerAdminPanelApplication{
	public static final Logger logger = LoggerFactory.getLogger(HamburgerAdminPanelApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(HamburgerAdminPanelApplication.class, args);
	}
}
