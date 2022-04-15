package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.DisLikeCommentaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisLikeCommentary} and its DTO {@link DisLikeCommentaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExtendedUserMapper.class, CommentaryMapper.class})
public interface DisLikeCommentaryMapper extends EntityMapper<DisLikeCommentaryDTO, DisLikeCommentary> {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    @Mapping(source = "comentary.id", target = "comentaryId")
    DisLikeCommentaryDTO toDto(DisLikeCommentary disLikeCommentary);

    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(source = "comentaryId", target = "comentary")
    DisLikeCommentary toEntity(DisLikeCommentaryDTO disLikeCommentaryDTO);

    default DisLikeCommentary fromId(Long id) {
        if (id == null) {
            return null;
        }
        DisLikeCommentary disLikeCommentary = new DisLikeCommentary();
        disLikeCommentary.setId(id);
        return disLikeCommentary;
    }
}
