package com.niveka.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Objet entity.
 */
public class ObjetDTO implements Serializable {

    private String id;

    private String nom;

    private String lien;

    private String encode;

    private String type;

    private long taille;

    private String rapportId;

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getRapportId() {
        return rapportId;
    }

    public void setRapportId(String rapportId) {
        this.rapportId = rapportId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTaille() {
        return taille;
    }

    public void setTaille(long taille) {
        this.taille = taille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjetDTO objetDTO = (ObjetDTO) o;
        if (objetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), objetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObjetDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lien='" + getLien() + "'" +
            ", type='" + getType() + "'" +
            ", taille='" + getTaille() + "'" +
            ", encode='" + getEncode() + "'" +
            ", rapport=" + getRapportId() +
            "}";
    }
}
