import React, { Suspense } from 'react';
import { ArticleCard } from '../../../models/Article';
import NoticiaCard from './articlecard';

interface Props {
  articles: ArticleCard[];
}

function ArticlesFeed({ articles }: Props) {
  return (
    <>
      {articles.map((article) => (
        <NoticiaCard key={article.tituloFeed} {...article} />
      ))}
    </>
  );
}

export default ArticlesFeed;
