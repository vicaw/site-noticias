import moment from 'moment';
import Link from 'next/link';
import { MODAL_TYPES, useGlobalModalContext } from '../../../contexts/ModalContext';
import Article from '../../../models/Article';

interface Props {
  article: Article;
}

export default function ManageArticlesListItem({ article }: Props) {
  const { showModal } = useGlobalModalContext();

  const createModal = () => {
    showModal(MODAL_TYPES.CREATEARTICLE_MODAL, { article });
  };

  return (
    <tr
      onClick={createModal}
      className="hover:bg-red-50 hover:text-red-700 cursor-pointer odd:bg-white even:bg-slate-50"
    >
      <td className="text-red-600 hover:text-red-700 font-bold text-lg text-left py-4 pl-2 tracking-tighter w-fit">
        <a
          href={`/categoria/${article.category.slug}/noticia/${article.slug}`}
          target="_blank"
          className="hover:text-blue-500 hover:underline "
          onClick={(e) => e.stopPropagation()}
        >
          {article.titulo}
        </a>
      </td>

      <td className="mt-10 text-center tracking-tighter w-fit">
        {moment(article.updatedAt).format('DD/MM/YYYY HH[h]mm ')}
      </td>
      <td className="mt-10 text-center tracking-tighter w-fit">
        {moment(article.createdAt).format('DD/MM/YYYY HH[h]mm ')}
      </td>
    </tr>
  );
}
