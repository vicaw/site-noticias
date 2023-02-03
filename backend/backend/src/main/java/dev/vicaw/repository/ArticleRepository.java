package dev.vicaw.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.model.article.Article;
import dev.vicaw.model.article.output.ArticleFeedOutput;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ArticleRepository implements PanacheRepository<Article> {

    public Optional<Article> findBySlug(String slug) {
        return find("slug", slug).firstResultOptional();
    }

    public PanacheQuery<ArticleFeedOutput> getAllFeedInfo() {
        return find("from Article n ORDER BY n.createdAt DESC").project(ArticleFeedOutput.class);
    }

    public PanacheQuery<ArticleFeedOutput> getAllFeedInfo(Long category_id) {
        return find("from Article n WHERE n.categoryId=?1 ORDER BY n.createdAt DESC", category_id)
                .project(ArticleFeedOutput.class);
    }

    public PanacheQuery<ArticleFeedOutput> search(String query) {
        return find(
                "from Article n where CONCAT_WS(body, titulo, subtitulo, chapeu_feed, resumo_feed, titulo_feed) LIKE CONCAT('%',?1,'%') ORDER BY n.createdAt DESC",
                query).project(ArticleFeedOutput.class);
    }

}
