package net.mboudnag.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) für Aufgaben.

 * Dieses DTO wird für die Kommunikation zwischen dem Client und dem Server verwendet.
 * Es dient als Zwischenschicht zwischen der Präsentations- und der Persistenzschicht
 * und verhindert die direkte Exponierung der Entity-Klasse.

 * Lombok-Annotationen generieren automatisch Getter, Setter sowie Konstruktoren.
 *
 * @author Mboudnag
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    /**
     * Die eindeutige ID der Aufgabe.

     * Wird von der Datenbank automatisch generiert.
     */
    private Long id;

    /**
     * Der Titel der Aufgabe.

     * Pflichtfeld: Darf nicht null sein.
     */
    private String title;

    /**
     * Die detaillierte Beschreibung der Aufgabe.

     * Pflichtfeld: Darf nicht null sein.
     */
    private String description;

    /**
     * Der Status der Aufgabe.

     * true = Aufgabe ist abgeschlossen<br>
     * false = Aufgabe ist noch offen (Standardwert)
     */
    private boolean completed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}