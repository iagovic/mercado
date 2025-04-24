package com.example.mercado.Repository;

import com.example.mercado.Model.Item;
import com.example.mercado.Model.ItemType;
import com.example.mercado.Model.Person;
import com.example.mercado.Model.Raridade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByType(ItemType type);
    List<Item> findByRaridade(Raridade raridade);
    List<Item> findByPriceGreaterThanEqual(Double price);
    List<Item> findByDono(Person dono);
}
