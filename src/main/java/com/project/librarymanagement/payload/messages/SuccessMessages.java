package com.project.librarymanagement.payload.messages;

public class SuccessMessages {

    //Book
    public static final String BOOK_SAVE = "Book is Saved Successfully";

    public static final String BOOK_UPDATE = "Book is Updated Successfully";
    public static final String  BOOK_DELETE = "Book is Deleted Successfully";

    //Author
    public static final String AUTHOR_SAVE = "Author is Saved";
    public static final String AUTHOR_UPDATE = "Author is Updated Successfully";
    public static final String AUTHOR_DELETE = "Author is Deleted Successfully";
    public static final String AUTHOR_FOUND = "Author is Found Successfully";

    // Publisher
    public static final String PUBLISHER_SAVE = "Publisher is Saved";
    public static final String PUBLISHER_UPDATE = "Publisher is Updated Successfully";
    public static final String PUBLISHER_DELETE = "Publisher is Deleted Successfully";
    public static final String PUBLISHER_FOUND = "Publisher is Found Successfully";

    //Loan
    public static final String LOAN_SAVE = "Loan is Saved Successfully";
    public static final String LOAN_UPDATE_BEFORE_EXPIRE_DATE = "Loan is Updated Successfully";
    public static final String LOAN_UPDATE_AFTER_EXPIRE_DATE = "Loan is Updated Successfully, and score is decreased by 1";
    public static final String LOAN_DELETE = "Loan is Deleted Successfully";
    public static final String LOAN_FOUND = "Loan is Found with Successfully";

    //Role

    public static final String ROLE_SAVE = "Role is Saved Successfully";

}
