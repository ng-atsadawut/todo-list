package com.codeng.todo.repository;

import com.codeng.todo.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAll();

    List<Task> findByOwner_UsernameOrOwner_Email(String username, String email);

    List<Task> findByCompletedTrue();

    List<Task> findByCompletedFalse();

    List<Task> findByTitleIsContainingAndOwner_UsernameOrOwner_Email(String name, String username, String email);
}
