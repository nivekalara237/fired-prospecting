package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A CompteRenduSuivi.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "compte_rendu_suivi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "compterendusuivi")
public class CompteRenduSuivi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("contenu")
    private String contenu;

    @Field("rdv_honore")
    private boolean rdvHonore;

    @Field("first_alarm")
    private boolean firstAlarm;

    @Field("second_alarm")
    private boolean secondAlarm;

    @Field("third_alarm")
    private boolean thirdAlarm;

    @Field("date_prochaine_rdv")
    private String dateProchaineRdv;

    @Field("date_prochaine_rdv_long")
    private long dateProchaineRdvLong;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    @Field("suivi_id")
    private String suiviId;

    @Field("prospect_id")
    private String prospectId;

    @Field("entreprise_id")
    private String entrepriseId;

    @Field("user_id")
    private String userId;

    @DBRef
    @Field("suivi")
    @JsonIgnoreProperties("")
    private Suivi suivi;

    @DBRef
    @Field("prospect")
    @JsonIgnoreProperties("")
    private Prospect prospect;

    @JsonIgnoreProperties("")
    private List<Fichier> fichiers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public long getDateProchaineRdvLong() {
        return dateProchaineRdvLong;
    }

    public void setDateProchaineRdvLong(long dateProchaineRdvLong) {
        this.dateProchaineRdvLong = dateProchaineRdvLong;
    }

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

    public String getSuiviId() {
        return suiviId;
    }

    public void setSuiviId(String suiviId) {
        this.suiviId = suiviId;
    }

    public CompteRenduSuivi suiviId(String suiviId){
        this.suiviId = suiviId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CompteRenduSuivi userId(String userId){
        this.userId = userId;
        return this;
    }

    public String getProspectId() {
        return prospectId;
    }

    public void setProspectId(String prospectId) {
        this.prospectId = prospectId;
    }

    public CompteRenduSuivi prospectId(String prospectId){
        this.prospectId = suiviId;
        return this;
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

    public CompteRenduSuivi contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDateProchaineRdv() {
        return dateProchaineRdv;
    }

    public CompteRenduSuivi dateProchaineRdv(String dateProchaineRdv) {
        this.dateProchaineRdv = dateProchaineRdv;
        return this;
    }

    public void setDateProchaineRdv(String dateProchaineRdv) {
        this.dateProchaineRdv = dateProchaineRdv;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public CompteRenduSuivi createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public CompteRenduSuivi updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public CompteRenduSuivi deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public CompteRenduSuivi entrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Suivi getSuivi() {
        return suivi;
    }

    public CompteRenduSuivi suivi(Suivi suivi) {
        this.suivi = suivi;
        return this;
    }
    public CompteRenduSuivi prospect(Prospect prospect) {
        this.prospect = prospect;
        return this;
    }

    public List<Fichier> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
    }

    public void setSuivi(Suivi suivi) {
        this.suivi = suivi;
    }

    public CompteRenduSuivi rdvHonore(boolean rdvHonore) {
        this.rdvHonore = rdvHonore;
        return this;
    }
    public boolean isRdvHonore() {
        return rdvHonore;
    }

    public void setRdvHonore(boolean rdvHonore) {
        this.rdvHonore = rdvHonore;
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
        CompteRenduSuivi compteRenduSuivi = (CompteRenduSuivi) o;
        if (compteRenduSuivi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteRenduSuivi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteRenduSuivi{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", dateProchaineRdv='" + getDateProchaineRdv() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", userId='" + getUserId() + "'" +
            ", dateProchaineRdvLong='" + getDateProchaineRdvLong() + "'" +
            "}";
    }
}
