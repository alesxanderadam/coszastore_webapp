package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE comment c SET c.content = ?1, c.email = ?2, c.website = ?3, c.name = ?4, c.blog = ?5 WHERE c.id = ?6")
    void updateComment(String content, String email, String website, String name, BlogEntity blog, int id);

    List<CommentEntity> findAllByBlogId(int id);

    int countByBlogId(int id);
}
