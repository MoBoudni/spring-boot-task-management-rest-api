package net.mboudnag.task.service;

import net.mboudnag.task.dto.TaskDto;

import java.util.List;

/**
 * Service-Interface für die Geschäftslogik der Aufgabenverwaltung.
 * 
 * Dieses Interface definiert alle Service-Methoden für die Verwaltung
 * von Aufgaben. Es bildet die Geschäftslogikschicht zwischen dem
 * Controller und dem Repository.
 * 
 * Die Implementierung erfolgt in {@link net.mboudnag.task.service.impl.TaskServiceImpl}.
 * 
 * @author Mboudnag
 * @version 1.0
 */
public interface TaskService {

    /**
     * Erstellt eine neue Aufgabe.
     *
     * @param taskDto die zu erstellende Aufgabe als DTO
     * @return die gespeicherte Aufgabe mit generierter ID
     */
    TaskDto addTask(TaskDto taskDto);

    /**
     * Ruft eine einzelne Aufgabe anhand ihrer ID ab.
     *
     * @param id die eindeutige ID der Aufgabe
     * @return die gefundene Aufgabe als DTO
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    TaskDto getTask(Long id);

    /**
     * Ruft alle vorhandenen Aufgaben ab.
     *
     * @return eine Liste aller Aufgaben
     */
    List<TaskDto> getAllTasks();

    /**
     * Aktualisiert eine bestehende Aufgabe.
     *
     * @param taskDto die aktualisierten Aufgabendaten
     * @param id      die ID der zu aktualisierenden Aufgabe
     * @return die aktualisierte Aufgabe als DTO
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    TaskDto updateTask(TaskDto taskDto, Long id);

    /**
     * Löscht eine Aufgabe anhand ihrer ID.
     *
     * @param id die ID der zu löschenden Aufgabe
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    void deleteTask(Long id);

    /**
     * Markiert eine Aufgabe als abgeschlossen.
     *
     * @param id die ID der Aufgabe
     * @return die aktualisierte Aufgabe mit completed=true
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    TaskDto completeTask(Long id);

    /**
     * Markiert eine Aufgabe als nicht abgeschlossen.
     *
     * @param id die ID der Aufgabe
     * @return die aktualisierte Aufgabe mit completed=false
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    TaskDto inCompleteTask(Long id);
}
