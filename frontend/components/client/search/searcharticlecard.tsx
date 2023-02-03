import moment from 'moment';
import Link from 'next/link';
import React from 'react';
import Article, { ArticleCard } from '../../../models/Article';

function SearchArticleCard(noticia: ArticleCard) {
  return (
    <div className="grid grid-cols-12 gap-4 pt-8">
      <img className="col-span-4" src="https://random.imagecdn.app/672/378" alt="" />
      <div className="flex flex-col col-span-8 gap-2">
        <p className="text-gray-600 font-semibold tracking-tight">{noticia.chapeuFeed}</p>
        <Link
          href={`/categoria/${noticia.category.slug}/noticia/${noticia.slug}`}
          scroll={true}
          className="text-red-600 hover:text-red-700 text-2xl font-bold tracking-tight"
        >
          {noticia.tituloFeed}
        </Link>
        <p className="text-gray-600 font-normal tracking-tight">{noticia.resumoFeed}</p>
        <p className="text-gray-600 text-xs">
          {moment(noticia.createdAt).fromNow()} — Em {noticia.category.name}
        </p>
      </div>
    </div>
  );
}

export default SearchArticleCard;
