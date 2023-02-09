package com.prueba.back.persistance.repository;

import com.prueba.back.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
List<User> findByUserName(String name);
List<User> findByLastName(String lastname);


}
