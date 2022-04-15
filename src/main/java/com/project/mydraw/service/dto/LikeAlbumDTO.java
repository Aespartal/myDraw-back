package com.project.mydraw.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.project.mydraw.domain.LikeAlbum} entity.
 */
public class LikeAlbumDTO implements Serializable {
    
    private Long id;


    private Long extendedUserId;

    private Long albumId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExtendedUserId() {
        return extendedUserId;
    }

    public void setExtendedUserId(Long extendedUserId) {
        this.extendedUserId = extendedUserId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeAlbumDTO)) {
            return false;
        }

        return id != null && id.equals(((LikeAlbumDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeAlbumDTO{" +
            "id=" + getId() +
            ", extendedUserId=" + getExtendedUserId() +
            ", albumId=" + getAlbumId() +
            "}";
    }
}
