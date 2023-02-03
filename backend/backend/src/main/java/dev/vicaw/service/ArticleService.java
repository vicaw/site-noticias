package dev.vicaw.service;

import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import dev.vicaw.model.article.Article;
import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.input.ArticleUpdateInput;
import dev.vicaw.model.article.output.FeedOutput;
import dev.vicaw.model.article.output.ArticleOutput;

public interface ArticleService {

    public List<Article> list(Long authorId);

    public Article getById(Long id);

    public Article create(InputStream coverImage, String coverImageName, @Valid ArticleInput article);

    public Article update(Long articleId, InputStream coverImage, String coverImageName,
            @Valid ArticleUpdateInput articleInput);

    public void delete(Long articleId);

    public ArticleOutput getBySlug(String slug);

    public FeedOutput getFeedInfo(int pagesize, int page, String categorySlug);

    public FeedOutput searchArticle(String query, int pagesize, int pagenumber);

}
