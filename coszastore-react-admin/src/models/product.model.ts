import { CategoryModel } from 'models/category.model';
import { IPagingRequest } from './pagination.model';
import { Tag } from './tag.model';

// export class ProductUpdateModel {
//     code!: string;
//     name!: string;
//     description: string;
//     isNewProduct!: boolean;
//     isBestSelling!: boolean;
//     basicUnit!: string;
//     importPrice!: number;
//     salePrice!: number;
//     status!: number;
//     category!: CategoryModel;
//     productImages!: IProductImageModel[];
//     keepedProductImageIds!: string[];
// }

// export class ProductModel extends ProductUpdateModel {
//     id!: string;
//     categoryId!: string;
// }

// export interface IProductRequest extends IPagingRequest {
//     search: string;
// }


// export interface IProductImageModel {
//     id: string;
//     thumbnailName: string;
//     imageName: string;
//     thumbnailUrl: string;
//     imageUrl: string;
// }

export class ProductUpdateModel {
    name: string;
    price: number;
    quantity: number;
    short_description: string;
    description: string;
    category: CategoryModel;
    weight: string;
    materials: string;
    dimensions: string;
    image: null | string;
    list_image: null | string;
    tag: Tag[];
    color: string[];
    size: string[];
}

export class ProductModel extends ProductUpdateModel {
    id: number;
    category_id: number;
}