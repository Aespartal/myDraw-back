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
 * Criteria class for the {@link com.project.mydraw.domain.Image} entity. This class is used
 * in {@link com.project.mydraw.web.rest.ImageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter image;

    private StringFilter imageContentType;

    private LongFilter albumId;

    public ImageCriteria() {
    }

    public ImageCriteria(ImageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.imageContentType = other.imageContentType == null ? null : other.imageContentType.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
    }

    @Override
    public ImageCriteria copy() {
        return new ImageCriteria(this);
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
        final ImageCriteria that = (ImageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(image, that.image) &&
            Objects.equals(imageContentType, that.imageContentType) &&
            Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        image,
        imageContentType,
        albumId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (image != null ? "image=" + image + ", " : "") +
                (imageContentType != null ? "imageContentType=" + imageContentType + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
            "}";
    }

}
