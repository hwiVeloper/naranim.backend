package dev.hwiveloper.app.woomin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
@EnableWebMvc
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
	
	@Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name())
                .allowCredentials(false)
                .maxAge(3600);
            }
        };
    }
}
