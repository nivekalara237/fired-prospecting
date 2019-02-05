package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.ObjetDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Objet and its DTO ObjetDTO.
 */
@Mapper(componentModel = "spring", uses = {RapportMapper.class})
@Service
public interface ObjetMapper extends EntityMapper<ObjetDTO, Objet> {

    @Mapping(source = "rapport.id", target = "rapportId")
    ObjetDTO toDto(Objet objet);

    @Mapping(source = "rapportId", target = "rapport")
    Objet toEntity(ObjetDTO objetDTO);

    default Objet fromId(String id) {
        if (id == null) {
            return null;
        }
        Objet objet = new Objet();
        objet.setId(id);
        return objet;
    }
}
