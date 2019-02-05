package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.EntrepriseDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Entreprise and its DTO EntrepriseDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
@Service
public interface EntrepriseMapper extends EntityMapper<EntrepriseDTO, Entreprise> {



    default Entreprise fromId(String id) {
        if (id == null) {
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(id);
        return entreprise;
    }
}
