package com.project.mydraw.service.mapper;


import com.project.mydraw.domain.*;
import com.project.mydraw.service.dto.LikeCommentaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LikeCommentary} and its DTO {@link LikeCommentaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExtendedUserMapper.class, CommentaryMapper.class})
public interface LikeCommentaryMapper extends EntityMapper<LikeCommentaryDTO, LikeCommentary> {

    @Mapping(source = "extendedUser.id", target = "extendedUserId")
    @Mapping(source = "comentary.id", target = "comentaryId")
    LikeCommentaryDTO toDto(LikeCommentary likeCommentary);

    @Mapping(source = "extendedUserId", target = "extendedUser")
    @Mapping(source = "comentaryId", target = "comentary")
    LikeCommentary toEntity(LikeCommentaryDTO likeCommentaryDTO);

    default LikeCommentary fromId(Long id) {
        if (id == null) {
            return null;
        }
        LikeCommentary likeCommentary = new LikeCommentary();
        likeCommentary.setId(id);
        return likeCommentary;
    }
}
