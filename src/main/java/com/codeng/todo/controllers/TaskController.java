package com.codeng.todo.controllers;

import com.codeng.todo.models.Task;
import com.codeng.todo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    public TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(value = "title", defaultValue = "", required = false) String name){
        if(name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTask());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByTitle(name));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @GetMapping("/complete")
    public ResponseEntity<List<Task>> getAllTaskComplete(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllComplete());
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getAllTaskInComplete(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllInComplete());
    }

    @PostMapping("")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody Task data){
        Optional<Task> task = taskService.updateTask(id, data);
        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @PathVariable boolean status){
        Optional<Task> task = taskService.updateTaskStatus(id, status);
        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        if(taskService.deleteTask(id)){
            return  ResponseEntity.status(HttpStatus.OK).body(String.format("Delete task %s success !!", id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

}
