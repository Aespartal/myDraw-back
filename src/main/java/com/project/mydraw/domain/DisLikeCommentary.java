package com.project.mydraw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DisLikeCommentary.
 */
@Entity
@Table(name = "dis_like_commentary")
public class DisLikeCommentary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "disLikeCommentaries", allowSetters = true)
    private ExtendedUser extendedUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "disLikeCommentaries", allowSetters = true)
    private Commentary comentary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtendedUser getExtendedUser() {
        return extendedUser;
    }

    public DisLikeCommentary extendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
        return this;
    }

    public void setExtendedUser(ExtendedUser extendedUser) {
        this.extendedUser = extendedUser;
    }

    public Commentary getComentary() {
        return comentary;
    }

    public DisLikeCommentary comentary(Commentary commentary) {
        this.comentary = commentary;
        return this;
    }

    public void setComentary(Commentary commentary) {
        this.comentary = commentary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisLikeCommentary)) {
            return false;
        }
        return id != null && id.equals(((DisLikeCommentary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisLikeCommentary{" +
            "id=" + getId() +
            "}";
    }
}
