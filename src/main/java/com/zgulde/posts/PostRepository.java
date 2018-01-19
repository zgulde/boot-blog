package com.zgulde.posts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll();
    Post findOne(Long id);
    Post save(Post post);
    void delete(Long id);
    List<Post> findByUserId(Long userId);

    @Query(nativeQuery = true, value="select * from posts where id in (select post_id from post_tag where tag_id = ?)")
    List<Post> findByTagId(long tagId);
}
