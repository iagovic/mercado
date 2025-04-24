package com.example.mercado.Controller;

import ch.qos.logback.classic.Logger;
import com.example.mercado.Model.Item;
import com.example.mercado.Model.ItemType;
import com.example.mercado.Model.Person;
import com.example.mercado.Model.Raridade;
import com.example.mercado.Repository.ItemRepository;
import com.example.mercado.Repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ItemController {
    Logger log;
    @Autowired
    private ItemRepository repository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    @Operation(summary = "Listar todos os itens")
    public List<Item> index() {
        return repository.findAll();
    }
    @PostMapping
    @Operation(summary = "Cadastrar novo item")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        return repository.save(item);
    }
    @GetMapping("search/name")
    @Operation(summary = "Buscar item pelo nome", description = "Busca itens pelo nome (obrigatório)")
    public ResponseEntity<List<Item>> searchByName(@RequestParam String name) {
        log.info("Buscando item com nome={}", name);
        List<Item> results = repository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(results);
    }
    @GetMapping("search/type")
    @Operation(summary = "Buscar item por tipo", description = "Busca itens pelo tipo (arma, armadura, poção, acessório)")
    public ResponseEntity<List<Item>> searchByType(@RequestParam ItemType type) {
        log.info("Buscando item com tipo={}", type);
        List<Item> results = repository.findByType(type);
        return ResponseEntity.ok(results);
    }
    @GetMapping("search/rarity")
    @Operation(summary = "Buscar item por raridade", description = "Busca itens pela raridade (comum, raro, épico, lendário)")
    public ResponseEntity<List<Item>> searchByRarity(@RequestParam Raridade raridade) {
        log.info("Buscando item com raridade={}", raridade);
        List<Item> results = repository.findByRaridade(raridade);
        return ResponseEntity.ok(results);
    }
    @GetMapping("search/price")
    @Operation(summary = "Buscar item por preço", description = "Busca itens pelo preço (maior ou igual)")
    public ResponseEntity<List<Item>> searchByPrice(@RequestParam Double price) {
        log.info("Buscando item com preço maior ou igual a {}", price);
        List<Item> results = repository.findByPriceGreaterThanEqual(price);
        return ResponseEntity.ok(results);
    }
    @GetMapping("search/owner")
    @Operation(summary = "Buscar item por dono", description = "Busca itens pelo dono (personagem)")
    public ResponseEntity<List<Item>> searchByOwner(@RequestParam Long personId) {
        log.info("Buscando item pertencente ao personagem com id={}", personId);

        Person dono = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado"));

        List<Item> results = repository.findByDono(dono);
        return ResponseEntity.ok(results);
    }


}
