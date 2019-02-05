package com.niveka.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Copie entity.
 */
public class CopieDTO implements Serializable {

    private String id;

    private String email;

    private String createdAt;

    private String rapportId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRapportId() {
        return rapportId;
    }

    public void setRapportId(String rapportId) {
        this.rapportId = rapportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CopieDTO copieDTO = (CopieDTO) o;
        if (copieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), copieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CopieDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", rapport=" + getRapportId() +
            "}";
    }
}
