import { Status } from './enums/status.enum';
import { IPagingRequest } from './pagination.model';
export class CategoryUpdateModel {
    code!: string;
    name!: string;
    status!: Status;
}

export class CategoryModel extends CategoryUpdateModel {
    id!: string;
}

export interface ICategoryRequest extends IPagingRequest {
    search: string;
}