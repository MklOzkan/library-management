package com.project.librarymanagement.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column (nullable = false)
        private String name;

        private Boolean builtIn;

        @OneToMany (mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonIgnore
        private Set<Book> books;

}
