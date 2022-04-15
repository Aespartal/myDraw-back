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
 * Criteria class for the {@link com.project.mydraw.domain.LikeAlbum} entity. This class is used
 * in {@link com.project.mydraw.web.rest.LikeAlbumResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /like-albums?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LikeAlbumCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter extendedUserId;

    private LongFilter albumId;

    public LikeAlbumCriteria() {
    }

    public LikeAlbumCriteria(LikeAlbumCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.extendedUserId = other.extendedUserId == null ? null : other.extendedUserId.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
    }

    @Override
    public LikeAlbumCriteria copy() {
        return new LikeAlbumCriteria(this);
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

    public LongFilter getAlbumId() {
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LikeAlbumCriteria that = (LikeAlbumCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(extendedUserId, that.extendedUserId) &&
            Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        extendedUserId,
        albumId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeAlbumCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (extendedUserId != null ? "extendedUserId=" + extendedUserId + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
            "}";
    }

}
