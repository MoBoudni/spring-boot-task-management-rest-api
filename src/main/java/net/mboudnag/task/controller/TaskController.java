package net.mboudnag.task.controller;

import lombok.AllArgsConstructor;
import net.mboudnag.task.dto.TaskDto;
import net.mboudnag.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    // Build Add task REST API

    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto){

        TaskDto savedTask = taskService.addTask(taskDto);

        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    // Build Get task REST API
    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask
(@PathVariable("id") Long taskId){
        TaskDto taskDto = taskService.getTask(taskId);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    // Build Get All tasks REST API
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskDto> tasks = taskService.getAllTasks();
        //return new ResponseEntity<>(tasks, HttpStatus.OK);
        return ResponseEntity.ok(tasks);
    }

    // Build Update task REST API
    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable("id") Long taskId){
        TaskDto updatedTask

 = taskService.updateTask(taskDto, taskId);
        return ResponseEntity.ok(updatedTask

);
    }

    // Build Delete task REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("task deleted successfully!.");
    }

    // Build Complete task REST API
    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("id") Long taskId){
        TaskDto updatedTask

 = taskService.completeTask(taskId);
        return ResponseEntity.ok(updatedTask

);
    }

    // Build In Complete task REST API
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TaskDto> inCompleteTask(@PathVariable("id") Long taskId){
        TaskDto updatedTask

 = taskService.inCompleteTask(taskId);
        return ResponseEntity.ok(updatedTask);
    }

}
