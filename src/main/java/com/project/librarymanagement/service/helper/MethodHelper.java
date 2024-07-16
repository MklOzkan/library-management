package com.project.librarymanagement.service.helper;

import com.project.librarymanagement.entity.business.*;
import com.project.librarymanagement.entity.enums.RoleType;
import com.project.librarymanagement.entity.user.Role;
import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.exception.BadRequestException;
import com.project.librarymanagement.exception.ResourceNotFoundException;
import com.project.librarymanagement.payload.messages.ErrorMessages;
import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.repository.business.*;
import com.project.librarymanagement.repository.user.RoleRepository;
import com.project.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;


    private final ReportResponse reportResponse;

    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Role findRoleByName(String roleName){
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    public boolean checkUserIfMember(User user){
        List<Role> roles = user.getRoles().stream().toList();

        return roles.contains(RoleType.MEMBER);
    }

    public void checkBuildIn(User user) {
        if (user.getBuiltIn()) {
            throw new BadRequestException("You do not have any permission to do this operation");
        }
    }

    public Book isBookExist(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOK_MESSAGE, id)));
    }

public Author isAuthorExist(Long id){
        return authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_AUTHOR_MESSAGE,id)));
}

    public Publisher getPublisherById(Long id){
        return publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,id)));
    }

    public boolean isPublisherExist(Long id) {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,id));
        }

        return true;
    }

    public void isPublisherExistByName(String name) {
        if (publisherRepository.existsByName(name)) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.ALREADY_CREATED_PUBLISHER_MESSAGE, name));
        }
    }

    public List<Book> findBooksByIds(List<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    public Category isCategoryExist(Long id){
        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_MESSAGE,id)));
    }

    public void isCategoryExistByName(String name){
        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_MESSAGE_BY_NAME, name));
        }
    }

    public Loan findLoanById(Long id){
        return loanRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan not found with id: " + id));
    }

    public Loan findLoanByUserId(Long userId){
        return loanRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Loan not found with user id: " + userId));
    }

    public void isLoanExistById(Long id){
        if(!loanRepository.existsById(id)){
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
    }

    public void isLoanExistByUserId(Long userId){
        if(!loanRepository.existsByUserId(userId)){
            throw new ResourceNotFoundException("Loan not found with user id: " + userId);
        }
    }

    public Publisher isPublisherExists(Long id) {
        return publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,id)));
    }

    public ReportResponse gerReport()
    {
        reportResponse .builder()
                .books(getBookCount())
                .authors(getAuthorsCount())
                .publishers(getPublishersCount())
                .categories(getCategoriesCount())
                .loans(getLoansCount())
                .unReturnedBooks(getUnreturnedBookCounts())
                .expiredBooks(getExpiredBooksCount())
                .members(getMemebersCount())
                .build();
        return  reportResponse;
    }

    //find all book count
    public int getBookCount ()
    {
        return (int) bookRepository.count();
    }

    public int getAuthorsCount ()
    {
        return (int) authorRepository.count();
    }

    public int getPublishersCount ()
    {
        return (int) publisherRepository.count();
    }

    public int getCategoriesCount ()
    {
        return (int) categoryRepository.count();
    }

    public int getLoansCount ()
    {
        return (int) loanRepository.count();
    }

    public int getUnreturnedBookCounts()
    {
        return  bookRepository.getUnrentedBookCount().intValue();
    }



    public int getExpiredBooksCount ()
    {
        return loanRepository.getDateExpiredBookCount().intValue();
    }

    public int getMemebersCount ()
    {
        return (int) loanRepository.count();
    }





}
