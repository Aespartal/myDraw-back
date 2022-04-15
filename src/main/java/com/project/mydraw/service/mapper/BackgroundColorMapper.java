package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.BackgroundColorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BackgroundColor} and its DTO {@link BackgroundColorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BackgroundColorMapper extends EntityMapper<BackgroundColorDTO, BackgroundColor> {


    @Mapping(target = "extendedUsers", ignore = true)
    @Mapping(target = "removeExtendedUser", ignore = true)
    BackgroundColor toEntity(BackgroundColorDTO backgroundColorDTO);

    default BackgroundColor fromId(Long id) {
        if (id == null) {
            return null;
        }
        BackgroundColor backgroundColor = new BackgroundColor();
        backgroundColor.setId(id);
        return backgroundColor;
    }
}
