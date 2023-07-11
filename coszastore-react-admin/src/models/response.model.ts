export class ResponseModel<T> {
    responseType: ResponseType | undefined;
    data: T | undefined;
    message: string | undefined;
}


export enum ResponseType {
    Success = 0,
    Error = 1,
    Warning = 2
}
