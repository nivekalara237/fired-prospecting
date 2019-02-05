package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.SuiviDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Suivi and its DTO SuiviDTO.
 */
@Mapper(componentModel = "spring", uses = {})
@Service
public interface SuiviMapper extends EntityMapper<SuiviDTO, Suivi> {


    @Mapping(target = "users", ignore = true)
    Suivi toEntity(SuiviDTO suiviDTO);

    default Suivi fromId(String id) {
        if (id == null) {
            return null;
        }
        Suivi suivi = new Suivi();
        suivi.setId(id);
        return suivi;
    }
}
