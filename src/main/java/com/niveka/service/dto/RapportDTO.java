package com.niveka.service.dto;

import com.niveka.domain.Copie;
import com.niveka.domain.PieceJointe;
import com.niveka.domain.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Rapport entity.
 */
public class RapportDTO implements Serializable {

    private String id;

    private String objet;

    private String contenu;

    private int type;

    private String position;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String entrepriseId;

    private String userId;

    private boolean haveFile;

    private User user;

    private Set<PieceJointe> pieceJointes = new HashSet<>();
    private Set<Copie> copies = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isHaveFile() {
        return haveFile;
    }

    public void setHaveFile(boolean haveFile) {
        this.haveFile = haveFile;
    }

    public Set<PieceJointe> getPieceJointes() {
        return pieceJointes;
    }

    public void setPieceJointes(Set<PieceJointe> pieceJointes) {
        this.pieceJointes = pieceJointes;
    }

    public Set<Copie> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copie> copies) {
        this.copies = copies;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public RapportDTO rapportDTOToList(HashSet<PieceJointe> pieceJointes,User user) {
        this.setUser(user);
        this.setPieceJointes(pieceJointes);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RapportDTO rapportDTO = (RapportDTO) o;
        if (rapportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rapportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RapportDTO{" +
            "id=" + getId() +
            ", objet='" + getObjet() + "'" +
            ", contenu='" + getContenu() + "'" +
            ", type=" + getType() +
            ", position='" + getPosition() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", user=" + getUser() +
            ", copies=" + getCopies() +
            ", entrepriseId=" + getEntrepriseId() +
            ", pieceJointes=" + getPieceJointes() +
            "}";
    }
}
