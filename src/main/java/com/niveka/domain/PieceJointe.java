package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PieceJointe.
 */
@Document(collection = "piece_jointe")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "piece_jointe")
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("lien")
    private String lien;

    @Field("encode")
    private String encode;

    @Field("taille")
    private long taille;

    @Field("type")
    private String type;

    @Field("rapport_id")
    private String rapportId;

    //@ManyToOne(mappedBy="rapport")
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

    public PieceJointe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLien() {
        return lien;
    }

    public PieceJointe lien(String lien) {
        this.lien = lien;
        return this;
    }

    public PieceJointe rapportId(String rapportId){
        this.rapportId = rapportId;
        return this;
    }

    public String getRapportId() {
        return rapportId;
    }

    public void setRapportId(String rapportId) {
        this.rapportId = rapportId;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getEncode() {
        return encode;
    }

    public PieceJointe encode(String encode) {
        this.encode = encode;
        return this;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public PieceJointe rapport(Rapport rapport) {
        this.rapport = rapport;
        return this;
    }

    public long getTaille() {
        return taille;
    }

    public void setTaille(long taille) {
        this.taille = taille;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        PieceJointe pieceJointe = (PieceJointe) o;
        if (pieceJointe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lien='" + getLien() + "'" +
            ", size='" + getTaille() + "'" +
            ", filetype='" + getType() + "'" +
            ", encode='" + getEncode() + "'" +
            "}";
    }
}
