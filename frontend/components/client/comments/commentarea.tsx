'use client';

import React, { useContext, useEffect, useRef, useState } from 'react';

import { AuthContext, useAuthContext } from '../../../contexts/AuthContext';

import { api } from '../../../services/axios/api';
import CommentCard from './commentcard';
import CommentForm from './commentform';

import { MODAL_TYPES, useGlobalModalContext } from '../../../contexts/ModalContext';
import Comment, { GetCommentsResponse } from '../../../models/Comment';
import useCommentService from '../../../hooks/useCommentService';

interface PageProps {
  articleId: string;
}

export default function CommentArea({ articleId }: PageProps) {
  const [comments, setComments] = useState<Comment[]>([]);
  const [showComments, setShowComments] = useState(false);
  const [count, setCount] = useState(0);

  const { user, isAuthenticated } = useAuthContext();

  const nextPage = useRef(0);
  const hasMore = useRef(false);

  const { showModal } = useGlobalModalContext();

  const { commentGetComments, commentGetCommentsCount } = useCommentService();

  const loginModal = () => {
    showModal(MODAL_TYPES.LOGIN_MODAL);
  };

  const showCommentsHandler = () => {
    setShowComments(true);
    getMoreComments();
  };

  const addComment = (comment: Comment) => {
    if (user) comment.author = user;
    setComments((comments) => [comment, ...comments]);
  };

  const removeComment = (id: string) => {
    setComments((comments) =>
      comments.filter((comment) => {
        return comment.id !== id;
      })
    );
  };

  const setData = (data: GetCommentsResponse) => {
    setComments((prev) => [...prev, ...data.comments]);
    hasMore.current = data.hasMore;
    nextPage.current = nextPage.current + 1;
  };

  const getMoreComments = async () => {
    const data = await commentGetComments(articleId, nextPage.current);

    if (data) {
      setComments((prev) => [...prev, ...data.comments]);
      hasMore.current = data.hasMore;
      nextPage.current = nextPage.current + 1;
    }
  };

  useEffect(() => {
    commentGetCommentsCount(articleId).then((count) => setCount(count || 0));
  }, []);

  return (
    <section className="container max-w-2xl m-auto pt-10 mt-10 border-none text-gray-700 text-sm pb-[200px]">
      <div>
        <span
          className={
            showComments
              ? 'text-lg mb-2 block'
              : 'text-2xl font-bold tracking-tight text-black mb-4 block'
          }
        >
          Comentários ({count})
        </span>

        {showComments && isAuthenticated ? (
          <p className="text-xs tracking-tight">
            Os comentários são de responsabilidade exclusiva de seus autores e não representam a
            opinião deste site. Se achar algo que viole os <b>termos de uso</b>, denuncie. Leia as{' '}
            <b>perguntas mais frequentes</b> para saber o que é impróprio ou ilegal.
          </p>
        ) : null}
      </div>

      {showComments && isAuthenticated ? (
        <div>
          <CommentForm articleId={articleId} addComment={addComment} />
          <div className="flex flex-col divide-y">
            {comments?.map((comment: any) => (
              <CommentCard
                key={comment.id}
                comment={comment}
                height={0}
                removeComment={removeComment}
              />
            ))}
          </div>
          {hasMore.current ? (
            <button
              className="m-auto mt-5 block border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
              onClick={getMoreComments}
            >
              Mostrar Mais
            </button>
          ) : null}
        </div>
      ) : (
        <div className="flex flex-col border items-center border-gray-300 py-6 gap-3">
          {isAuthenticated ? (
            <button
              className="border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
              onClick={showCommentsHandler}
            >
              Ver Comentários
            </button>
          ) : (
            <>
              <div>Acesse sua Conta e participe da conversa</div>
              <button
                className="border rounded-sm p-4 border-gray-300 text-base font-semibold hover:bg-gray-200"
                onClick={loginModal}
              >
                Clique aqui para fazer Login
              </button>
            </>
          )}
        </div>
      )}
    </section>
  );
}
