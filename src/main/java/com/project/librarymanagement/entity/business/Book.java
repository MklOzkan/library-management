package com.project.librarymanagement.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"loan"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String isbn;
    private Integer pageCount;
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;
    @ManyToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;
    private Integer publishDate;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    private File image;
    private Boolean loanable;
    private String shelfCode;
    private Boolean active;
    private Boolean featured;
    private LocalDateTime createDate;
    private Boolean builtIn;
    private int rentalAmount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_loan",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "loan_id"))
    private List<Loan> loans;

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }
}
