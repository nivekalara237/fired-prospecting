package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.FichierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fichier and its DTO FichierDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FichierMapper extends EntityMapper<FichierDTO, Fichier> {



    default Fichier fromId(String id) {
        if (id == null) {
            return null;
        }
        Fichier fichier = new Fichier();
        fichier.setId(id);
        return fichier;
    }
}
