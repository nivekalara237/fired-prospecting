package com.niveka.domain;

import com.niveka.service.dto.EntrepriseDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Entreprise.
 */
@Document(collection = "entreprise")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "entreprise")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    @Field("designation")
    private String designation;

    @Field("logo")
    private String logo;

    @Field("range_utilisateur")
    private String range_utilisateur;

    @Field("nombre_utilisateur")
    private int nombre_utilisteur;

    @Field("status")
    private String status;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

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

    public Entreprise designation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getRange_utilisateur() {
        return range_utilisateur;
    }

    public void setRange_utilisateur(String range_utilisateur) {
        this.range_utilisateur = range_utilisateur;
    }

    public int getNombre_utilisteur() {
        return nombre_utilisteur;
    }

    public void setNombre_utilisteur(int nombre_utilisteur) {
        this.nombre_utilisteur = nombre_utilisteur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLogo() {
        return logo;
    }

    public Entreprise logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Entreprise createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Entreprise updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Entreprise deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Entreprise users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Entreprise addUser(User user) {
        this.users.add(user);
        //user.getEntreprise().addUser(user);
        return this;
    }

    public Entreprise removeUser(User user) {
        this.users.remove(user);
        //user.getEntreprises().remove(this);
        return this;
    }

    public EntrepriseDTO toDTO(){
        EntrepriseDTO e = new EntrepriseDTO();
        e.setId(this.id);
        e.setLogo(this.logo);
        e.setDesignation(this.designation);
        e.setRange_utilisateur(this.range_utilisateur);
        e.setNombre_utilisateur(this.nombre_utilisteur);
        e.setCreatedAt(this.createdAt);
        return e;
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
        Entreprise entreprise = (Entreprise) o;
        if (entreprise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreprise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", logo='" + getLogo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
