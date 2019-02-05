package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.ProspectDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Prospect and its DTO ProspectDTO.
 */
@Mapper(componentModel = "spring", uses = {SuiviMapper.class, UserMapper.class})
@Service
public interface ProspectMapper extends EntityMapper<ProspectDTO, Prospect> {

    @Mapping(source = "suivi.id", target = "suiviId")
    @Mapping(source = "user.id", target = "userId")
    ProspectDTO toDto(Prospect prospect);

    @Mapping(source = "suiviId", target = "suivi")
    @Mapping(source = "userId", target = "user")
    Prospect toEntity(ProspectDTO prospectDTO);

    default Prospect fromId(String id) {
        if (id == null) {
            return null;
        }
        Prospect prospect = new Prospect();
        prospect.setId(id);
        return prospect;
    }
}
