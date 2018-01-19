package com.zgulde.posts;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();
    Tag findByName(String name);
}
