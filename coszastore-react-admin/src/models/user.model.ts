import { Status } from './enums/status.enum';
import { IPagingRequest } from './pagination.model';

export class UserUpdateModel {
    username: string;
    email: string;
    address: string;
    phone_number: string;
    avatar: string;
    status: Status;
    role: Role;
}

export class UserModel extends UserUpdateModel {
    id!: string;
}

export class Role {
    id: number;
    name: string;
    description: string;
}

export interface IUserPagingRequest extends IPagingRequest {
    search: string;
}

