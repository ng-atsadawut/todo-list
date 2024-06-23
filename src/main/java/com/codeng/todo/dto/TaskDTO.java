package com.codeng.todo.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long ownerId;
    private String title;
    private String description;
    private boolean completed;
}
