package com.niveka.service.dto;

import com.niveka.domain.ZChannel;
import com.niveka.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the ZChannel entity.
 */
public class ZChannelDTO implements Serializable {

    private String id;

    private String designation;

    private String code;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private EntrepriseDTO entreprise;
    private String entrepriseId;

    private Set<UserDTO> users = new HashSet<>();

    @Autowired
    private UserMapper userMapper;

    public ZChannelDTO() {
    }

    public ZChannel toEntity(){
        ZChannel channel = new ZChannel();
        channel.setId(this.id);
        channel.setCode(code);
        channel.setCreatedAt(createdAt);
        channel.setUpdatedAt(updatedAt);
        channel.setDeletedAt(deletedAt);
        channel.setDesignation(designation);
        channel.setEntreprise(entreprise.toEntity());
        channel.setEntrepriseId(entrepriseId);
        return channel;
    }

    public ZChannelDTO(String id, String designation, String code, String createdAt, String updatedAt, String deletedAt, EntrepriseDTO entreprise, Set<UserDTO> users) {
        this.id = id;
        this.designation = designation;
        this.code = code;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.entreprise = entreprise;
        this.users = users;
    }

    public ZChannelDTO(String id, String designation, String code, EntrepriseDTO entreprise, Set<UserDTO> users) {
        this.id = id;
        this.designation = designation;
        this.code = code;
        this.entreprise = entreprise;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public EntrepriseDTO getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO entreprise) {
        this.entreprise = entreprise;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZChannelDTO channelDTO = (ZChannelDTO) o;
        if (channelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZChannelDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", code='" + getCode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
