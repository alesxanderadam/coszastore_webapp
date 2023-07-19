import { IPagingRequest } from './pagination.model';
import { Status } from "./enums/status.enum";

export class UserUpdateModel {
    name!: string;
    userName!: string;
    address!: string;
    email!: string;
    phoneNumber!: string;
    status!: Status;
    avatar!: string;
}

export class UserModel extends UserUpdateModel {
    id!: string;
}

export interface IUserPagingRequest extends IPagingRequest {
    search: string;
}

export interface IUserBaseReponse<T> {
    statusCode: number;
    message: string;
    data: T
}

export interface IUserInforModel {
    userId: string;
    roles: string[];
}