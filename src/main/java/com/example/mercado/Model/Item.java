package com.example.mercado.Model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Nonnull
    private Double price;

    @OneToOne
    private Person dono;

    @Enumerated(EnumType.STRING)
    private Raridade raridade;}
