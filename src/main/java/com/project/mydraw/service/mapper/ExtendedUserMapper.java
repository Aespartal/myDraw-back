package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.ExtendedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtendedUser} and its DTO {@link ExtendedUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BackgroundColorMapper.class})
public interface ExtendedUserMapper extends EntityMapper<ExtendedUserDTO, ExtendedUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "backgroundColor.id", target = "backgroundColorId")
    ExtendedUserDTO toDto(ExtendedUser extendedUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "removeAlbum", ignore = true)
    @Mapping(source = "backgroundColorId", target = "backgroundColor")
    ExtendedUser toEntity(ExtendedUserDTO extendedUserDTO);

    default ExtendedUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtendedUser extendedUser = new ExtendedUser();
        extendedUser.setId(id);
        return extendedUser;
    }
}
