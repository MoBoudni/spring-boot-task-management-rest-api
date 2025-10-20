package net.mboudnag.task;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Hauptklasse der Task-Management-Anwendung.

 * Diese Klasse dient als Einstiegspunkt für die Spring Boot-Anwendung
 * und konfiguriert grundlegende Beans, die in der gesamten Anwendung
 * verwendet werden.

 * Die {@code @SpringBootApplication}-Annotation kombiniert drei wichtige Annotationen:

 *   {@code @Configuration} - Kennzeichnet die Klasse als Konfigurationsquelle
 *   {@code @EnableAutoConfiguration} - Aktiviert die automatische Spring-Konfiguration
 *   {@code @ComponentScan} - Scannt das Package nach Spring-Komponenten

 * @author Mboudnag
 * @version 2.0
 */
@SpringBootApplication
public class TaskManagementApplication {

    /**
     * Erstellt eine ModelMapper-Bean für die gesamte Anwendung.

     * Der ModelMapper wird verwendet, um Objekte zwischen verschiedenen
     * Schichten zu konvertieren, insbesondere zwischen Entity- und DTO-Objekten.
     * Dies vermeidet repetitiven Code für die Objektumwandlung und sorgt für
     * eine saubere Trennung der Schichten.

     * Anwendungsbeispiele:

     *   Task (Entity) → TaskDto (DTO)
     *   TaskDto (DTO) → Task (Entity)
     *
     * @return eine neue ModelMapper-Instanz, die von Spring verwaltet wird.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Haupteinstiegspunkt der Anwendung.

     * Diese Methode startet die Spring Boot-Anwendung, initialisiert den
     * Application Context und startet den eingebetteten Tomcat-Webserver.

     * @param args Kommandozeilenargumente, die beim Start übergeben werden können
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}