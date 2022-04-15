package com.project.mydraw.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.project.mydraw.domain.LikeCommentary} entity.
 */
public class LikeCommentaryDTO implements Serializable {
    
    private Long id;


    private Long extendedUserId;

    private Long comentaryId;
    
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

    public Long getComentaryId() {
        return comentaryId;
    }

    public void setComentaryId(Long commentaryId) {
        this.comentaryId = commentaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeCommentaryDTO)) {
            return false;
        }

        return id != null && id.equals(((LikeCommentaryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeCommentaryDTO{" +
            "id=" + getId() +
            ", extendedUserId=" + getExtendedUserId() +
            ", comentaryId=" + getComentaryId() +
            "}";
    }
}
