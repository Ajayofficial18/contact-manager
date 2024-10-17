package com.backend.smart_contact.Repository;

import org.springframework.data.repository.CrudRepository;

import com.backend.smart_contact.Entities.User;

public interface UserRepository extends CrudRepository<User,Integer>{

    Object findByEmail(String email);

}
