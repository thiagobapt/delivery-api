package com.devlivery_api.Projeto.Delivery.API.repository;

import com.devlivery_api.Projeto.Delivery.API.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Buscar cliente por email (método derivado)
    Optional<Client> findByEmail(String email);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Buscar clientes ativos
    List<Client> findByActiveTrue();

    // Buscar clientes por nome (contendo)
    List<Client> findByNameContainingIgnoreCase(String nome);
}
