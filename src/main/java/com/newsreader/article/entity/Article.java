package com.newsreader.article.entity;

import com.newsreader.article.model.entities.ArticleEntityBase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Article extends ArticleEntityBase {

    String header;

    String shortDescription;

    String text;

    Date publishDate;

    @ElementCollection
    List<String> authors;

    @ElementCollection
    List<String> keywords;
}
