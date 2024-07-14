package com.project.librarymanagement.payload.messages;

public class ErrorMessages {

    public static final String NOT_FOUND_BOOK_MESSAGE = "Error: Book with id, %s not found";

    //author

    public static final String NOT_FOUND_AUTHOR_MESSAGE = "Error: Author with id %s not found";

    public static final String NOT_FOUND_PUBLISHER_MESSAGE = "Error: Publisher with id %s not found";

    public static final String NOT_FOUND_PUBLISHER_MESSAGE_NAME = "Error: Publisher with name %s not found";
    //User
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User with id %s not found";

    //Loan
    public static final String NOT_FOUND_LOAN_MESSAGE = "Error: Loan with id %s not found";
    public static final String NOT_FOUND_LOAN_BY_USER_MESSAGE = "Error: Loan with user id %s not found";
    public static final String NOT_FOUND_LOAN_BY_BOOK_MESSAGE = "Error: Loan with book id %s not found";
    public static final String CANNOT_HAVE_MORE_BOOKS = "Error: User with id %s cannot have more than %s books";

    // Publisher

    public static final String ALREADY_EXISTS_PUBLISHER = "Error: Publisher with id  %s already exists";
}