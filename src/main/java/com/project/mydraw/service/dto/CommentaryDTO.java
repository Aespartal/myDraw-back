package com.project.mydraw.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.project.mydraw.domain.Commentary} entity.
 */
public class CommentaryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Instant dateCreated;

    private Instant dateModified;

    private Boolean isModified;


    private Long extendedUserId;

    private Long albumId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateModified() {
        return dateModified;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean isIsModified() {
        return isModified;
    }

    public void setIsModified(Boolean isModified) {
        this.isModified = isModified;
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
        if (!(o instanceof CommentaryDTO)) {
            return false;
        }

        return id != null && id.equals(((CommentaryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentaryDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", isModified='" + isIsModified() + "'" +
            ", extendedUserId=" + getExtendedUserId() +
            ", albumId=" + getAlbumId() +
            "}";
    }
}
