package com.stefanini.desafio.dto.response;

import com.stefanini.desafio.entity.TaskEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TaskResponseDTO {
    private UUID id;

    private String title;

    private String description;

    private String status;

    private Date dateCreate;

    public TaskResponseDTO(TaskEntity task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.dateCreate = task.getDateCreate();
    }
}
