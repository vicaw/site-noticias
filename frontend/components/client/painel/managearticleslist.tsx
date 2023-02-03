'use client';

import { PlusIcon } from '@heroicons/react/20/solid';
import { useEffect, useRef, useState } from 'react';
import { useAuthContext } from '../../../contexts/AuthContext';
import { MODAL_TYPES, useGlobalModalContext } from '../../../contexts/ModalContext';
import useArticleService from '../../../hooks/useArticleService';
import Article from '../../../models/Article';
import ManageArticlesListItem from './managearticleslistitem';

const columns = [
  { id: 1, title: 'Título', accessor: 'titulo' },
  { id: 2, title: 'Última Edição', accessor: 'updatedAt' },
  { id: 3, title: 'Publicação', accessor: 'createdAt' },
];

export default function ManageArticlesList() {
  const [articles, setArticles] = useState<Article[]>([]);

  const { user } = useAuthContext();
  const { showModal } = useGlobalModalContext();

  const { articleGetAllArticles } = useArticleService();

  //const hasMore = useRef(false);
  //const nextPage = useRef(0);

  const getMore = async () => {
    if (!user || user.role === 'USER') return;

    const query = user.role === 'EDITOR' ? new URLSearchParams({ authorId: user.id }) : undefined;
    query?.forEach((value, key) => {
      console.log(`${key}=${value}`);
    });
    const data = await articleGetAllArticles(query);

    if (data) {
      const a = [...articles, ...data];

      a.sort((a, b) => Date.parse(b.createdAt) - Date.parse(a.createdAt));

      setArticles(a);

      //setArticles((prev) => [...prev, ...data]);
    }

    articles.sort;
  };

  const addArticle = (article: Article) => {
    setArticles((articles) => [article, ...articles]);
  };

  const createModal = () => {
    showModal(MODAL_TYPES.CREATEARTICLE_MODAL, { addArticle });
  };

  useEffect(() => {
    getMore();
  }, []);

  return (
    <>
      <div className="flex flex-col space-y-6 md:space-y-0 md:flex-row justify-between">
        <div className="mr-6">
          <h1 className="text-4xl font-semibold mb-2">Notícias</h1>
          <h2 className="text-gray-600 ml-0.5">Gerencie suas notícias</h2>
        </div>
        <div className="flex flex-wrap items-start justify-end -mb-3">
          <button
            onClick={createModal}
            className="inline-flex px-5 py-3 text-white bg-red-600 hover:bg-red-700 focus:bg-red-700 rounded-md ml-6 mb-3"
          >
            <PlusIcon className="flex-shrink-0 h-6 w-6 text-white -ml-1 mr-2" />
            Criar nova notícia
          </button>
        </div>
      </div>
      <section className="containter bg-white p-4 rounded h-full">
        <table className="containter w-full">
          <thead>
            <tr>
              {columns.map((col) => (
                <th key={col.id}>{col.title}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {articles.map((article) => (
              <ManageArticlesListItem key={article.id} article={article} />
            ))}
          </tbody>
        </table>
      </section>
    </>
  );
}

{
  /* <section className="containter bg-white p-4 rounded">
<div className="flex">
  <div>
    Pelé posta foto ao lado de Zito durante a Copa de 1958 e diz que vai
    assistir do hospital ao jogo do Brasil contra a Coreia do Sul
  </div>
  <div>São Paulo</div>
  <div>05/12/2022</div>
</div>
</section> */
}
