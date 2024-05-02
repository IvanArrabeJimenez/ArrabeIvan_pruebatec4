package com.ivanArrabe.AgenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // Indica que esta clase no será mapeada directamente a una tabla en la base de datos
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Estrategia de herencia: tabla por cada clase hija
public abstract class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    private Integer peopleQuantity;
    private Boolean deleted;//Para hacer el borrado lógico.
}
