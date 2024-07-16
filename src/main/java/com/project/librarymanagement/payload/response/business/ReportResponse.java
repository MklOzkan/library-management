package com.project.librarymanagement.payload.response.business;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {



    private int books;
    private int authors;
    private int publishers;
    private int categories;
    private int loans;
    private int unReturnedBooks;
    private int expiredBooks;
    private int members;




}
