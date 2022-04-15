package com.project.mydraw.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BackgroundColor.
 */
@Entity
@Table(name = "background_color")
public class BackgroundColor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "backgroundColor")
    private Set<ExtendedUser> extendedUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BackgroundColor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public BackgroundColor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ExtendedUser> getExtendedUsers() {
        return extendedUsers;
    }

    public BackgroundColor extendedUsers(Set<ExtendedUser> extendedUsers) {
        this.extendedUsers = extendedUsers;
        return this;
    }

    public BackgroundColor addExtendedUser(ExtendedUser extendedUser) {
        this.extendedUsers.add(extendedUser);
        extendedUser.setBackgroundColor(this);
        return this;
    }

    public BackgroundColor removeExtendedUser(ExtendedUser extendedUser) {
        this.extendedUsers.remove(extendedUser);
        extendedUser.setBackgroundColor(null);
        return this;
    }

    public void setExtendedUsers(Set<ExtendedUser> extendedUsers) {
        this.extendedUsers = extendedUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BackgroundColor)) {
            return false;
        }
        return id != null && id.equals(((BackgroundColor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BackgroundColor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
