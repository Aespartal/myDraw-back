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
 * Criteria class for the {@link com.project.mydraw.domain.Album} entity. This class is used
 * in {@link com.project.mydraw.web.rest.AlbumResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /albums?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlbumCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter image;

    private StringFilter imageContentType;

    private IntegerFilter order;

    private InstantFilter date;

    private LongFilter imageId;

    private LongFilter extendedUserId;

    private LongFilter categoryId;

    public AlbumCriteria() {
    }

    public AlbumCriteria(AlbumCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.imageContentType = other.imageContentType == null ? null : other.imageContentType.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
        this.extendedUserId = other.extendedUserId == null ? null : other.extendedUserId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public AlbumCriteria copy() {
        return new AlbumCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getImage() {
        return image;
    }

    public void setImage(StringFilter image) {
        this.image = image;
    }

    public StringFilter getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(StringFilter imageContentType) {
        this.imageContentType = imageContentType;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public LongFilter getImageId() {
        return imageId;
    }

    public void setImageId(LongFilter imageId) {
        this.imageId = imageId;
    }

    public LongFilter getExtendedUserId() {
        return extendedUserId;
    }

    public void setExtendedUserId(LongFilter extendedUserId) {
        this.extendedUserId = extendedUserId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlbumCriteria that = (AlbumCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(image, that.image) &&
            Objects.equals(imageContentType, that.imageContentType) &&
            Objects.equals(order, that.order) &&
            Objects.equals(date, that.date) &&
            Objects.equals(imageId, that.imageId) &&
            Objects.equals(extendedUserId, that.extendedUserId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        image,
        imageContentType,
        order,
        date,
        imageId,
        extendedUserId,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlbumCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (image != null ? "image=" + image + ", " : "") +
                (imageContentType != null ? "imageContentType=" + imageContentType + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (imageId != null ? "imageId=" + imageId + ", " : "") +
                (extendedUserId != null ? "extendedUserId=" + extendedUserId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
