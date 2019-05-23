package com.niveka.service.dto;

import com.niveka.domain.Entreprise;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Entreprise entity.
 */
public class EntrepriseDTO implements Serializable {

    private String id;

    private String designation;

    private String logo;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String range_utilisateur;

    private int nombre_utilisateur;

    private String status;

    private Set<UserDTO> users = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getRange_utilisateur() {
        return range_utilisateur;
    }

    public void setRange_utilisateur(String range_utilisateur) {
        this.range_utilisateur = range_utilisateur;
    }

    public int getNombre_utilisateur() {
        return nombre_utilisateur;
    }

    public void setNombre_utilisateur(int nombre_utilisateur) {
        this.nombre_utilisateur = nombre_utilisateur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Entreprise toEntity(){
        Entreprise e = new Entreprise();
        e.setId(this.id);
        e.setLogo(this.logo);
        e.setDesignation(this.designation);
        e.setRange_utilisateur(this.range_utilisateur);
        e.setNombre_utilisateur(this.nombre_utilisateur);
        e.setCreatedAt(this.createdAt);
        e.setUpdatedAt(this.updatedAt);
        e.deletedAt(this.deletedAt);
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrepriseDTO entrepriseDTO = (EntrepriseDTO) o;
        if (entrepriseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrepriseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntrepriseDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", logo='" + getLogo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
