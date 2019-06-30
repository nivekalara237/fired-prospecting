package com.niveka.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Fichier.
 */
@Document(collection = "fichier")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fichier")
public class Fichier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("path")
    private String path;

    @Field("type")
    private String type;

    @Field("size")
    private long size;

    @Field("model")
    private String model;

    @Field("model_id")
    private String modelId;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public Fichier path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public Fichier type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public Fichier size(long size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getModel() {
        return model;
    }

    public Fichier model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelId() {
        return modelId;
    }

    public Fichier modelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Fichier createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Fichier updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Fichier deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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
        Fichier fichier = (Fichier) o;
        if (fichier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fichier{" +
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
