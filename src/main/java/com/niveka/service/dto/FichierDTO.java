package com.niveka.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Fichier entity.
 */
public class FichierDTO implements Serializable {

    private String id;

    private String path;

    private String type;

    private long size;

    private String model;

    private String modelId;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichierDTO fichierDTO = (FichierDTO) o;
        if (fichierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichierDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", type='" + getType() + "'" +
            ", size=" + getSize() +
            ", model='" + getModel() + "'" +
            ", modelId='" + getModelId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
