import { useState } from 'react';
import { useAuthContext } from '../contexts/AuthContext';
import ApiError from '../models/ApiError';
import Comment, { GetCommentsResponse, NewComment } from '../models/Comment';
import commentService from '../services/CommentServices';

export default function useCommentService() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<String | String[] | null>(null);

  const { signOut } = useAuthContext();

  async function commentPostComment({ articleId, authorId, parentId, body }: NewComment) {
    setLoading(true);
    setError(null);

    try {
      const data: Comment = await commentService.postComment({
        articleId,
        authorId,
        parentId,
        body,
      });

      return data;
    } catch (e) {
      if (e instanceof ApiError) {
        if (e.code === 401) {
          signOut();
        } else {
          setError(e.messages);
        }
      }
    } finally {
      setLoading(false);
    }
  }

  async function commentGetComments(articleId: string, page: number) {
    setLoading(true);
    setError(null);

    try {
      const data: GetCommentsResponse = await commentService.getComments(articleId, page);
      return data;
    } catch (e) {
      if (e instanceof ApiError) {
        if (e.code === 401) {
          signOut();
        } else {
          setError(e.messages);
        }
      }
    } finally {
      setLoading(false);
    }
  }

  async function commentGetCommentsCount(articleId: string) {
    setLoading(true);
    setError(null);

    try {
      const count: number = await commentService.getCommentsCount(articleId);
      return count;
    } catch (e) {
      if (e instanceof ApiError) {
        if (e.code === 401) {
          signOut();
        } else {
          setError(e.messages);
        }
      }
    } finally {
      setLoading(false);
    }
  }

  async function commentDeleteComment(commentId: string) {
    setLoading(true);
    setError(null);

    try {
      const res = await commentService.deleteComment(commentId);
      return res;
    } catch (e) {
      if (e instanceof ApiError) {
        if (e.code === 401) {
          signOut();
        } else {
          setError(e.messages);
        }
      }
    } finally {
      setLoading(false);
    }
  }

  return {
    loading,
    error,
    commentPostComment,
    commentGetComments,
    commentGetCommentsCount,
    commentDeleteComment,
  };
}
