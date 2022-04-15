package com.project.mydraw.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.project.mydraw.domain.DisLikeCommentary} entity. This class is used
 * in {@link com.project.mydraw.web.rest.DisLikeCommentaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dis-like-commentaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisLikeCommentaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter extendedUserId;

    private LongFilter comentaryId;

    public DisLikeCommentaryCriteria() {
    }

    public DisLikeCommentaryCriteria(DisLikeCommentaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.extendedUserId = other.extendedUserId == null ? null : other.extendedUserId.copy();
        this.comentaryId = other.comentaryId == null ? null : other.comentaryId.copy();
    }

    @Override
    public DisLikeCommentaryCriteria copy() {
        return new DisLikeCommentaryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getExtendedUserId() {
        return extendedUserId;
    }

    public void setExtendedUserId(LongFilter extendedUserId) {
        this.extendedUserId = extendedUserId;
    }

    public LongFilter getComentaryId() {
        return comentaryId;
    }

    public void setComentaryId(LongFilter comentaryId) {
        this.comentaryId = comentaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisLikeCommentaryCriteria that = (DisLikeCommentaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(extendedUserId, that.extendedUserId) &&
            Objects.equals(comentaryId, that.comentaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        extendedUserId,
        comentaryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisLikeCommentaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (extendedUserId != null ? "extendedUserId=" + extendedUserId + ", " : "") +
                (comentaryId != null ? "comentaryId=" + comentaryId + ", " : "") +
            "}";
    }

}
