package net.mboudnag.task;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// Dieser Codeblock ist die Hülle und der Starter der Anwendung
// Zweck: Startet die Spring Boot-Anwendung und definiert eine Utility-Bean.

@SpringBootApplication  // kombiniert @Configuration, @EnableAutoConfiguration und @ComponentScan
                        // initialisiert die gesamte Spring Boot-Anwendung, aktiviert die automatische
                        // Konfiguration und scannt Komponenten
public class TaskManagementApplication {

    @Bean
    public ModelMapper modelMapper() {  // modelMapper(): Definiert eine Bean vom Typ ModelMapper, die üblicherweise
        // zur Umwandlung von Datenübertragungsobjekten (DTOs) in Entitäten und umgekehrt verwendet wird.
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
                              //SpringApplication.run(): startet den eingebetteten Webserver und initialisiert
                              // den Application Context.

}

// Dieser Codeblock ist eine spezifische Konfiguration (eine "Erweiterung" oder "Einstellung"),
// die innerhalb dieser Hülle definiert wird, um ein bestimmtes Sicherheitsproblem (CORS) zu lösen.:

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/**")
//						.allowedMethods("*")
//						.allowedOrigins("http://localhost:3000");
//			}
//		};
//	}

