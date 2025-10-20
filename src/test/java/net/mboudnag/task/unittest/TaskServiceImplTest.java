package net.mboudnag.task.unittest;

import net.mboudnag.task.dto.TaskDto;
import net.mboudnag.task.entity.Task;
import net.mboudnag.task.exception.ResourceNotFoundException;
import net.mboudnag.task.repository.TaskRepository;
import net.mboudnag.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit-Tests für die TaskServiceImpl-Klasse.
 * <p>
 * Diese Testklasse verwendet Mockito, um die Abhängigkeiten zu mocken
 * und die Geschäftslogik isoliert zu testen.
 * </p>
 *
 * @author Mboudnag
 * @version 1.0
 * @since 2023
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Unit Tests")
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    /**
     * Initialisiert Testdaten vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Test Description");
        taskDto.setCompleted(false);
    }

    /**
     * Testet das erfolgreiche Hinzufügen einer neuen Aufgabe.
     */
    @Test
    @DisplayName("Should successfully add a new task")
    void addTask_Success() {
        // Arrange
        when(modelMapper.map(taskDto, Task.class)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        // Act
        TaskDto result = taskService.addTask(taskDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        verify(taskRepository, times(1)).save(task);
        verify(modelMapper, times(2)).map(any(), any());
    }

    /**
     * Testet das erfolgreiche Abrufen einer Aufgabe anhand ihrer ID.
     */
    @Test
    @DisplayName("Should successfully get task by ID")
    void getTask_Success() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        // Act
        TaskDto result = taskService.getTask(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Testet, ob eine ResourceNotFoundException geworfen wird,
     * wenn eine nicht existierende Aufgabe abgerufen wird.
     */
    @Test
    @DisplayName("Should throw ResourceNotFoundException when task not found")
    void getTask_NotFound_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTask(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id:999");
        verify(taskRepository, times(1)).findById(999L);
    }

    /**
     * Testet das erfolgreiche Abrufen aller Aufgaben.
     */
    @Test
    @DisplayName("Should successfully get all tasks")
    void getAllTasks_Success() {
        // Arrange
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Second Task");
        task2.setDescription("Second Description");
        task2.setCompleted(true);

        TaskDto taskDto2 = new TaskDto();
        taskDto2.setId(2L);
        taskDto2.setTitle("Second Task");
        taskDto2.setDescription("Second Description");
        taskDto2.setCompleted(true);

        List<Task> tasks = Arrays.asList(task, task2);

        when(taskRepository.findAll()).thenReturn(tasks);
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);
        when(modelMapper.map(task2, TaskDto.class)).thenReturn(taskDto2);

        // Act
        List<TaskDto> result = taskService.getAllTasks();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task");
        assertThat(result.get(1).getTitle()).isEqualTo("Second Task");
        verify(taskRepository, times(1)).findAll();
    }

    /**
     * Testet das erfolgreiche Aktualisieren einer Aufgabe.
     */
    @Test
    @DisplayName("Should successfully update an existing task")
    void updateTask_Success() {
        // Arrange
        TaskDto updatedDto = new TaskDto();
        updatedDto.setTitle("Updated Title");
        updatedDto.setDescription("Updated Description");
        updatedDto.setCompleted(true);

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(modelMapper.map(updatedTask, TaskDto.class)).thenReturn(updatedDto);

        // Act
        TaskDto result = taskService.updateTask(updatedDto, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.isCompleted()).isTrue();
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Testet, ob eine ResourceNotFoundException geworfen wird,
     * wenn eine nicht existierende Aufgabe aktualisiert werden soll.
     */
    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing task")
    void updateTask_NotFound_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.updateTask(taskDto, 999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id : 999");
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    /**
     * Testet das erfolgreiche Löschen einer Aufgabe.
     */
    @Test
    @DisplayName("Should successfully delete a task")
    void deleteTask_Success() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    /**
     * Testet, ob eine ResourceNotFoundException geworfen wird,
     * wenn eine nicht existierende Aufgabe gelöscht werden soll.
     */
    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing task")
    void deleteTask_NotFound_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.deleteTask(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id : 999");
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).deleteById(any());
    }

    /**
     * Testet das erfolgreiche Markieren einer Aufgabe als abgeschlossen.
     */
    @Test
    @DisplayName("Should successfully mark task as completed")
    void completeTask_Success() {
        // Arrange
        Task completedTask = new Task();
        completedTask.setId(1L);
        completedTask.setTitle("Test Task");
        completedTask.setDescription("Test Description");
        completedTask.setCompleted(true);

        TaskDto completedDto = new TaskDto();
        completedDto.setId(1L);
        completedDto.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(completedTask);
        when(modelMapper.map(completedTask, TaskDto.class)).thenReturn(completedDto);

        // Act
        TaskDto result = taskService.completeTask(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isCompleted()).isTrue();
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Testet das erfolgreiche Markieren einer Aufgabe als nicht abgeschlossen.
     */
    @Test
    @DisplayName("Should successfully mark task as incomplete")
    void inCompleteTask_Success() {
        // Arrange
        task.setCompleted(true); // Starte mit abgeschlossener Aufgabe

        Task incompleteTask = new Task();
        incompleteTask.setId(1L);
        incompleteTask.setTitle("Test Task");
        incompleteTask.setDescription("Test Description");
        incompleteTask.setCompleted(false);

        TaskDto incompleteDto = new TaskDto();
        incompleteDto.setId(1L);
        incompleteDto.setCompleted(false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(incompleteTask);
        when(modelMapper.map(incompleteTask, TaskDto.class)).thenReturn(incompleteDto);

        // Act
        TaskDto result = taskService.inCompleteTask(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isCompleted()).isFalse();
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    /**
     * Testet, ob eine ResourceNotFoundException geworfen wird,
     * wenn eine nicht existierende Aufgabe als abgeschlossen markiert werden soll.
     */
    @Test
    @DisplayName("Should throw ResourceNotFoundException when completing non-existing task")
    void completeTask_NotFound_ThrowsException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.completeTask(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id : 999");
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }
}