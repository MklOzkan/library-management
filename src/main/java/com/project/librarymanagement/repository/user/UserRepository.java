package com.project.librarymanagement.repository.user;

import com.project.librarymanagement.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);


    @Query(" SELECT u FROM User  u ORDER BY COUNT(u.borrowCount) DESC")
    Page<User> getMostBarowerUsers(Pageable pageable);
}
