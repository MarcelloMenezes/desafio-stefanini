package com.stefanini.desafio.service;

import com.stefanini.desafio.dto.request.TaskRequestDTO;
import com.stefanini.desafio.dto.response.TaskResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TaskServiceI {
    TaskResponseDTO findById(UUID id);

    List<TaskResponseDTO> findAll();

    TaskResponseDTO register(TaskRequestDTO taskDTO);

    TaskResponseDTO update(TaskRequestDTO taskDTO, UUID id);

    void deleteById(UUID id);

}
