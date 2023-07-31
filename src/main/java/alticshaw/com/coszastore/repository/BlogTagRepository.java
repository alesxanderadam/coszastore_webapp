package alticshaw.com.coszastore.repository;

import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.BlogTagEntity;
import alticshaw.com.coszastore.entity.TagEntity;
import alticshaw.com.coszastore.entity.ids.BlogTagIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlogTagRepository extends JpaRepository<BlogTagEntity, BlogTagIds> {
    @Query("SELECT bt.tag FROM blog_tag bt WHERE bt.ids.blogId = ?1")
    List<TagEntity> findTagsByBlogId(int blogId);

    @Transactional
    void deleteAllByBlog(BlogEntity blogEntity);
}
