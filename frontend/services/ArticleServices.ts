import { AxiosError } from 'axios';
import ApiError from '../models/ApiError';
import Article, { ArticleCard, GetFeedResponse, NewArticle } from '../models/Article';
import { api } from './axios/api';

async function createArticle(data: FormData): Promise<Article> {
  try {
    const res = await api.post(`http://localhost:8080/api/articles`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    const content = await res.data;
    return content;
  } catch (e) {
    console.log('[ArticleServices] createArticle() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'Create Article Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
}

async function updateArticle(articleId: string, data: FormData): Promise<Article> {
  try {
    const res = await api.put(`http://localhost:8080/api/articles/${articleId}`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    const content = await res.data;
    return content;
  } catch (e) {
    console.log('[ArticleServices] updateArticle() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'Update Article Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
}

async function getArticleFeed(page: number, category?: string): Promise<GetFeedResponse> {
  const url = category
    ? `http://localhost:8080/api/articles/feedinfo?category=${category}&page=${page}&pagesize=10`
    : `http://localhost:8080/api/articles/feedinfo?page=${page}&pagesize=10`;

  const response = await fetch(url, {
    next: { revalidate: 1 },
  });
  const data = await response.json();

  if (response.ok) return data as GetFeedResponse;

  console.log('[ArticleServices] getArticleFeed() Error:', data.message);
  const error = new ApiError('Get Article Feed Error', data.message, data.code);

  return { hasMore: false, articles: [] };
  //throw error;
}

async function getAllArticles(query?: URLSearchParams): Promise<Article[]> {
  let url = 'http://localhost:8080/api/articles?';

  query?.forEach((value, key) => {
    url = url.concat(`${key}=${value}`);
  });

  console.log(url);

  const response = await fetch(url, {
    next: { revalidate: 1 },
  });
  const data = await response.json();

  if (response.ok) return data as Article[];

  console.log('[ArticleServices] getAllArticles() Error:', data.message);
  const error = new ApiError('Get All Articles Error', data.message, data.code);
  throw error;
}

async function getArticleBySlug(slug: string): Promise<Article> {
  const response = await fetch(`http://localhost:8080/api/articles/slugs/${slug}`, {
    next: { revalidate: 1 },
  });
  const data = await response.json();

  if (response.ok) return data as Article;

  console.log('[ArticleServices] getArticleBySlug() Error:', data.message);
  const error = new ApiError('Get Article By Slug Error', data.message, data.code);
  throw error;
}

async function searchArticles(page: number, query: URLSearchParams): Promise<GetFeedResponse> {
  let url = `http://localhost:8080/api/articles/search?page=${page}&pagesize=10`;

  query.forEach((value, key) => {
    url = url.concat(`&${key}=${value}`);
  });

  const response = await fetch(url, {
    next: { revalidate: 1 },
  });
  const data = await response.json();

  if (response.ok) return data as GetFeedResponse;

  console.log('[ArticleServices] searchArticles() Error:', data.message);
  const error = new ApiError('Search Articles Error', data.message, data.code);

  return { hasMore: false, articles: [] };
}

const articleService = {
  getArticleFeed,
  getArticleBySlug,
  getAllArticles,
  searchArticles,
  createArticle,
  updateArticle,
};

export default articleService;
