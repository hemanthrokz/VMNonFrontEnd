package com.VendingMachine.VendingMachine01;

import com.VendingMachine.VendingMachine01.logger.LoggingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendingMachine01Application {
	public static void main(String[] args) {
		SpringApplication.run(VendingMachine01Application.class, args);
		new LoggingController().index();
	}
}
