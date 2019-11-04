package dev.hwiveloper.naranim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableWebMvc
@MapperScan(basePackages = "dev.hwiveloper.naranim.mapper")
@Slf4j
public class NaranimApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaranimApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
                .allowCredentials(false)
                .maxAge(3600);
            }
        };
    }
}
