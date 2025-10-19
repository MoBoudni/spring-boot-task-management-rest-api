package net.mboudnag.task.service.impl;

import lombok.AllArgsConstructor;
import net.mboudnag.task.dto.TaskDto;
import net.mboudnag.task.entity.Task;
import net.mboudnag.task.exception.ResourceNotFoundException;
import net.mboudnag.task.repository.TaskRepository;
import net.mboudnag.task.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementierung des TaskService-Interfaces.
 *
 * Diese Klasse enthält die gesamte Geschäftslogik für die Verwaltung
 * von Aufgaben. Sie fungiert als Vermittler zwischen dem Controller
 * und dem Repository und übernimmt die Konvertierung zwischen
 * Entity- und DTO-Objekten.
 *
 * Die {@code @Service}-Annotation markiert diese Klasse als Spring-Bean
 * und ermöglicht die automatische Dependency Injection.
 *
 * @author Mboudnag
 * @version 2.0
 */
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    /**
     * Erstellt eine neue Aufgabe in der Datenbank.
     *
     * Konvertiert das empfangene DTO in eine Entity, speichert diese
     * und gibt die gespeicherte Aufgabe wieder als DTO zurück.
     *
     * @param taskDto die zu erstellende Aufgabe als DTO
     * @return die gespeicherte Aufgabe mit generierter ID als DTO
     */
    @Override
    public TaskDto addTask(TaskDto taskDto) {
        // DTO in JPA-Entity konvertieren
        Task task = modelMapper.map(taskDto, Task.class);

        // Entity in der Datenbank speichern
        Task savedTask = taskRepository.save(task);

        // Gespeicherte Entity zurück in DTO konvertieren

        return modelMapper.map(savedTask, TaskDto.class);
    }

    /**
     * Ruft eine einzelne Aufgabe anhand ihrer ID ab.
     *
     * @param id die eindeutige ID der Aufgabe
     * @return die gefundene Aufgabe als DTO
     * @throws ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @Override
    public TaskDto getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id:" + id));

        return modelMapper.map(task, TaskDto.class);
    }

    /**
     * Ruft alle vorhandenen Aufgaben aus der Datenbank ab.
     *
     * Konvertiert alle gefundenen Entities in DTOs mithilfe
     * von Java Streams für eine effiziente Verarbeitung.
     *
     *
     * @return eine Liste aller Aufgaben als DTOs
     */
    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map((task) -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Aktualisiert eine bestehende Aufgabe mit neuen Daten.
     *
     * Sucht die Aufgabe, aktualisiert ihre Felder (Titel, Beschreibung, Status)
     * und speichert die Änderungen in der Datenbank.
     *
     * @param taskDto die aktualisierten Aufgabendaten
     * @param id      die ID der zu aktualisierenden Aufgabe
     * @return die aktualisierte Aufgabe als DTO
     * @throws ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @Override
    public TaskDto updateTask(TaskDto taskDto, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        // Felder der Entity mit neuen Werten aktualisieren
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        // Aktualisierte Entity speichern
        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    /**
     * Löscht eine Aufgabe aus der Datenbank.
     *
     * Prüft zunächst, ob die Aufgabe existiert, bevor sie gelöscht wird.
     *
     * @param id die ID der zu löschenden Aufgabe
     * @throws ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        taskRepository.deleteById(id);
    }

    /**
     * Markiert eine Aufgabe als abgeschlossen.
     *
     * Setzt das completed-Flag auf true und speichert die Änderung.
     *
     * @param id die ID der Aufgabe
     * @return die aktualisierte Aufgabe mit completed=true als DTO
     * @throws ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @Override
    public TaskDto completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.TRUE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    /**
     * Markiert eine Aufgabe als nicht abgeschlossen.
     *
     * Setzt das completed-Flag auf false und speichert die Änderung.
     *
     * @param id die ID der Aufgabe
     * @return die aktualisierte Aufgabe mit completed=false als DTO
     * @throws ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @Override
    public TaskDto inCompleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.FALSE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }
}