import { IPagingRequest } from './pagination.model';
export default class RoleUpdateModel {
    name!: string;
}
export class RoleModel extends RoleUpdateModel {
    id!: string;
}
export interface IRolePagingRequest extends IPagingRequest {
    search: string;
}
