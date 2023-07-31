// import { IPagingRequest } from './pagination.model';
export class CategoryUpdateModel {
    name!: string;
}

export class CategoryModel extends CategoryUpdateModel {
    id!: string;
}

// export interface ICategoryRequest extends IPagingRequest {
//     search: string;
// }