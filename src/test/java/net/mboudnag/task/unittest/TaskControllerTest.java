package net.mboudnag.task.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mboudnag.task.controller.TaskController;
import net.mboudnag.task.dto.TaskDto;
import net.mboudnag.task.exception.ResourceNotFoundException;
import net.mboudnag.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit-Tests für den TaskController.
 * <p>
 * Diese Testklasse verwendet MockMvc, um die REST-Endpunkte
 * ohne den gesamten Application Context zu testen.
 * </p>
 *
 * @author Mboudnag
 * @version 1.0
 * @since 2023
 */
@WebMvcTest(TaskController.class)
@DisplayName("TaskController Unit Tests")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDto taskDto;

    /**
     * Initialisiert Testdaten vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Test Description");
        taskDto.setCompleted(false);
    }

    /**
     * Testet den POST-Endpunkt zum Erstellen einer neuen Aufgabe.
     */
    @Test
    @DisplayName("POST /api/tasks - Should create new task successfully")
    void addTask_Success() throws Exception {
        // Arrange
        when(taskService.addTask(any(TaskDto.class))).thenReturn(taskDto);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.completed", is(false)));

        verify(taskService, times(1)).addTask(any(TaskDto.class));
    }

    /**
     * Testet den GET-Endpunkt zum Abrufen einer einzelnen Aufgabe.
     */
    @Test
    @DisplayName("GET /api/tasks/{id} - Should return task by ID")
    void getTask_Success() throws Exception {
        // Arrange
        when(taskService.getTask(1L)).thenReturn(taskDto);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.completed", is(false)));

        verify(taskService, times(1)).getTask(1L);
    }

    /**
     * Testet den GET-Endpunkt, wenn die Aufgabe nicht gefunden wird.
     */
    @Test
    @DisplayName("GET /api/tasks/{id} - Should return 404 when task not found")
    void getTask_NotFound() throws Exception {
        // Arrange
        when(taskService.getTask(999L))
                .thenThrow(new ResourceNotFoundException("Task not found with id:999"));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTask(999L);
    }

    /**
     * Testet den GET-Endpunkt zum Abrufen aller Aufgaben.
     */
    @Test
    @DisplayName("GET /api/tasks - Should return all tasks")
    void getAllTasks_Success() throws Exception {
        // Arrange
        TaskDto taskDto2 = new TaskDto();
        taskDto2.setId(2L);
        taskDto2.setTitle("Second Task");
        taskDto2.setDescription("Second Description");
        taskDto2.setCompleted(true);

        List<TaskDto> tasks = Arrays.asList(taskDto, taskDto2);
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Test Task")))
                .andExpect(jsonPath("$[1].title", is("Second Task")))
                .andExpect(jsonPath("$[1].completed", is(true)));

        verify(taskService, times(1)).getAllTasks();
    }

    /**
     * Testet den GET-Endpunkt, wenn keine Aufgaben vorhanden sind.
     */
    @Test
    @DisplayName("GET /api/tasks - Should return empty list when no tasks exist")
    void getAllTasks_EmptyList() throws Exception {
        // Arrange
        when(taskService.getAllTasks()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(taskService, times(1)).getAllTasks();
    }

    /**
     * Testet den PUT-Endpunkt zum Aktualisieren einer Aufgabe.
     */
    @Test
    @DisplayName("PUT /api/tasks/{id} - Should update existing task")
    void updateTask_Success() throws Exception {
        // Arrange
        TaskDto updatedDto = new TaskDto();
        updatedDto.setId(1L);
        updatedDto.setTitle("Updated Title");
        updatedDto.setDescription("Updated Description");
        updatedDto.setCompleted(true);

        when(taskService.updateTask(any(TaskDto.class), eq(1L))).thenReturn(updatedDto);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.description", is("Updated Description")))
                .andExpect(jsonPath("$.completed", is(true)));

        verify(taskService, times(1)).updateTask(any(TaskDto.class), eq(1L));
    }

    /**
     * Testet den PUT-Endpunkt, wenn die zu aktualisierende Aufgabe nicht existiert.
     */
    @Test
    @DisplayName("PUT /api/tasks/{id} - Should return 404 when updating non-existing task")
    void updateTask_NotFound() throws Exception {
        // Arrange
        when(taskService.updateTask(any(TaskDto.class), eq(999L)))
                .thenThrow(new ResourceNotFoundException("Task not found with id : 999"));

        // Act & Assert
        mockMvc.perform(put("/api/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(any(TaskDto.class), eq(999L));
    }

    /**
     * Testet den DELETE-Endpunkt zum Löschen einer Aufgabe.
     */
    @Test
    @DisplayName("DELETE /api/tasks/{id} - Should delete task successfully")
    void deleteTask_Success() throws Exception {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("task deleted successfully!."));

        verify(taskService, times(1)).deleteTask(1L);
    }

    /**
     * Testet den DELETE-Endpunkt, wenn die zu löschende Aufgabe nicht existiert.
     */
    @Test
    @DisplayName("DELETE /api/tasks/{id} - Should return 404 when deleting non-existing task")
    void deleteTask_NotFound() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Task not found with id : 999"))
                .when(taskService).deleteTask(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(999L);
    }

    /**
     * Testet den PATCH-Endpunkt zum Markieren einer Aufgabe als abgeschlossen.
     */
    @Test
    @DisplayName("PATCH /api/tasks/{id}/complete - Should mark task as completed")
    void completeTask_Success() throws Exception {
        // Arrange
        TaskDto completedDto = new TaskDto();
        completedDto.setId(1L);
        completedDto.setTitle("Test Task");
        completedDto.setDescription("Test Description");
        completedDto.setCompleted(true);

        when(taskService.completeTask(1L)).thenReturn(completedDto);

        // Act & Assert
        mockMvc.perform(patch("/api/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(true)));

        verify(taskService, times(1)).completeTask(1L);
    }

    /**
     * Testet den PATCH-Endpunkt zum Markieren einer Aufgabe als nicht abgeschlossen.
     */
    @Test
    @DisplayName("PATCH /api/tasks/{id}/in-complete - Should mark task as incomplete")
    void inCompleteTask_Success() throws Exception {
        // Arrange
        TaskDto incompleteDto = new TaskDto();
        incompleteDto.setId(1L);
        incompleteDto.setTitle("Test Task");
        incompleteDto.setDescription("Test Description");
        incompleteDto.setCompleted(false);

        when(taskService.inCompleteTask(1L)).thenReturn(incompleteDto);

        // Act & Assert
        mockMvc.perform(patch("/api/tasks/1/in-complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(false)));

        verify(taskService, times(1)).inCompleteTask(1L);
    }

    /**
     * Testet den PATCH-Endpunkt, wenn die Aufgabe nicht gefunden wird.
     */
    @Test
    @DisplayName("PATCH /api/tasks/{id}/complete - Should return 404 when task not found")
    void completeTask_NotFound() throws Exception {
        // Arrange
        when(taskService.completeTask(999L))
                .thenThrow(new ResourceNotFoundException("Task not found with id : 999"));

        // Act & Assert
        mockMvc.perform(patch("/api/tasks/999/complete"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).completeTask(999L);
    }
}