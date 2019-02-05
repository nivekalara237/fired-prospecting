package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.CopieDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Copie and its DTO CopieDTO.
 */
@Mapper(componentModel = "spring", uses = {RapportMapper.class})
@Service
public interface CopieMapper extends EntityMapper<CopieDTO, Copie> {

    @Mapping(source = "rapport.id", target = "rapportId")
    CopieDTO toDto(Copie copie);

    @Mapping(source = "rapportId", target = "rapport")
    Copie toEntity(CopieDTO copieDTO);

    default Copie fromId(String id) {
        if (id == null) {
            return null;
        }
        Copie copie = new Copie();
        copie.setId(id);
        return copie;
    }
}
