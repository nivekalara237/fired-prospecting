package com.niveka.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Suivi.
 */
@Document(collection = "suivi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "suivi")
public class Suivi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date_rdv")
    private String dateRdv;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    @Field("user_id")
    private String userId;

    @DBRef
    @Field("user")
    private Set<User> users = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateRdv() {
        return dateRdv;
    }

    public Suivi dateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
        return this;
    }

    public void setDateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Suivi createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Suivi updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Suivi deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Suivi users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Suivi addUser(User user) {
        this.users.add(user);
        user.getSuivis().add(this);
        return this;
    }

    public Suivi removeUser(User user) {
        this.users.remove(user);
        user.getSuivis().remove(this);
        return this;
    }

    public Suivi userId(String userId){
        this.userId = userId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Suivi suivi = (Suivi) o;
        if (suivi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suivi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Suivi{" +
            "id=" + getId() +
            ", dateRdv='" + getDateRdv() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
