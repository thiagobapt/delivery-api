package com.devlivery_api.Projeto.Delivery.API.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(nullable = true)
    private Boolean active;

    public void deactivate() {
        this.active = false;
    }
}
