package com.project.librarymanagement.entity.business;

import jakarta.persistence.*;

import com.project.librarymanagement.entity.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Categories categoryName;

    private Boolean builtIn;

    private int sequence;

}
