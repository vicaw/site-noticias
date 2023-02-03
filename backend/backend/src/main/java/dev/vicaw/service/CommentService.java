package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.comment.input.CommentEditInput;
import dev.vicaw.model.comment.input.CommentInput;
import dev.vicaw.model.comment.output.ArticleCommentsOutput;
import dev.vicaw.model.comment.output.CommentOutput;

public interface CommentService {

    public List<CommentOutput> list();

    public CommentOutput getById(Long id);

    public CommentOutput create(CommentInput commentInput);

    public CommentOutput update(Long id, CommentEditInput input);

    public void delete(Long id);

    public ArticleCommentsOutput getArticleComments(Long articleId, int pagesize, int pagenumber);

    public long getArticleCommentsCount(Long articleId);

    // public List<Comment> getChildren(Long id);

}
