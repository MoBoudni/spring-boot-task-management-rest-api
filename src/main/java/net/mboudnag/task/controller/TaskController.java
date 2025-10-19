
package net.mboudnag.task.controller;

import lombok.AllArgsConstructor;
import net.mboudnag.task.dto.TaskDto;
import net.mboudnag.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für die Verwaltung von Aufgaben.
<<<<<<< HEAD
 *
 * Dieser Controller stellt REST-Endpunkte für CRUD-Operationen
 * (Create, Read, Update, Delete) sowie für die Statusverwaltung
 * von Aufgaben bereit.
 *
=======
 * 
 * Dieser Controller stellt REST-Endpunkte für CRUD-Operationen
 * (Create, Read, Update, Delete) sowie für die Statusverwaltung
 * von Aufgaben bereit.
 * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
 * @author Mboudnag
 * @version 2.0
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    /**
     * Erstellt eine neue Aufgabe.
<<<<<<< HEAD
     *
     * Dieser Endpunkt nimmt eine Aufgabe im JSON-Format entgegen
     * und speichert sie in der Datenbank.
     *
=======
     * 
     * Dieser Endpunkt nimmt eine Aufgabe im JSON-Format entgegen
     * und speichert sie in der Datenbank.
     * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
     * @param taskDto die zu erstellende Aufgabe als Data Transfer Object
     * @return ResponseEntity mit der gespeicherten Aufgabe und HTTP-Status 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        TaskDto savedTask = taskService.addTask(taskDto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    /**
     * Ruft eine einzelne Aufgabe anhand ihrer ID ab.
     *
     * @param taskId die eindeutige ID der Aufgabe
     * @return ResponseEntity mit der gefundenen Aufgabe und HTTP-Status 200 (OK)
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long taskId) {
        TaskDto taskDto = taskService.getTask(taskId);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    /**
     * Ruft alle vorhandenen Aufgaben ab.
     *
     * @return ResponseEntity mit einer Liste aller Aufgaben und HTTP-Status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Aktualisiert eine bestehende Aufgabe.
<<<<<<< HEAD
     *
     * Alle Felder der Aufgabe (Titel, Beschreibung, Status) werden
     * mit den neuen Werten überschrieben.
     *
=======
     * 
     * Alle Felder der Aufgabe (Titel, Beschreibung, Status) werden
     * mit den neuen Werten überschrieben.
     * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
     * @param taskDto die aktualisierten Aufgabendaten
     * @param taskId  die ID der zu aktualisierenden Aufgabe
     * @return ResponseEntity mit der aktualisierten Aufgabe und HTTP-Status 200 (OK)
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @PutMapping("{id}")
<<<<<<< HEAD
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto,
                                              @PathVariable("id") Long taskId) {
=======
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, 
                                               @PathVariable("id") Long taskId) {
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
        TaskDto updatedTask = taskService.updateTask(taskDto, taskId);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Löscht eine Aufgabe anhand ihrer ID.
     *
     * @param taskId die ID der zu löschenden Aufgabe
     * @return ResponseEntity mit Erfolgsmeldung und HTTP-Status 200 (OK)
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("task deleted successfully!.");
    }

    /**
     * Markiert eine Aufgabe als abgeschlossen.
<<<<<<< HEAD
     *
     * Setzt das completed-Flag der Aufgabe auf true.
     *
=======
     * 
     * Setzt das completed-Flag der Aufgabe auf true.
     * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
     * @param taskId die ID der Aufgabe, die als abgeschlossen markiert werden soll
     * @return ResponseEntity mit der aktualisierten Aufgabe und HTTP-Status 200 (OK)
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("id") Long taskId) {
        TaskDto updatedTask = taskService.completeTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Markiert eine Aufgabe als nicht abgeschlossen.
<<<<<<< HEAD
     *
     * Setzt das completed-Flag der Aufgabe auf false.
     *
=======
     * 
     * Setzt das completed-Flag der Aufgabe auf false.
     * 
>>>>>>> 7998a7dae4e14e974d2f365958a670588b69874f
     * @param taskId die ID der Aufgabe, die als nicht abgeschlossen markiert werden soll
     * @return ResponseEntity mit der aktualisierten Aufgabe und HTTP-Status 200 (OK)
     * @throws net.mboudnag.task.exception.ResourceNotFoundException wenn keine Aufgabe mit der ID existiert
     */
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TaskDto> inCompleteTask(@PathVariable("id") Long taskId) {
        TaskDto updatedTask = taskService.inCompleteTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }
}
