package net.mboudnag.task.repository;

import net.mboudnag.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository-Interface für Datenbankoperationen auf Task-Entitäten.
<<<<<<< HEAD
 *
 * Dieses Interface erweitert {@link JpaRepository} und erbt dadurch
 * automatisch alle Standard-CRUD-Operationen (Create, Read, Update, Delete)
 * sowie Paginierungs- und Sortierfunktionen.
 *
 * Spring Data JPA generiert zur Laufzeit automatisch eine Implementierung
 * dieses Interfaces. Es müssen keine eigenen Implementierungen geschrieben werden.
 *
 * Folgende Methoden sind automatisch verfügbar:
 *
=======
 * 
 * Dieses Interface erweitert {@link JpaRepository} und erbt dadurch
 * automatisch alle Standard-CRUD-Operationen (Create, Read, Update, Delete)
 * sowie Paginierungs- und Sortierfunktionen.
 * 
 * Spring Data JPA generiert zur Laufzeit automatisch eine Implementierung
 * dieses Interfaces. Es müssen keine eigenen Implementierungen geschrieben werden.
 * 
 * Folgende Methoden sind automatisch verfügbar:
 * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
 *   save(Task) - Speichert oder aktualisiert eine Aufgabe
 *   findById(Long) - Sucht eine Aufgabe anhand ihrer ID
 *   findAll() - Gibt alle Aufgaben zurück
 *   deleteById(Long) - Löscht eine Aufgabe anhand ihrer ID
 *   und viele weitere...
<<<<<<< HEAD
 *
=======
 * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
 * @author Mboudnag
 * @version 2.0
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Keine zusätzlichen Methoden erforderlich
    // Alle benötigten CRUD-Operationen werden von JpaRepository geerbt
<<<<<<< HEAD
}
=======
}
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
