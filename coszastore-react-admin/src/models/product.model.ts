import { CategoryModel } from 'models/category.model';
import { IPagingRequest } from './pagination.model';
import { Tag } from './tag.model';
import { CustomBoolean } from './enums/boolean.enum';
import Item from './utils/item.util';

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
    import_price: number;
    is_best_selling: CustomBoolean;
    is_new_product: CustomBoolean;
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
    colors: Color[];
    sizes: Size[];
}

export class ProductModel extends ProductUpdateModel {
    id: number;
    category_id: number;
}


export class Color extends Item {

}
export class Size extends Item {

}

// export class Color {
//     color_id: number[] | [];
//     quantity;

// }
// export class Size {
//     size_id: number[] | [];
//     quantity: number;
// }


