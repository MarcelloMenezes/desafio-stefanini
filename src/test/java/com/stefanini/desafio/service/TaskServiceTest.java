package com.stefanini.desafio.service;

import com.stefanini.desafio.dto.request.TaskRequestDTO;
import com.stefanini.desafio.dto.response.TaskResponseDTO;
import com.stefanini.desafio.entity.TaskEntity;
import com.stefanini.desafio.exception.ResourceNotFoundException;
import com.stefanini.desafio.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.config.Task;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void register_WhenDataIsValid_ShouldSaveTaskAndReturnDTO() {
        // Arrange
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Nova Tarefa");
        dto.setDescription("Descrição");
        dto.setStatus("PENDENTE");

        TaskEntity savedEntity = new TaskEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setTitle(dto.getTitle());
        savedEntity.setDescription(dto.getDescription());
        savedEntity.setStatus(dto.getStatus());

        when(repository.save(any(TaskEntity.class))).thenReturn(savedEntity);

        // Act
        TaskResponseDTO response = service.register(dto);

        // Assert
        assertNotNull(response);
        assertEquals("Nova Tarefa", response.getTitle());
        assertEquals("PENDENTE", response.getStatus());
        verify(repository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void register_WhenTitleIsNull_ShouldThrowIllegalArgumentException() {
        // Arrange
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle(null);
        dto.setDescription("Descrição");
        dto.setStatus("PENDENTE");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.register(dto);
        });

        assertEquals("Titulo é obrigatório", thrown.getMessage());
        verify(repository, never()).save(any(TaskEntity.class));
    }

    @Test
    void findAll_WhenTasksExist_ShouldReturnListOfTaskDTOs() {
        // Arrange
        TaskEntity task1 = new TaskEntity(UUID.randomUUID(), "Tarefa 1", "Descrição 1", "PENDENTE", new Date());
        TaskEntity task2 = new TaskEntity(UUID.randomUUID(), "Tarefa 2", "Descrição 2", "CONCLUIDA",  new Date());

        when(repository.findAll()).thenReturn(List.of(task1, task2));

        // Act
        List<TaskResponseDTO> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tarefa 1", result.get(0).getTitle());
        assertEquals("Tarefa 2", result.get(1).getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_WhenRepositoryThrowsException_ShouldPropagateRuntimeException() {
        // Arrange
        when(repository.findAll()).thenThrow(new RuntimeException("Erro ao acessar o banco"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.findAll();
        });

        assertEquals("Erro ao acessar o banco", thrown.getMessage());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update_WhenDataIsValid_ShouldUpdateTaskAndReturnDTO() {
        // Arrange
        UUID id = UUID.randomUUID();

        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(id);
        existingTask.setTitle("Título Antigo");
        existingTask.setDescription("Descrição Antiga");
        existingTask.setStatus("PENDENTE");

        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Título Novo");
        requestDTO.setDescription("Descrição Nova");
        requestDTO.setStatus("CONCLUIDA");

        when(repository.findById(id)).thenReturn(Optional.of(existingTask));
        when(repository.save(any(TaskEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponseDTO response = service.update(requestDTO, id);

        // Assert
        assertNotNull(response);
        assertEquals("Título Novo", response.getTitle());
        assertEquals("Descrição Nova", response.getDescription());
        assertEquals("CONCLUIDA", response.getStatus());

        verify(repository).findById(id);
        verify(repository).save(existingTask);
    }

    @Test
    void update_WhenTaskNotFound_ShouldThrowResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Título");
        requestDTO.setDescription("Descrição");
        requestDTO.setStatus("PENDENTE");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(requestDTO, id);
        });

        assertEquals("Não há tarefa com esse ID no banco de dados!", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deleteById_WhenTaskExists_ShouldDeleteTaskFromRepository() {
        // Arrange
        UUID id = UUID.randomUUID();
        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existingTask));

        // Act
        service.deleteById(id);

        // Assert
        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteById_WhenTaskNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
        });

        assertEquals("Não há tarefa com esse ID no banco de dados!", exception.getMessage());

        verify(repository).findById(id);
        verify(repository, never()).deleteById(any());
    }
}
