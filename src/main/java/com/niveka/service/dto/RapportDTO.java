package com.niveka.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rapport entity.
 */
public class RapportDTO implements Serializable {

    private String id;

    private String objet;

    private String contenu;

    private Integer type;

    private String position;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String userId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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
            ", user=" + getUserId() +
            "}";
    }
}
