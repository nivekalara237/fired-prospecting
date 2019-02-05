package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.RapportDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Rapport and its DTO RapportDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
@Service
public interface RapportMapper extends EntityMapper<RapportDTO, Rapport> {

    @Mapping(source = "user.id", target = "userId")
    RapportDTO toDto(Rapport rapport);

    @Mapping(source = "userId", target = "user")
    Rapport toEntity(RapportDTO rapportDTO);

    default Rapport fromId(String id) {
        if (id == null) {
            return null;
        }
        Rapport rapport = new Rapport();
        rapport.setId(id);
        return rapport;
    }
}