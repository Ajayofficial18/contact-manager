package com.backend.smart_contact.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.smart_contact.Entities.Contact;
import com.backend.smart_contact.Entities.User;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

    // for pagination
    @Query("from Contact as c where c.user.id=:userId")
    public Page<Contact> findContactByUser(@Param("userId") int userId,Pageable pageable);

    @Query("from Contact as c where c.user.id=:userId")
    public List<Contact> findContactByUser(@Param("userId") int userId);

    // search functionality
    public List<Contact> findByNameContainingAndUser(String name,User user);

}
