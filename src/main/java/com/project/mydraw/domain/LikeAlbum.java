package com.project.mydraw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A LikeAlbum.
 */
@Entity
@Table(name = "like_album")
public class LikeAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "likeAlbums", allowSetters = true)
    private ExtendedUser extendedUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "likeAlbums", allowSetters = true)
    private Album album;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtendedUser getExtendedUser() {
        return extendedUser;
    }

    public LikeAlbum extendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
        return this;
    }

    public void setExtendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
    }

    public Album getAlbum() {
        return album;
    }

    public LikeAlbum album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeAlbum)) {
            return false;
        }
        return id != null && id.equals(((LikeAlbum) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeAlbum{" +
            "id=" + getId() +
            "}";
    }
}
