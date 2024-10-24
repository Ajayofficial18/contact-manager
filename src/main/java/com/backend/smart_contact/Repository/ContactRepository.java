package com.backend.smart_contact.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.smart_contact.Entities.Contact;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

    @Query("from Contact as c where c.user.id=:userId")
    public List<Contact> findContactByUser(@Param("userId") int userId);

}
