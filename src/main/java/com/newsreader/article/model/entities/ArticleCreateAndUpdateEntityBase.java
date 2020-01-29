package com.newsreader.article.model.entities;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Base entity which tracks creation and update times.
 */
@MappedSuperclass
@DiscriminatorColumn(
    name = "discriminator",
    discriminatorType = DiscriminatorType.STRING)
public class ArticleCreateAndUpdateEntityBase implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @Getter
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Getter
    private Date updateDate;

    @PrePersist
    private void prePersist() {
        creationDate = new Date();
        updateDate = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        updateDate = new Date();
    }
}
