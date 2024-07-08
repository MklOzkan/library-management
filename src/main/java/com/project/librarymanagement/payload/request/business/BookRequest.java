package com.project.librarymanagement.payload.request.business;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.librarymanagement.entity.business.Author;
import com.project.librarymanagement.entity.business.Category;
import com.project.librarymanagement.entity.business.Publisher;
import jakarta.validation.constraints.*;
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
public class BookRequest {

    @NotNull(message = "Name should not be null")
    @Size(min = 2, max = 80, message = "Name of the book should be between 2 and 80")
    private String name;

    @NotNull(message = "isbn should not be null")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d{1}$\n" ,message="isbn should be in format : 999-99-99999-99-9 .")
    private String isbn;
    private Integer pageCount;
    private Author author;
    private Publisher publisher;
    @NotNull(message = "publishDate should not be null")
    @Min(1000)
    @Max(9999)
    private Integer publishDate;
    @NotNull(message = "Category should not be null")
    private Category category;
    private File image;
    @NotNull(message = "loanable should not be null")
    private Boolean loanable;
    @NotNull(message = "shelfCode should not be null")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$\n" ,message="shelfCode should be in format : AA-999 .")
    private String shelfCode;
    @NotNull(message = "active should not be null")
    private Boolean active;
    @NotNull(message = "featured should not be null")
    private Boolean featured;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @NotNull(message = "builtIn should not be null")
    private Boolean builtIn;


}
