package com.niveka.service.dto;

import com.niveka.domain.Fichier;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the CompteRenduSuivi entity.
 */
public class CompteRenduSuiviDTO implements Serializable {

    private String id;

    private String contenu;

    private String dateProchaineRdv;

    private long dateProchaineRdvLong;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String suiviId;

    private String userId;

    private boolean firstAlarm;

    private boolean secondAlarm;

    private boolean thirdAlarm;

    private String entrepriseId;

    private String prospectId;
    private List<Fichier> fichiers;

    private boolean rdvHonore;

    public boolean isFirstAlarm() {
        return firstAlarm;
    }

    public void setFirstAlarm(boolean firstAlarm) {
        this.firstAlarm = firstAlarm;
    }

    public boolean isSecondAlarm() {
        return secondAlarm;
    }

    public void setSecondAlarm(boolean secondAlarm) {
        this.secondAlarm = secondAlarm;
    }

    public boolean isThirdAlarm() {
        return thirdAlarm;
    }

    public void setThirdAlarm(boolean thirdAlarm) {
        this.thirdAlarm = thirdAlarm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
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

    public boolean isRdvHonore() {
        return rdvHonore;
    }

    public void setRdvHonore(boolean rdvHonore) {
        this.rdvHonore = rdvHonore;
    }

    public List<Fichier> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public long getDateProchaineRdvLong() {
        return dateProchaineRdvLong;
    }

    public void setDateProchaineRdvLong(long dateProchaineRdvLong) {
        this.dateProchaineRdvLong = dateProchaineRdvLong;
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
            ", fichiers=" + getFichiers() +
            ", userId=" + getUserId() +
            ", entrepriseId=" + getEntrepriseId() +
            ", firstAlarm=" + isFirstAlarm() +
            ", secondAlarm=" + isSecondAlarm() +
            ", thirdAlarm=" + isThirdAlarm() +
            ", dateProchaineRdvLong=" + getDateProchaineRdvLong() +
            "}";
    }
}
