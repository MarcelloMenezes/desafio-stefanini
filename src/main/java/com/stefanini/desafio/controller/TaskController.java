package com.stefanini.desafio.controller;

import com.stefanini.desafio.dto.request.TaskRequestDTO;
import com.stefanini.desafio.dto.response.TaskResponseDTO;
import com.stefanini.desafio.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tarefas")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findByIdTask(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(service.findAll());
    }


    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Validated TaskRequestDTO data) {
        TaskResponseDTO response = service.register(data);

        return new ResponseEntity<TaskResponseDTO>(response, CREATED );
    }

    @PutMapping("/{id}")
    public  ResponseEntity<TaskResponseDTO> updateTask(@PathVariable("id") UUID id, @RequestBody @Validated TaskRequestDTO dto) {
        TaskResponseDTO updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") UUID id){
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
