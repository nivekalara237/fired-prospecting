package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.CompteRenduSuiviDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity CompteRenduSuivi and its DTO CompteRenduSuiviDTO.
 */
@Mapper(componentModel = "spring", uses = {SuiviMapper.class,ProspectMapper.class})
@Service
public interface CompteRenduSuiviMapper extends EntityMapper<CompteRenduSuiviDTO, CompteRenduSuivi> {

    @Mappings({
        @Mapping(source = "suivi.id", target = "suiviId"),
        @Mapping(source = "prospect.id", target = "prospectId")
    })
    CompteRenduSuiviDTO toDto(CompteRenduSuivi compteRenduSuivi);

    @Mappings({
        @Mapping(source = "suiviId", target = "suivi"),
        @Mapping(source = "prospectId", target = "prospect")
    })
    CompteRenduSuivi toEntity(CompteRenduSuiviDTO compteRenduSuiviDTO);

    default CompteRenduSuivi fromId(String id) {
        if (id == null) {
            return null;
        }
        CompteRenduSuivi compteRenduSuivi = new CompteRenduSuivi();
        compteRenduSuivi.setId(id);
        return compteRenduSuivi;
    }
}
