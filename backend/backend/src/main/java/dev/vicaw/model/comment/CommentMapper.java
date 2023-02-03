package dev.vicaw.model.comment;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import dev.vicaw.model.comment.input.CommentEditInput;
import dev.vicaw.model.comment.input.CommentInput;
import dev.vicaw.model.comment.output.CommentOutput;
import dev.vicaw.model.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);

        default CommentOutput toCommentOutput(Comment comment) {
                return CommentOutput
                                .builder()
                                .id(comment.getId())
                                .articleId(comment.getArticleId())
                                .parentId(comment.getParentId() != null ? comment.getParentId() : null)
                                .author(userMapper.toUserProfileOutput(comment.getAuthor()))
                                .body(comment.getBody())
                                .createdAt(comment.getCreatedAt())
                                .updatedAt(comment.getUpdatedAt())
                                .children(
                                                comment.getChildren() != null
                                                                ? comment.getChildren().stream().map(
                                                                                child -> this.toCommentOutput(child))
                                                                                .collect(Collectors.toList())
                                                                : null)
                                .build();
        }

        List<CommentOutput> toCommentOutputList(List<Comment> comments);

        Comment toModel(CommentInput input);

        void updateEntityFromInput(CommentEditInput input, @MappingTarget Comment entity);
}