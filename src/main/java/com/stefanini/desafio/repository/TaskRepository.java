package com.stefanini.desafio.repository;

import com.stefanini.desafio.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
}
