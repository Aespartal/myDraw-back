package com.project.mydraw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Commentary.
 */
@Entity
@Table(name = "commentary")
public class Commentary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @Column(name = "is_modified")
    private Boolean isModified;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commentaries", allowSetters = true)
    private ExtendedUser extendedUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commentaries", allowSetters = true)
    private Album album;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Commentary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Commentary dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateModified() {
        return dateModified;
    }

    public Commentary dateModified(Instant dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean isIsModified() {
        return isModified;
    }

    public Commentary isModified(Boolean isModified) {
        this.isModified = isModified;
        return this;
    }

    public void setIsModified(Boolean isModified) {
        this.isModified = isModified;
    }

    public ExtendedUser getExtendedUser() {
        return extendedUser;
    }

    public Commentary extendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
        return this;
    }

    public void setExtendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
    }

    public Album getAlbum() {
        return album;
    }

    public Commentary album(Album album) {
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
        if (!(o instanceof Commentary)) {
            return false;
        }
        return id != null && id.equals(((Commentary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commentary{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", isModified='" + isIsModified() + "'" +
            "}";
    }
}
