package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.service.CommentService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import dev.vicaw.exception.ApiException;
import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.comment.CommentMapper;
import dev.vicaw.model.comment.input.CommentEditInput;
import dev.vicaw.model.comment.input.CommentInput;
import dev.vicaw.model.comment.output.ArticleCommentsOutput;
import dev.vicaw.model.comment.output.CommentOutput;

import dev.vicaw.repository.CommentRepository;

@RequestScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    CommentRepository commentRepository;;

    @Inject
    CommentMapper commentMapper;

    @Override
    public List<CommentOutput> list() {
        return commentMapper.toCommentOutputList(commentRepository.listAll());
    }

    @Override
    public CommentOutput getById(Long id) {
        Optional<Comment> comment = commentRepository.findByIdOptional(id);
        if (comment.isEmpty())
            throw new ApiException(404, "Não existe nenhum comentário com o ID informado.");

        CommentOutput commentMapped = commentMapper.toCommentOutput(comment.get());

        return commentMapped;
    }

    @Override
    @Transactional
    public CommentOutput create(CommentInput commentInput) {
        if (commentInput.getParentId() != null) {
            CommentOutput parent = getById(commentInput.getParentId());
            if (parent.getArticleId() != commentInput.getArticleId())
                throw new ApiException(400, "Erro ao processar o ID do comentário pai.");
        }

        Comment comment = commentMapper.toModel(commentInput);
        commentRepository.persist(comment);
        return commentMapper.toCommentOutput(comment);
    }

    @Override
    @Transactional
    public CommentOutput update(Long id, CommentEditInput input) {
        Optional<Comment> optional = commentRepository.findByIdOptional(id);

        if (optional.isEmpty())
            throw new ApiException(404, "Não existe nenhum comentário com o ID informado.");

        Comment comment = optional.get();

        commentMapper.updateEntityFromInput(input, comment);

        return commentMapper.toCommentOutput(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean result = commentRepository.deleteById(id);
        if (!result)
            throw new ApiException(404, "Não existe nenhum comentário com o ID informado.");
    }

    @Override
    public ArticleCommentsOutput getArticleComments(Long articleId, int pagesize, int pagenumber) {
        PanacheQuery<Comment> commentsWithoutParent = commentRepository
                .find("noticia_id = ?1 AND parent_id IS NULL order by createdAt DESC", articleId);

        PanacheQuery<Comment> page = commentsWithoutParent.page(Page.of(pagenumber, pagesize));

        List<Comment> comments = page.list();

        boolean hasMore = page.hasNextPage();

        List<CommentOutput> commentsMapped = commentMapper.toCommentOutputList(comments);

        return new ArticleCommentsOutput(hasMore, commentsMapped);
    }

    @Override
    public long getArticleCommentsCount(Long articleId) {
        long count = commentRepository.find("noticia_id", articleId).count();
        return count;
    }

    /*
     * @Override
     * public List<Comment> getChildren(Long id) {
     * return new ArrayList<Comment>();
     * }
     */

}
