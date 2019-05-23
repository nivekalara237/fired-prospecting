package com.niveka.service.dto;

import com.niveka.domain.Suivi;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Prospect entity.
 */
public class ProspectDTO implements Serializable {

    private String id;

    private String nom;

    private String email;

    private String telephone;

    private Integer type;

    private String dateRdv;

    private String compteRendu;

    private String localisation;

    private String position;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String suiviId;
    private Suivi suivi;

    private String userId;

    public Suivi getSuivi() {
        return suivi;
    }

    public void setSuivi(Suivi suivi) {
        this.suivi = suivi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getCompteRendu() {
        return compteRendu;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ProspectDTO setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    /*public ProspectDTO setCreated(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }*/

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProspectDTO prospectDTO = (ProspectDTO) o;
        if (prospectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prospectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProspectDTO{" +
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
            ", suiviID=" + getSuiviId() +
            ", suivi=" + getSuivi() +
            ", user=" + getUserId() +
            "}";
    }
}
