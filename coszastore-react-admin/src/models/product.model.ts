import { CategoryModel } from 'models/category.model';
import { IPagingRequest } from './pagination.model';
import { Status } from './enums/status.enum';

export class ProductUpdateModel {
    code!: string;
    name!: string;
    description: string;
    isNewProduct!: boolean;
    isBestSelling!: boolean;
    basicUnit!: string;
    importPrice!: number;
    salePrice!: number;
    status!: Status;
    category!: CategoryModel;
    productImages!: IProductImageModel[];
    keepedProductImageIds!: string[];
}

export class ProductModel extends ProductUpdateModel {
    id!: string;
    categoryId!: string;
}

export interface IProductRequest extends IPagingRequest {
    search: string;
}


export interface IProductImageModel {
    id: string;
    thumbnailName: string;
    imageName: string;
    thumbnailUrl: string;
    imageUrl: string;
}