export class ResponseModel<T> {
    statusCode: number;
    data: T | undefined;
    message: string | undefined;
}

export const ResponseType = {
    Success: 200,
    Error: 500,
    Unauthorization: 401
}
