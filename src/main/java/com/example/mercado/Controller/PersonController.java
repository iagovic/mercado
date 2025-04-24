package com.example.mercado.Controller;

import ch.qos.logback.classic.Logger;
import com.example.mercado.Model.Person;
import com.example.mercado.Model.PersonGender;
import com.example.mercado.Repository.PersonRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("persons")
@Slf4j
public class PersonController {
    Logger log;

    @Autowired
    private PersonRepository repository;

    @GetMapping
    @Operation(summary = "Listar personagens", description = "Retorna uma lista com todos os personagens")
    @Cacheable("persons")
    public List<Person> index() {
        return repository.findAll();
    }
    @PostMapping
    @CacheEvict(value = "persons", allEntries = true)
    @Operation(summary = "Cadastrar personagem", responses = {
            @ApiResponse(responseCode = "400", description = "Validação falhou")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person create(@RequestBody @Valid Person person) {
        log.info("Cadastrando personagem ");
        return repository.save(person);
    }
    @GetMapping("search/gender")
    @Operation(summary = "Buscar personagem por classe", description = "Busca personagens pela classe (gender)")
    public ResponseEntity<List<Person>> searchByGender(@RequestParam PersonGender gender) {
        log.info("Buscando personagem com classe={}", gender);
        List<Person> results = repository.findByGender(gender);
        return ResponseEntity.ok(results);
    }

    @GetMapping("search/level")
    @Operation(summary = "Buscar personagem por nível", description = "Busca personagens por nível (1 a 99)")
    public ResponseEntity<List<Person>> searchByLevel(@RequestParam Integer nivel) {
        if (nivel < 1 || nivel > 99) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nível deve estar entre 1 e 99");
        }
        log.info("Buscando personagem com nível={}", nivel);
        List<Person> results = repository.findByNivel(nivel);
        return ResponseEntity.ok(results);
    }
    @GetMapping("search/coins")
    @Operation(summary = "Buscar personagem por moedas", description = "Busca personagens por saldo de moedas (maior ou igual)")
    public ResponseEntity<List<Person>> searchByCoins(@RequestParam Double moedas) {
        log.info("Buscando personagem com moedas maiores ou iguais a {}", moedas);
        List<Person> results = repository.findByMoedasGreaterThanEqual(moedas);
        return ResponseEntity.ok(results);
    }



}

//exemplopara pesquisa
///search/name?name=NomePersonagem
//
///search/gender?gender=GUERREIRO
//
///search/level?nivel=25
//
///search/coins?moedas=50.0