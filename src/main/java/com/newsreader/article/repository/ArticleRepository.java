package com.newsreader.article.repository;

import com.newsreader.article.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>, JpaRepository<Article, Long>,
    JpaSpecificationExecutor<Article> {

    @Query("From Article a where (:author is null or :author member a.authors) and (:keyword is null or :keyword "
        + "member a.keywords) and ((:startDate is null or a.publishDate >= :startDate) and (:endDate is null or a"
        + ".publishDate <= :endDate))")
    List<Article> findAllArticleByQuery(@Param("author") String articleAuthor, @Param("keyword") String articleKeyword,
        @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
