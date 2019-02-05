package com.niveka.service.mapper;

import com.niveka.domain.*;
import com.niveka.service.dto.MessageDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {ChannelMapper.class, UserMapper.class})
@Service
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "channel.id", target = "channelId")
    @Mapping(source = "user.id", target = "userId")
    MessageDTO toDto(Message message);

    @Mapping(source = "channelId", target = "channel")
    @Mapping(source = "userId", target = "user")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(String id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
