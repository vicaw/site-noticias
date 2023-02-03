import { useState } from 'react';
import ApiError from '../models/ApiError';
import Article, { ArticleCard, GetFeedResponse } from '../models/Article';
import articleService from '../services/ArticleServices';

export default function useArticleService() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<String | String[] | null>(null);

  async function articleGetArticleFeed(page: number, category?: string) {
    setLoading(true);
    setError(null);

    try {
      const data: GetFeedResponse = await articleService.getArticleFeed(page, category);
      return data;
    } catch (e) {
      if (e instanceof ApiError) setError(e.messages);
    } finally {
      setLoading(false);
    }
  }

  async function articleSearchArticles(page: number, query: URLSearchParams) {
    setLoading(true);
    setError(null);

    try {
      const data: GetFeedResponse = await articleService.searchArticles(page, query);
      return data;
    } catch (e) {
      if (e instanceof ApiError) setError(e.messages);
    } finally {
      setLoading(false);
    }
  }

  async function articleGetAllArticles(query?: URLSearchParams) {
    setLoading(true);
    setError(null);

    try {
      const data: Article[] = await articleService.getAllArticles(query);
      return data;
    } catch (e) {
      if (e instanceof ApiError) setError(e.messages);
    } finally {
      setLoading(false);
    }
  }

  async function articleCreateArticle(formData: FormData) {
    setLoading(true);
    setError(null);

    try {
      const data: Article = await articleService.createArticle(formData);
      console.log('Returned data: ', data);
      return data;
    } catch (e) {
      if (e instanceof ApiError) setError(e.messages);
    } finally {
      setLoading(false);
    }
  }

  async function articleUpdateArticle(articleId: string, formData: FormData) {
    setLoading(true);
    setError(null);

    try {
      const data: Article = await articleService.updateArticle(articleId, formData);
      return data;
    } catch (e) {
      if (e instanceof ApiError) setError(e.messages);
    } finally {
      setLoading(false);
    }
  }

  return {
    loading,
    error,
    articleGetArticleFeed,
    articleSearchArticles,
    articleGetAllArticles,
    articleCreateArticle,
    articleUpdateArticle,
  };
}
