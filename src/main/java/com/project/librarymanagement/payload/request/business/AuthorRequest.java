package com.project.librarymanagement.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {
    @NotNull(message = "Please enter Author's name")
    @Size(min = 4,max = 70,message = "Author's name should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Author's name must consist of the character .")
    private  String name;

    @NotNull(message = "builtIn should not be null")
    private Boolean builtIn;



}
