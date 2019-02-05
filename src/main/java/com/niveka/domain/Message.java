package com.niveka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Message.
 */
@Document(collection = "message")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("contenu")
    private String contenu;

    @Field("key")
    private String key;

    @Field("vu")
    private String vu;

    @Field("time")
    private String time;

    @Field("channel_id")
    private Long channelId;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    @Field("deleted_at")
    private String deletedAt;

    @DBRef
    @Field("channel")
    @JsonIgnoreProperties("")
    private ZChannel channel;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public Message contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getKey() {
        return key;
    }

    public Message key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVu() {
        return vu;
    }

    public Message vu(String vu) {
        this.vu = vu;
        return this;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public String getTime() {
        return time;
    }

    public Message time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Message channelId(Long channelId) {
        this.channelId = channelId;
        return this;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Message createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Message updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public Message deletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public ZChannel getChannel() {
        return channel;
    }

    public Message channel(ZChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(ZChannel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public Message user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", key='" + getKey() + "'" +
            ", vu='" + getVu() + "'" +
            ", time='" + getTime() + "'" +
            ", channelId=" + getChannelId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
