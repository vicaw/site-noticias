export default interface User {
    id: string;
    name: string;
    role: string;
    email?: string;  
}

export interface NewUser {
    name: string;
    email: string;
    password: string;
}
  