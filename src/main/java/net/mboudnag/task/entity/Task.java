package net.mboudnag.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * JPA-Entity-Klasse für Aufgaben.

 * Diese Klasse repräsentiert eine Aufgabe in der Datenbank und wird
 * in der Tabelle "Tasks" gespeichert. Sie enthält alle persistenten
 * Attribute einer Aufgabe.

 * Lombok-Annotationen generieren automatisch Getter, Setter sowie Konstruktoren.
 *
 * @author Mboudnag
 * @version 3.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tasks")
public class Task {

    /**
     * Die eindeutige ID der Aufgabe (Primärschlüssel).

     * Wird automatisch von der Datenbank generiert (Auto-Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Der Titel der Aufgabe.

     * Pflichtfeld auf Datenbankebene (NOT NULL Constraint).
     */
    @Column(nullable = false)
    private String title;

    /**
     * Die detaillierte Beschreibung der Aufgabe.

     * Pflichtfeld auf Datenbankebene (NOT NULL Constraint).
     */
    @Column(nullable = false)
    private String description;

    /**
     * Der Abschlussstatus der Aufgabe.

     * true = Aufgabe ist abgeschlossen<br>
     * false = Aufgabe ist noch offen (Standardwert: false)
     */
    private boolean completed;

    /**
     * Der Zeitpunkt der Erstellung der Aufgabe.

     * Wird automatisch beim ersten Speichern gesetzt und nicht mehr verändert.
     * Hibernate setzt diesen Wert automatisch.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Der Zeitpunkt der letzten Aktualisierung der Aufgabe.

     * Wird automatisch bei jeder Änderung aktualisiert.
     * Hibernate setzt diesen Wert automatisch.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
