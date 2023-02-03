import { AxiosError } from 'axios';
import ApiError from '../models/ApiError';
import { SignInRequest, SignInResponse } from '../models/Auth';
import User, { NewUser } from '../models/User';
import { api } from './axios/api';

async function registrationRequest(data: NewUser): Promise<SignInResponse> {
  try {
    const res = await api.post(`http://localhost:8080/api/users`, data);
    const dados = await res.data;
    return dados;
  } catch (e) {
    console.log('[AccountServices] registrationRequest() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'Registration Request Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
}

async function signInRequest(data: SignInRequest): Promise<SignInResponse> {
  try {
    const res = await api.post(`http://localhost:8080/api/users/login`, data);
    const dados = await res.data;
    return dados;
  } catch (e) {
    console.log('[AccountServices] signInRequest() Error: ', e);

    const err = e as AxiosError<any>;
    const error = new ApiError(
      'SignIn Request Error',
      err.response?.data.code as number,
      err.response?.data.message
    );

    throw error;
  }
}

async function recoverUserInformation(userId: string) {
  const res = await api.get(`http://localhost:8080/api/users/profiles/${userId}`);
  const dados = await res.data;
  return dados;
}

const accountService = {
  signInRequest,
  recoverUserInformation,
  registrationRequest,
};

export default accountService;
