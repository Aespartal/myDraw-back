package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.CommentaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commentary} and its DTO {@link CommentaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExtendedUserMapper.class, AlbumMapper.class})
public interface CommentaryMapper extends EntityMapper<CommentaryDTO, Commentary> {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    @Mapping(source = "album.id", target = "albumId")
    CommentaryDTO toDto(Commentary commentary);

    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(source = "albumId", target = "album")
    Commentary toEntity(CommentaryDTO commentaryDTO);

    default Commentary fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commentary commentary = new Commentary();
        commentary.setId(id);
        return commentary;
    }
}
