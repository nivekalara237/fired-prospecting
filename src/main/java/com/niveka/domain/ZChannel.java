package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ZChannel.
 */
@Document(collection = "channel")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "channel")
public class ZChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("designation")
    private String designation;

    @Field("entreprise_id")
    private String entrepriseId;

    @Field("code")
    private String code;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    @DBRef
    @Field("entreprise")
    @JsonIgnoreProperties("")
    private Entreprise entreprise;

    @DBRef
    @Field("users")
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public ZChannel designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public ZChannel entrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
        return this;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public String getCode() {
        return code;
    }

    public ZChannel code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ZChannel createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ZChannel updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public ZChannel deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public ZChannel entreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Set<User> getUsers() {
        return users;
    }

    public ZChannel users(Set<User> users) {
        this.users = users;
        return this;
    }

    public ZChannel addUser(User user) {
        this.users.add(user);
        //user.getChannels().add(this);
        return this;
    }

    public ZChannel removeUser(User user) {
        this.users.remove(user);
        //user.getChannels().remove(this);
        return this;
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
        ZChannel channel = (ZChannel) o;
        if (channel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZChannel{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", entrepriseId='" + getEntrepriseId() + "'" +
            ", code='" + getCode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
