package dev.vicaw.model.comment;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.vicaw.model.article.Article;
import dev.vicaw.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "noticia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Article.class, fetch = FetchType.LAZY)
    private Article noticia;

    @Column(name = "noticia_id")
    private Long articleId;

    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User author;

    @Column(name = "author_id")
    private Long authorId;

    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    private Comment parentComment;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> children;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
