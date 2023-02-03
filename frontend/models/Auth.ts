import User from "./User";

export interface SignInRequest {
    email: string;
    password: string;
}

export type SignInResponse = {
    token: string;
    user: User;
};