package dev.hwiveloper.app.woomin;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WoominApplication {

	public static void main(String[] args) {
		StringBuilder appSb = new StringBuilder();
		appSb.append("\r\n");
		appSb.append("____    __    ____   ______     ______   .___  ___.  __  .__   __. \r\n"); 
		appSb.append("\\   \\  /  \\  /   /  /  __  \\   /  __  \\  |   \\/   | |  | |  \\ |  | \r\n"); 
		appSb.append(" \\   \\/    \\/   /  |  |  |  | |  |  |  | |  \\  /  | |  | |   \\|  | \r\n"); 
		appSb.append("  \\            /   |  |  |  | |  |  |  | |  |\\/|  | |  | |  . `  | \r\n"); 
		appSb.append("   \\    /\\    /    |  `--'  | |  `--'  | |  |  |  | |  | |  |\\   | \r\n"); 
		appSb.append("    \\__/  \\__/      \\______/   \\______/  |__|  |__| |__| |__| \\__| \r\n"); 
		appSb.append("                                                                   \r\n");
		appSb.append("============================================ 우리동네 민주주의 ==\r\n");
		System.out.println(appSb.toString());
		
		SpringApplication.run(WoominApplication.class, args);
		
	}
}
