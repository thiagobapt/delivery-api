package com.devlivery_api.Projeto.Delivery.API.controller;

import com.devlivery_api.Projeto.Delivery.API.entity.Client;
import com.devlivery_api.Projeto.Delivery.API.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientService clientService;

    /**
     * Cadastrar novo client
     */
    @PostMapping
    public ResponseEntity<?> register(@Validated @RequestBody Client client) {
        try {
            Client clientSalvo = clientService.register(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Listar todos os clients ativos
     */
    @GetMapping
    public ResponseEntity<List<Client>> list() {
        List<Client> clients = clientService.listActive();
        return ResponseEntity.ok(clients);
    }

    /**
     * Buscar client por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id) {
        Optional<Client> client = clientService.searchById(id);

        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualizar client
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Validated @RequestBody Client client) {
        try {
            Client updatedClient = clientService.update(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Inativar client (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            clientService.deactivate(id);
            return ResponseEntity.ok().body("Client inativado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Buscar clients por nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Client>> searchByName(@RequestParam String name) {
        List<Client> clients = clientService.searchByName(name);
        return ResponseEntity.ok(clients);
    }

    /**
     * Buscar client por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> searchByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.searchByEmail(email);

        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
