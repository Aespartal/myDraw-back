package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.LikeAlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LikeAlbum} and its DTO {@link LikeAlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExtendedUserMapper.class, AlbumMapper.class})
public interface LikeAlbumMapper extends EntityMapper<LikeAlbumDTO, LikeAlbum> {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    @Mapping(source = "album.id", target = "albumId")
    LikeAlbumDTO toDto(LikeAlbum likeAlbum);

    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(source = "albumId", target = "album")
    LikeAlbum toEntity(LikeAlbumDTO likeAlbumDTO);

    default LikeAlbum fromId(Long id) {
        if (id == null) {
            return null;
        }
        LikeAlbum likeAlbum = new LikeAlbum();
        likeAlbum.setId(id);
        return likeAlbum;
    }
}
