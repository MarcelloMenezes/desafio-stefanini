package com.stefanini.desafio.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.ErrorResponse;

import java.util.Date;
import java.util.UUID;

@Table(name="Tasks")
@Entity(name="Tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= "id")
public class TaskEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String description;

    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_create")
    private Date dateCreate;

    @PrePersist void onCreate() {
        this.dateCreate = new Date();
    }
}
