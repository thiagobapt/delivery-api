package com.devlivery_api.Projeto.Delivery.API.service;

import com.devlivery_api.Projeto.Delivery.API.entity.Client;
import com.devlivery_api.Projeto.Delivery.API.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        
        Client.setAtivo(true);

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
        return ClientRepository.findByAtivoTrue();
    }

    public Client update(Long id, Client UpdatedClient) {
        Client Client = searchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        if (!Client.getEmail().equals(UpdatedClient.getEmail()) &&
                ClientRepository.existsByEmail(UpdatedClient.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + UpdatedClient.getEmail());
        }


        Client.setNome(UpdatedClient.getNome());
        Client.setEmail(UpdatedClient.getEmail());
        Client.setTelefone(UpdatedClient.getTelefone());
        Client.setEndereco(UpdatedClient.getEndereco());

        return ClientRepository.save(Client);
    }

    /**
     * Inativar Client (soft delete)
     */
    public void deactivate(Long id) {
        Client Client = searchById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        Client.inativar();
        ClientRepository.save(Client);
    }

    /**
     * Buscar Clients por nome
     */
    @Transactional(readOnly = true)
    public List<Client> searchByName(String name) {
        return ClientRepository.findByNomeContainingIgnoreCase(name);
    }

    /**
     * Validações de negócio
     */
    private void validateClientData(Client Client) {
        if (Client.getNome() == null || Client.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (Client.getEmail() == null || Client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (Client.getNome().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
    }
}
