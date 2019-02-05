package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Rapport.
 */
@Document(collection = "rapport")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rapport")
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("contenu")
    private String contenu;

    @Field("type")
    private Integer type;

    @Field("position")
    private String position;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties("")
    private User user;

    @DBRef
    @Field("objets")
    private Set<Objet> objets = new HashSet<>();

    @DBRef
    @Field("copies")
    private Set<Copie> copies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Copie> getCopies() {
        return copies;
    }

    public Rapport copies(Set<Copie> copies) {
        this.copies = copies;
        return this;
    }



    public void setCopies(Set<Copie> copies) {
        this.copies = copies;
    }

    public String getContenu() {
        return contenu;
    }

    public Rapport contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Integer getType() {
        return type;
    }

    public Rapport type(Integer type) {
        this.type = type;
        return this;
    }

    public Set<Objet> getObjets() {
        return objets;
    }

    public void setObjets(Set<Objet> objets) {
        this.objets = objets;
    }

    public Rapport objets(Set<Objet> objets){
        this.objets = objets;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public Rapport position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Rapport createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Rapport updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Rapport deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getUser() {
        return user;
    }

    public Rapport user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Rapport rapport = (Rapport) o;
        if (rapport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rapport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rapport{" +
            "id=" + getId() +
            ", objets='" + getObjets() + "'" +
            ", copies='" + getCopies() + "'" +
            ", contenu='" + getContenu() + "'" +
            ", type=" + getType() +
            ", position='" + getPosition() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", objets='" + getObjets() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
