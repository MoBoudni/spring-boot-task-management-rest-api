package net.mboudnag.task.unittest;

import net.mboudnag.task.repository.TaskRepository;
import net.mboudnag.task.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integrationstests für das TaskRepository.

 * Diese Testklasse verwendet @DataJpaTest, um nur die JPA-Schicht
 * zu testen. Es wird eine In-Memory-H2-Datenbank verwendet.

 * @author Mboudnag
 * @version 1.0
 */
@DataJpaTest
@DisplayName("TaskRepository Integration Tests")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Task task;

    /**
     * Initialisiert Testdaten vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);
    }

    /**
     * Testet das Speichern einer neuen Aufgabe.
     */
    @Test
    @DisplayName("Should save task successfully")
    void save_Success() {
        // Act
        Task savedTask = taskRepository.save(task);

        // Assert
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
        assertThat(savedTask.getDescription()).isEqualTo("Test Description");
        assertThat(savedTask.isCompleted()).isFalse();
    }

    /**
     * Testet das Abrufen einer Aufgabe anhand ihrer ID.
     */
    @Test
    @DisplayName("Should find task by ID")
    void findById_Success() {
        // Arrange
        Task savedTask = entityManager.persistAndFlush(task);

        // Act
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        // Assert
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getId()).isEqualTo(savedTask.getId());
        assertThat(foundTask.get().getTitle()).isEqualTo("Test Task");
    }

    /**
     * Testet das Abrufen einer nicht existierenden Aufgabe.
     */
    @Test
    @DisplayName("Should return empty when task not found by ID")
    void findById_NotFound() {
        // Act
        Optional<Task> foundTask = taskRepository.findById(999L);

        // Assert
        assertThat(foundTask).isEmpty();
    }

    /**
     * Testet das Abrufen aller Aufgaben.
     */
    @Test
    @DisplayName("Should find all tasks")
    void findAll_Success() {
        // Arrange
        Task task2 = new Task();
        task2.setTitle("Second Task");
        task2.setDescription("Second Description");
        task2.setCompleted(true);

        entityManager.persist(task);
        entityManager.persist(task2);
        entityManager.flush();

        // Act
        List<Task> tasks = taskRepository.findAll();

        // Assert
        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test Task");
        assertThat(tasks.get(1).getTitle()).isEqualTo("Second Task");
    }

    /**
     * Testet das Abrufen aller Aufgaben, wenn keine vorhanden sind.
     */
    @Test
    @DisplayName("Should return empty list when no tasks exist")
    void findAll_EmptyList() {
        // Act
        List<Task> tasks = taskRepository.findAll();

        // Assert
        assertThat(tasks).isEmpty();
    }

    /**
     * Testet das Aktualisieren einer Aufgabe.
     */
    @Test
    @DisplayName("Should update task successfully")
    void update_Success() {

        // Arrange
        Task savedTask = entityManager.persistAndFlush(task);
        savedTask.setTitle("Updated Task");
        savedTask.setDescription("Updated Description");
        savedTask.setCompleted(true);

        // Act
        Task updatedTask = taskRepository.save(savedTask);
        entityManager.flush(); // Stellt sicher, dass Änderungen in der DB sichtbar sind

        // Assert
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(savedTask.getId());
        assertThat(updatedTask.getTitle()).isEqualTo("Updated Task");
        assertThat(updatedTask.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedTask.isCompleted()).isTrue();
    }

    /**
     * Testet das Löschen einer Aufgabe.
     */
    @Test
    @DisplayName("Should delete task successfully")
    void delete_Success() {
        // Arrange
        Task savedTask = entityManager.persistAndFlush(task);

        // Act
        taskRepository.deleteById(savedTask.getId());

        // Assert
        assertThat(taskRepository.findById(savedTask.getId())).isEmpty();
    }
}
