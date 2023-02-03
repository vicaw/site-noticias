
export default class ApiError extends Error {
    code: number;
    messages: string[];

    constructor(title: string, code: number, messages: string[]) {
       super(title);
       this.code = code;
       this.messages = messages;
    }
}