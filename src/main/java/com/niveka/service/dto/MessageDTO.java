package com.niveka.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Message entity.
 */
public class MessageDTO implements Serializable {

    private String id;

    private String contenu;

    private String key;

    private String vu;

    private String time;

    private Long channelId;

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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
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

        MessageDTO messageDTO = (MessageDTO) o;
        if (messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", key='" + getKey() + "'" +
            ", vu='" + getVu() + "'" +
            ", time='" + getTime() + "'" +
            ", channelId=" + getChannelId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", channel=" + getChannelId() +
            ", user=" + getUserId() +
            "}";
    }
}
