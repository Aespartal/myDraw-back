package com.project.mydraw.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.project.mydraw.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.project.mydraw.domain.ExtendedUser} entity. This class is used
 * in {@link com.project.mydraw.web.rest.ExtendedUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /extended-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExtendedUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter telephone;

    private LocalDateFilter birthdate;

    private StringFilter description;

    private GenderFilter gender;

    private LongFilter userId;

    private LongFilter albumId;

    private LongFilter backgroundColorId;

    public ExtendedUserCriteria() {
    }

    public ExtendedUserCriteria(ExtendedUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
        this.backgroundColorId = other.backgroundColorId == null ? null : other.backgroundColorId.copy();
    }

    @Override
    public ExtendedUserCriteria copy() {
        return new ExtendedUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public LocalDateFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateFilter birthdate) {
        this.birthdate = birthdate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getAlbumId() {
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }

    public LongFilter getBackgroundColorId() {
        return backgroundColorId;
    }

    public void setBackgroundColorId(LongFilter backgroundColorId) {
        this.backgroundColorId = backgroundColorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExtendedUserCriteria that = (ExtendedUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(albumId, that.albumId) &&
            Objects.equals(backgroundColorId, that.backgroundColorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        telephone,
        birthdate,
        description,
        gender,
        userId,
        albumId,
        backgroundColorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtendedUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
                (backgroundColorId != null ? "backgroundColorId=" + backgroundColorId + ", " : "") +
            "}";
    }

}
