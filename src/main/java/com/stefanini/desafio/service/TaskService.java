package com.stefanini.desafio.service;

import com.stefanini.desafio.dto.request.TaskRequestDTO;
import com.stefanini.desafio.dto.response.TaskResponseDTO;
import com.stefanini.desafio.entity.TaskEntity;
import com.stefanini.desafio.exception.ResourceNotFoundException;
import com.stefanini.desafio.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class TaskService  implements TaskServiceI{
    @Autowired
    private TaskRepository repository;


    @Override
    public TaskResponseDTO findById(UUID id) {
        TaskEntity task = findTask(id);
        return new TaskResponseDTO(task);
    }

    @Override
    public List<TaskResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(t -> new TaskResponseDTO(t))
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO register(TaskRequestDTO taskDTO) {
            TaskEntity task = new TaskEntity();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());

            return new TaskResponseDTO(repository.save(task));
    }

    @Override
    public TaskResponseDTO update(TaskRequestDTO taskDTO, UUID id) {
        TaskEntity task = findTask(id);

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        return new TaskResponseDTO(repository.save(task));
    }

    @Override
    public void deleteById(UUID id) {
        findTask(id);
        repository.deleteById(id);
    }

    public TaskEntity findTask(UUID id) {
       return (repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há tarefa com esse ID no banco de dados!")));
    }
}
