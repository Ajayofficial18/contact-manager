package com.backend.smart_contact.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.smart_contact.Entities.User;

public interface UserRepository extends CrudRepository<User,Integer>{

    public Object findByEmail(String email);

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);

}
