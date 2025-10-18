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

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private ModelMapper modelMapper;

    @Override
    public TaskDto addTask(TaskDto taskDto) {

        // convert TaskDto into Task Jpa entity
        Task task = modelMapper.map(taskDto, Task.class);

        // Task Jpa entity
        Task savedTask = taskRepository.save(task);

        // Convert saved Task Jpa entity object into TaskDto object

        TaskDto savedTaskDto = modelMapper.map(savedTask, TaskDto.class);

        return savedTaskDto;
    }

    @Override
    public TaskDto getTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id:" + id));

        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map((task) -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, Long id) {

         Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));
         task.setTitle(taskDto.getTitle());
         task.setDescription(taskDto.getDescription());
         task.setCompleted(taskDto.isCompleted());

         Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto completeTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.TRUE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }
    @Override
    public TaskDto inCompleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : " + id));

        task.setCompleted(Boolean.FALSE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }
}
