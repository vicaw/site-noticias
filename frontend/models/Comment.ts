import User from "./User";

export default interface Comment {
  id: string;
  articleId: string;
  parentId?: string;
  author: User;
  body: string;
  createdAt: string;
  children?: Comment[];
}

export interface NewComment {
  articleId: string;
  authorId?: string;
  parentId?: string | null;
  body: string;
}

export interface GetCommentsResponse {
  comments: Comment[];
  hasMore: boolean;
}
