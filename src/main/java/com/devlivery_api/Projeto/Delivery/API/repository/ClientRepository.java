package com.devlivery_api.Projeto.Delivery.API.repository;

import com.devlivery_api.Projeto.Delivery.API.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
