package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.AlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExtendedUserMapper.class, CategoryMapper.class})
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    @Mapping(source = "category.id", target = "categoryId")
    AlbumDTO toDto(Album album);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImage", ignore = true)
    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(source = "categoryId", target = "category")
    Album toEntity(AlbumDTO albumDTO);

    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
