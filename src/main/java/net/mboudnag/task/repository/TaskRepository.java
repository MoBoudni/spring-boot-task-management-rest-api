package net.mboudnag.task.repository;

import net.mboudnag.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository-Interface für Datenbankoperationen auf Task-Entitäten.

 * Dieses Interface erweitert {@link JpaRepository} und erbt dadurch
 * automatisch alle Standard-CRUD-Operationen (Create, Read, Update, Delete)
 * sowie Paginierungs- und Sortierfunktionen.

 * Spring Data JPA generiert zur Laufzeit automatisch eine Implementierung
 * dieses Interfaces. Es müssen keine eigenen Implementierungen geschrieben werden.

 * Folgende Methoden sind automatisch verfügbar:

 *   save(Task) - Speichert oder aktualisiert eine Aufgabe
 *   findById(Long) - Sucht eine Aufgabe anhand ihrer ID
 *   findAll() - Gibt alle Aufgaben zurück
 *   deleteById(Long) - Löscht eine Aufgabe anhand ihrer ID
 *   und viele weitere...

 * @author Mboudnag
 * @version 2.0
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Keine zusätzlichen Methoden erforderlich
    // Alle benötigten CRUD-Operationen werden von JpaRepository geerbt


    // Benötigte Methoden für die Tests
    List<Task> findByCompleted(boolean completed);
    List<Task> findByTitleContainingIgnoreCase(String title);
    List<Task> findByCompletedFalseOrderByCreatedAt();
    long deleteByCompleted(boolean completed);
}