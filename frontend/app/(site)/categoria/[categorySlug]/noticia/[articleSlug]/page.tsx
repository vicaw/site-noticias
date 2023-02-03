import type { GetStaticPaths, GetStaticProps, NextPage } from 'next';
import { ParsedUrlQuery } from 'querystring';

import parse from 'html-react-parser';
import moment from 'moment';
import { notFound } from 'next/navigation';
import CommentArea from '../../../../../../components/client/comments/commentarea';
import Article from '../../../../../../models/Article';
import articleService from '../../../../../../services/ArticleServices';
import Category from '../../../../../../models/Category';
import categoryService from '../../../../../../services/CategoryServices';

type PageProps = {
  params: {
    articleId: string;
    categorySlug: string;
    articleSlug: string;
  };
};

async function Noticia({ params: { categorySlug, articleSlug, articleId } }: PageProps) {
  try {
    const noticia: Article = await articleService.getArticleBySlug(articleSlug);
    if (categorySlug !== noticia.category.slug) throw Error('Category does not match');

    return (
      <main className="max-w-4xl m-auto grid grid-cols-1 divide-y divide-gray-300 pt-32">
        <div>
          <h1 className="text-5xl font-bold tracking-tighter leading-[1.1]">{noticia?.titulo}</h1>
          <h2 className="mt-6 text-xl text-gray-600 tracking-tight mr-6 leading-[1.5]">
            {noticia?.subtitulo}
          </h2>
          <div className="mt-10 mb-10 text-gray-600">
            <p className="text-gray-700 font-bold">Por {noticia?.author.name}</p>
            <p>
              {moment(noticia?.createdAt).format('DD/MM/YYYY HH[h]mm ')}

              {noticia?.createdAt !== noticia?.updatedAt ? (
                <span className="before:content-['\B7']">
                  {' Atualizado ' + moment(noticia.updatedAt).fromNow()}
                </span>
              ) : null}
            </p>
          </div>
        </div>
        <hr />
        <article className="max-w-2xl m-auto mt-10">
          <img
            className="container col-span-4"
            src={`http://localhost:8081/images/${noticia.coverImgName}`}
            alt=""
          />
          <div className="my-10">{parse(noticia.body)}</div>
        </article>
        <hr />
        <CommentArea articleId={noticia.id} />
      </main>
    );
  } catch (error) {
    return notFound();
  }
}

export default Noticia;

export async function generateStaticParams() {
  const articles: Article[] = await articleService.getAllArticles();

  return articles.map((noticia) => ({
    articleId: noticia.id,
    articleSlug: noticia.slug,
    categorySlug: noticia.category.slug,
  }));
}
