package com.project.librarymanagement.payload.response.business;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {

    private String name;


    private String isbn;
    private Integer pageCount;
    private Author author;
    private Publisher publisher;
    private Integer publishDate;
    private Category category;
    private File image;
    private Boolean loanable;
    private String shelfCode;
    private Boolean active;
    private Boolean featured;
    private LocalDateTime createDate;
    private Boolean builtIn;
}
