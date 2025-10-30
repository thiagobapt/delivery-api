package com.devlivery_api.Projeto.Delivery.API.service;

import com.devlivery_api.Projeto.Delivery.API.entity.Client;
import com.devlivery_api.Projeto.Delivery.API.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    @Autowired
    private ClientRepository ClientRepository;

    public Client register(Client Client) {

        if (ClientRepository.existsByEmail(Client.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + Client.getEmail());
        }

        validateClientData(Client);
        
        Client.setActive(true);

        return ClientRepository.save(Client);
    }

    @Transactional(readOnly = true)
    public Optional<Client> searchById(Long id) {
        return ClientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Client> searchByEmail(String email) {
        return ClientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Client> listActive() {
        return ClientRepository.findByActiveTrue();
    }

    public Client update(Long id, Client UpdatedClient) {
        Client Client = searchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        if (!Client.getEmail().equals(UpdatedClient.getEmail()) &&
                ClientRepository.existsByEmail(UpdatedClient.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + UpdatedClient.getEmail());
        }


        Client.setName(UpdatedClient.getName());
        Client.setEmail(UpdatedClient.getEmail());
        Client.setPhone(UpdatedClient.getPhone());
        Client.setAddress(UpdatedClient.getAddress());

        return ClientRepository.save(Client);
    }

    public void deactivate(Long id) {
        Client Client = searchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        Client.deactivate();
        ClientRepository.save(Client);
    }

    @Transactional(readOnly = true)
    public List<Client> searchByName(String name) {
        return ClientRepository.findByNameContainingIgnoreCase(name);
    }

    private void validateClientData(Client Client) {
        if (Client.getName() == null || Client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory");
        }

        if (Client.getEmail() == null || Client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is mandatory");
        }

        if (Client.getName().length() < 2) {
            throw new IllegalArgumentException("Name must have at least 2 characters");
        }
    }
}
