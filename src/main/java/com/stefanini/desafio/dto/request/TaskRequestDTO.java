package com.stefanini.desafio.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskRequestDTO {
    private String title;

    private String description;

    private String status;

    private Date dateCreate;
}
