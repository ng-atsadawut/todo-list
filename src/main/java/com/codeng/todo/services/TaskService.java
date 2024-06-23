package com.codeng.todo.services;

import com.codeng.todo.models.Task;
import com.codeng.todo.models.User;
import com.codeng.todo.repository.TaskRepository;
import com.codeng.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    public UserRepository userRepository;

    public List<Task> getAllTask(String usernameOrEmail) {
        return taskRepository.findByOwner_UsernameOrOwner_Email(usernameOrEmail, usernameOrEmail);
//        return taskRepository.findAll();
    }

    public Task createTask(Task task, String usernameOrEmail){
        Optional<User> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        System.out.println(usernameOrEmail);
        System.out.println(user);
        task.setOwner(user.get());
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public List<Task> getAllComplete(){
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> getAllInComplete(){
        return taskRepository.findByCompletedFalse();
    }

    public List<Task> getTaskByTitle(String usernameOrEmail, String name){
        System.out.println("usernameOrEmail: " + usernameOrEmail);
        System.out.println("name: " + name);
        return taskRepository.findByTitleIsContainingAndOwner_UsernameOrOwner_Email(name, usernameOrEmail, usernameOrEmail);
    }

    public Optional<Task> updateTask(Long id, Task task){
        Optional<Task> getTask = taskRepository.findById(id);
        if(getTask.isEmpty()){
            return getTask;
        }
        task.setId(id);
        return Optional.of(taskRepository.save(task));
    }

    public Optional<Task> updateTaskStatus(Long id, boolean completeStatus){
        Optional<Task> getTask = taskRepository.findById(id);
        if(getTask.isEmpty()){
            return getTask;
        }

        Task taskUpdate = getTask.get();
        taskUpdate.setId(id);
        taskUpdate.setCompleted(completeStatus);

        return Optional.of(taskRepository.save(taskUpdate));
    }

    public boolean deleteTask(Long id){
        Optional<Task> getTask = taskRepository.findById(id);
        System.out.println("getTask----------------------------------");
        System.out.println(id);
        System.out.println(getTask);
        if(getTask.isEmpty()){
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

}
