import { IPagingRequest } from './pagination.model';
import { Status } from "./enums/status.enum";

export class UserUpdateModel {
    name!: string;
    userName!: string;
    address!: string;
    email!: string;
    phoneNumber!: string;
    status!: Status;
}

export class UserModel extends UserUpdateModel {
    id!: string;
    img!: string;
}

export interface IUserPagingRequest extends IPagingRequest {
    search: string;
}

export interface IUserInforModel {
    userId: string;
    roles: string[];
}