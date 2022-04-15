package com.project.mydraw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.project.mydraw.domain.enumeration.Gender;

/**
 * A ExtendedUser.
 */
@Entity
@Table(name = "extended_user")
public class ExtendedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Size(max = 3500)
    @Column(name = "description", length = 3500)
    private String description;

    @Lob
    @Column(name = "image_cover")
    private byte[] imageCover;

    @Column(name = "image_cover_content_type")
    private String imageCoverContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "extendedUser")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "extendedUsers", allowSetters = true)
    private BackgroundColor backgroundColor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public ExtendedUser telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ExtendedUser birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getDescription() {
        return description;
    }

    public ExtendedUser description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageCover() {
        return imageCover;
    }

    public ExtendedUser imageCover(byte[] imageCover) {
        this.imageCover = imageCover;
        return this;
    }

    public void setImageCover(byte[] imageCover) {
        this.imageCover = imageCover;
    }

    public String getImageCoverContentType() {
        return imageCoverContentType;
    }

    public ExtendedUser imageCoverContentType(String imageCoverContentType) {
        this.imageCoverContentType = imageCoverContentType;
        return this;
    }

    public void setImageCoverContentType(String imageCoverContentType) {
        this.imageCoverContentType = imageCoverContentType;
    }

    public Gender getGender() {
        return gender;
    }

    public ExtendedUser gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public ExtendedUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public ExtendedUser albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public ExtendedUser addAlbum(Album album) {
        this.albums.add(album);
        album.setExtendedUser(this);
        return this;
    }

    public ExtendedUser removeAlbum(Album album) {
        this.albums.remove(album);
        album.setExtendedUser(null);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public BackgroundColor getBackgroundColor() {
        return backgroundColor;
    }

    public ExtendedUser backgroundColor(BackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(BackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedUser)) {
            return false;
        }
        return id != null && id.equals(((ExtendedUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtendedUser{" +
            "id=" + getId() +
            ", telephone='" + getTelephone() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageCover='" + getImageCover() + "'" +
            ", imageCoverContentType='" + getImageCoverContentType() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
