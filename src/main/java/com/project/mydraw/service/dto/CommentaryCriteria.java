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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.project.mydraw.domain.Commentary} entity. This class is used
 * in {@link com.project.mydraw.web.rest.CommentaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commentaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private InstantFilter dateCreated;

    private InstantFilter dateModified;

    private BooleanFilter isModified;

    private LongFilter extendedUserId;

    private LongFilter albumId;

    public CommentaryCriteria() {
    }

    public CommentaryCriteria(CommentaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.dateCreated = other.dateCreated == null ? null : other.dateCreated.copy();
        this.dateModified = other.dateModified == null ? null : other.dateModified.copy();
        this.isModified = other.isModified == null ? null : other.isModified.copy();
        this.extendedUserId = other.extendedUserId == null ? null : other.extendedUserId.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
    }

    @Override
    public CommentaryCriteria copy() {
        return new CommentaryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(InstantFilter dateCreated) {
        this.dateCreated = dateCreated;
    }

    public InstantFilter getDateModified() {
        return dateModified;
    }

    public void setDateModified(InstantFilter dateModified) {
        this.dateModified = dateModified;
    }

    public BooleanFilter getIsModified() {
        return isModified;
    }

    public void setIsModified(BooleanFilter isModified) {
        this.isModified = isModified;
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
        final CommentaryCriteria that = (CommentaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dateCreated, that.dateCreated) &&
            Objects.equals(dateModified, that.dateModified) &&
            Objects.equals(isModified, that.isModified) &&
            Objects.equals(extendedUserId, that.extendedUserId) &&
            Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        dateCreated,
        dateModified,
        isModified,
        extendedUserId,
        albumId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dateCreated != null ? "dateCreated=" + dateCreated + ", " : "") +
                (dateModified != null ? "dateModified=" + dateModified + ", " : "") +
                (isModified != null ? "isModified=" + isModified + ", " : "") +
                (extendedUserId != null ? "extendedUserId=" + extendedUserId + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
            "}";
    }

}
