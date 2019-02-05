package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.ZChannelDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity ZChannel and its DTO ZChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {EntrepriseMapper.class, UserMapper.class})
@Service
public interface ChannelMapper extends EntityMapper<ZChannelDTO, ZChannel> {

    @Mapping(source = "entreprise.id", target = "entrepriseId")
    ZChannelDTO toDto(ZChannel channel);

    @Mapping(source = "entrepriseId", target = "entreprise")
    ZChannel toEntity(ZChannelDTO channelDTO);

    default ZChannel fromId(String id) {
        if (id == null) {
            return null;
        }
        ZChannel channel = new ZChannel();
        channel.setId(id);
        return channel;
    }
}
