package dev.hwiveloper.app.woomin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WoominApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoominApplication.class, args);
	}
}
