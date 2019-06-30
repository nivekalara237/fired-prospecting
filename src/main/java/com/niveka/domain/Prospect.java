package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;


/**
 * A Prospect.
 */
@Document(collection = "prospect")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prospect")
public class Prospect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Text, fielddata = true)
    @org.springframework.data.mongodb.core.mapping.Field("name")
    private String nom;

    @Field(type = FieldType.Text, fielddata = true)
    @org.springframework.data.mongodb.core.mapping.Field("email")
    private String email;

    @Field(type = FieldType.Text, fielddata = true)
    @org.springframework.data.mongodb.core.mapping.Field("telephone")
    private String telephone;

    @org.springframework.data.mongodb.core.mapping.Field("type")
    private Integer type;

    @org.springframework.data.mongodb.core.mapping.Field("date_rdv")
    private String dateRdv;

    @org.springframework.data.mongodb.core.mapping.Field("compte_rendu")
    private String compteRendu;


    @Field(type = FieldType.Text, fielddata = true)
    @org.springframework.data.mongodb.core.mapping.Field("localisation")
    private String localisation;

    @org.springframework.data.mongodb.core.mapping.Field("position")
    private String position;

    @org.springframework.data.mongodb.core.mapping.Field("created_at")
    private String createdAt;

    @org.springframework.data.mongodb.core.mapping.Field("updated_at")
    private String updatedAt;

    @org.springframework.data.mongodb.core.mapping.Field("deleted_at")
    private String deletedAt;

    @org.springframework.data.mongodb.core.mapping.Field("suivi_id")
    private String suiviId;

    @org.springframework.data.mongodb.core.mapping.Field("user_id")
    private String userId;

    @org.springframework.data.mongodb.core.mapping.Field("entreprise_id")
    private String entrepriseId;

    @DBRef
    @org.springframework.data.mongodb.core.mapping.Field("suivi")
    private Suivi suivi;

    @DBRef
    @org.springframework.data.mongodb.core.mapping.Field("user")
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Prospect nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public Prospect email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Prospect telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getType() {
        return type;
    }

    public Prospect type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDateRdv() {
        return dateRdv;
    }

    public Prospect dateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
        return this;
    }

    public void setDateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getCompteRendu() {
        return compteRendu;
    }

    public Prospect compteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
        return this;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Prospect localisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getPosition() {
        return position;
    }

    public Prospect position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Prospect createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Prospect entrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
        return this;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Prospect updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Prospect deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Suivi getSuivi() {
        return suivi;
    }

    public Prospect suivi(Suivi suivi) {
        this.suivi = suivi;
        return this;
    }

    public void setSuivi(Suivi suivi) {
        this.suivi = suivi;
    }

    public User getUser() {
        return user;
    }

    public Prospect user(User user) {
        this.user = user;
        return this;
    }

    public Prospect userId(String userId){
        this.userId = userId;
        return this;
    }

    public Prospect suiviId(String suiviId){
        this.suiviId = suiviId;
        return this;
    }

    public String getSuiviId() {
        return suiviId;
    }

    public void setSuiviId(String suiviId) {
        this.suiviId = suiviId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        Prospect prospect = (Prospect) o;
        if (prospect.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prospect.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prospect{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", type=" + getType() +
            ", dateRdv='" + getDateRdv() + "'" +
            ", compteRendu='" + getCompteRendu() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", position='" + getPosition() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", entrepriseId='" + getEntrepriseId() + "'" +
            "}";
    }
}
