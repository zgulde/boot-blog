package com.zgulde.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    User save(User user);
    User findOne(Long id);
    boolean exists(Long id);
    List<User> findAll();
}
