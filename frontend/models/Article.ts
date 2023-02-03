import Category from './Category';
import User from './User';

export default interface Article {
  id: string;
  slug: string;
  titulo: string;
  subtitulo: string;
  resumo: string;
  body: string;
  createdAt: string;
  updatedAt: string;
  chapeuFeed: string;
  tituloFeed: string;
  resumoFeed: string;
  category: Category;
  author: User;
  categoryId: string;
  coverImgName: string;
}

export interface NewArticle {
  titulo: string;
  subtitulo: string;
  body: string;
  chapeuFeed: string;
  tituloFeed: string;
  resumoFeed: string;
  categoryId: string;
  authorId: string;
}

export interface ArticleCard {
  id: string;
  slug: string;
  chapeuFeed: string;
  tituloFeed: string;
  resumoFeed: string;
  createdAt: string;
  category: Category;
  coverImgName: string;
}

export interface GetFeedResponse {
  hasMore: boolean;
  articles: ArticleCard[];
}
