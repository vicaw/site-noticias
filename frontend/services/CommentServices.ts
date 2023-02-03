import { AxiosError, AxiosResponse } from 'axios';
import ApiError from '../models/ApiError';
import Comment, { GetCommentsResponse, NewComment } from '../models/Comment';
import { api } from './axios/api';

const postComment = async (comment: NewComment): Promise<Comment> => {
  try {
    const res = await api.post('http://localhost:8080/api/comments/', comment);
    const content = await res.data;
    return content;
  } catch (e) {
    console.log('[CommentServices] postComment() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError('Post Comment Error', err.response?.data.code as number, err.response?.data.message);

    throw error;
  }
};

const getComments = async (articleId: string, page: number): Promise<GetCommentsResponse> => {
  try {
    const res = await api.get(`http://localhost:8080/api/comments/articles/${articleId}?page=${page}&pagesize=10`);

    const content = await res.data;
    return content;
  } catch (e) {
    console.log('[CommentServices] getComments() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError('Get Comments Error', err.response?.data.code as number, err.response?.data.message);

    throw error;
  }
};

const getCommentsCount = async (articleId: string): Promise<number> => {
  try {
    const res = await api.get(`http://localhost:8080/api/comments/articles/${articleId}/count`);

    const content = await res.data;
    return content;
  } catch (e) {
    console.log('[CommentServices] getCommentsCount() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'Get Comments Count Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
};

const deleteComment = async (commentId: string): Promise<AxiosResponse> => {
  try {
    const res = await api.delete(`http://localhost:8080/api/comments/${commentId}`);
    return res;
  } catch (e) {
    console.log('[CommentServices] deleteComment() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'Get Comments Count Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
};

const commentService = { postComment, getComments, getCommentsCount, deleteComment };

export default commentService;
