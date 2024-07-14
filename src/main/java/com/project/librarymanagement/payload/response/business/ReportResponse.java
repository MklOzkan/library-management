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

//    {
//        books: 1231,
//                authors: 234,
//            publishers:232,
//            categories: 12,
//            loans: 1324,
//            unReturnedBooks: 124,
//            expiredBooks: 12,
//            members: 1322
//    }

    private int books;
    private int authors;
    private int publishers;
    private int categories;
    private int loans;
    private int unReturnedBooks;
    private int expiredBooks;
    private int members;




}
