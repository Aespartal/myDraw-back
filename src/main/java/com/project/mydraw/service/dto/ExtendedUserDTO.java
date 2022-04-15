package com.project.mydraw.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.project.mydraw.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.project.mydraw.domain.ExtendedUser} entity.
 */
public class ExtendedUserDTO implements Serializable {
    
    private Long id;

    private String telephone;

    @NotNull
    private LocalDate birthdate;

    @Size(max = 3500)
    private String description;

    @Lob
    private byte[] imageCover;

    private String imageCoverContentType;
    @NotNull
    private Gender gender;


    private Long userId;

    private Long backgroundColorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageCover() {
        return imageCover;
    }

    public void setImageCover(byte[] imageCover) {
        this.imageCover = imageCover;
    }

    public String getImageCoverContentType() {
        return imageCoverContentType;
    }

    public void setImageCoverContentType(String imageCoverContentType) {
        this.imageCoverContentType = imageCoverContentType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBackgroundColorId() {
        return backgroundColorId;
    }

    public void setBackgroundColorId(Long backgroundColorId) {
        this.backgroundColorId = backgroundColorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedUserDTO)) {
            return false;
        }

        return id != null && id.equals(((ExtendedUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtendedUserDTO{" +
            "id=" + getId() +
            ", telephone='" + getTelephone() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageCover='" + getImageCover() + "'" +
            ", gender='" + getGender() + "'" +
            ", userId=" + getUserId() +
            ", backgroundColorId=" + getBackgroundColorId() +
            "}";
    }
}
