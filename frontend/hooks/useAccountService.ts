import { useContext, useState } from "react";
import { AuthContext, useAuthContext } from "../contexts/AuthContext";
import ApiError from "../models/ApiError";
import { SignInRequest, SignInResponse } from "../models/Auth";
import { NewUser } from "../models/User";
import  accountService  from "../services/AccountServices";

export default function useAccountService(){
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<String | String[] | null>(null);

    const { setSession } = useAuthContext();

    async function accountSignInRequest({ email, password }: SignInRequest){
        setLoading(true);
        setError(null);
        try{
            const data: SignInResponse = await accountService.signInRequest({ email, password })
            setSession(data.token, data.user);
        } catch(e){
            if(e instanceof ApiError){
                setError(e.messages);           
            }
        }
        setLoading(false);
    }

    async function accountRegistrationRequest({ email, password, name }: NewUser){
        setLoading(true);
        setError(null);
        try{
            const data: SignInResponse = await accountService.registrationRequest({ email, password, name })
            setSession(data.token, data.user);
        } catch(e){
            if(e instanceof ApiError){
                setError(e.messages);           
            }
        }
        setLoading(false);
    }


    return{ loading, error, accountSignInRequest, accountRegistrationRequest }

}