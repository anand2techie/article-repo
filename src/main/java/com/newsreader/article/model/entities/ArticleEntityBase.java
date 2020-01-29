package com.newsreader.article.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(of = "id", callSuper = false)
public abstract class ArticleEntityBase extends ArticleCreateAndUpdateEntityBase
    implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    protected Long id;

}