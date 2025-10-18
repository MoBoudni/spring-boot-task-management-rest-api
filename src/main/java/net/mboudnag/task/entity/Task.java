package net.mboudnag.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA-Entity-Klasse für Aufgaben.
 * 
 * Diese Klasse repräsentiert eine Aufgabe in der Datenbank und wird
 * in der Tabelle "Tasks" gespeichert. Sie enthält alle persistenten
 * Attribute einer Aufgabe.
 * 
 * Lombok-Annotationen generieren automatisch Getter, Setter sowie Konstruktoren.
 * 
 * @author Mboudnag
 * @version 2.0
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
     * 
     * Wird automatisch von der Datenbank generiert (Auto-Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Der Titel der Aufgabe.
     * 
     * Pflichtfeld auf Datenbankebene (NOT NULL Constraint).
     */
    @Column(nullable = false)
    private String title;

    /**
     * Die detaillierte Beschreibung der Aufgabe.
     * 
     * Pflichtfeld auf Datenbankebene (NOT NULL Constraint).
     */
    @Column(nullable = false)
    private String description;

    /**
     * Der Abschlussstatus der Aufgabe.
     * 
     * true = Aufgabe ist abgeschlossen<br>
     * false = Aufgabe ist noch offen (Standardwert: false)
     */
    private boolean completed;
}
