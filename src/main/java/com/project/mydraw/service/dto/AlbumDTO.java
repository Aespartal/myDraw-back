package com.project.mydraw.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.project.mydraw.domain.Album} entity.
 */
public class AlbumDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @Size(max = 3500)
    private String description;

    @NotNull
    private String image;

    @NotNull
    private String imageContentType;

    @NotNull
    private Integer order;

    @NotNull
    private Instant date;


    private Long extendedUserId;

    private Long categoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getExtendedUserId() {
        return extendedUserId;
    }

    public void setExtendedUserId(Long extendedUserId) {
        this.extendedUserId = extendedUserId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlbumDTO)) {
            return false;
        }

        return id != null && id.equals(((AlbumDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlbumDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", order=" + getOrder() +
            ", date='" + getDate() + "'" +
            ", extendedUserId=" + getExtendedUserId() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
