export class ResponseModel<T> {
    statusCode: number;
    data: T | undefined;
    message: string | undefined;
}