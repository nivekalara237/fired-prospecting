package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.PieceJointeDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity PieceJointe and its DTO PieceJointeDTO.
 */
@Mapper(componentModel = "spring", uses = {RapportMapper.class})
@Service
public interface PieceJointeMapper extends EntityMapper<PieceJointeDTO, PieceJointe> {

    @Mapping(source = "rapport.id", target = "rapportId")
    PieceJointeDTO toDto(PieceJointe pieceJointe);

    @Mapping(source = "rapportId", target = "rapport")
    PieceJointe toEntity(PieceJointeDTO pieceJointeDTO);

    default PieceJointe fromId(String id) {
        if (id == null) {
            return null;
        }
        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setId(id);
        return pieceJointe;
    }
}
