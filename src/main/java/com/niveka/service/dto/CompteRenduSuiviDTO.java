package com.niveka.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompteRenduSuivi entity.
 */
public class CompteRenduSuiviDTO implements Serializable {

    private String id;

    private String contenu;

    private String dateProchaineRdv;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String suiviId;

    private String prospectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDateProchaineRdv() {
        return dateProchaineRdv;
    }

    public void setDateProchaineRdv(String dateProchaineRdv) {
        this.dateProchaineRdv = dateProchaineRdv;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CompteRenduSuiviDTO setCreated(String createdAt) {
        this.createdAt = createdAt;
        return this;
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

    public String getSuiviId() {
        return suiviId;
    }

    public void setSuiviId(String suiviId) {
        this.suiviId = suiviId;
    }

    public String getProspectId() {
        return prospectId;
    }

    public void setProspectId(String prospectId) {
        this.prospectId = prospectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompteRenduSuiviDTO compteRenduSuiviDTO = (CompteRenduSuiviDTO) o;
        if (compteRenduSuiviDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteRenduSuiviDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteRenduSuiviDTO{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", dateProchaineRdv='" + getDateProchaineRdv() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", prospectId=" + getProspectId() +
            "}";
    }
}
