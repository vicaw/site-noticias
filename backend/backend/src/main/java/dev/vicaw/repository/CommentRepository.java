package dev.vicaw.repository;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.comment.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {

}
