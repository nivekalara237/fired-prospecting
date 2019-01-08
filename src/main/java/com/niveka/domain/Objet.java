package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Objet.
 */
@Document(collection = "objet")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "objet")
public class Objet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("lien")
    private String lien;

    @Field("encode")
    private String encode;

    @DBRef
    @Field("rapport")
    @JsonIgnoreProperties("")
    private Rapport rapport;

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

    public Objet nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLien() {
        return lien;
    }

    public Objet lien(String lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getEncode() {
        return encode;
    }

    public Objet encode(String encode) {
        this.encode = encode;
        return this;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public Objet rapport(Rapport rapport) {
        this.rapport = rapport;
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
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
        Objet objet = (Objet) o;
        if (objet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), objet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Objet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lien='" + getLien() + "'" +
            ", encode='" + getEncode() + "'" +
            "}";
    }
}
