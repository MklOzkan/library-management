package com.project.librarymanagement.payload.messages;

import java.util.Locale;

public class ErrorMessages {

    public static final String NOT_FOUND_BOOK_MESSAGE = "Error: Book with id, %s not found";

    //author

    public static final String NOT_FOUND_AUTHOR_MESSAGE = "Error: Author with id %s not found";

    public static final String NOT_FOUND_PUBLISHER_MESSAGE = "Error: Publisher with id %s not found";
    public static final String ALREADY_CREATED_PUBLISHER_MESSAGE = "Error: Publisher with id %s is already created";
    public static final String NOT_FOUND_CATEGORY_MESSAGE = "Error: Category with id %d is not found";
    public static final String NOT_FOUND_CATEGORY_MESSAGE_BY_NAME = "Error: Category with name %s is not found";
    public static final String CANNOT_HAVE_MORE_BOOKS = "Error: Can not have more books name %d is not found";
    public static final String NOT_FOUND_USER_MESSAGE = "Error:User not found name %s is not found";
}