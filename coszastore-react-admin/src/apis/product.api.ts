import { IProductRequest, ProductModel, ProductUpdateModel } from '../models/product.model';
import { ResponseModel } from 'models/response.model';
import { IPaginationModel } from '../models/pagination.model';
import Fetcher from "./fetcher";
import { UploadFile } from 'antd';

export default class ProductApi extends Fetcher {
    constructor() {
        super();
    }

    // getProduct(request: IProductRequest): Promise<IPaginationModel<ProductModel>> {
    //     return this.get(`${this.rootApiUrl}/api/product`, request);
    // }

    getProduct(): Promise<ResponseModel<ProductModel[]>> {
        return this.get(`${this.rootApiUrl}/api/product`);
    }

    getProductById(productId: string): Promise<ResponseModel<ProductModel>> {
        return this.get(`${this.rootApiUrl}/api/product/${productId}`);
    }
    addProduct(product: ProductUpdateModel, imageFiles: UploadFile[]): Promise<ResponseModel<ProductModel>> {
        const formData = new FormData();
        if (imageFiles && imageFiles.length > 0) {
            imageFiles.forEach(file => {
                formData.append('imageFiles[]', file as any);
            })
        }

        return this.postFromForm(`${this.rootApiUrl}/api/product`, product, formData);
    }
    updateProduct(productId: string, product: ProductUpdateModel, imageFiles: UploadFile[]): Promise<ResponseModel<ProductModel>> {
        const formData = new FormData();
        if (imageFiles && imageFiles.length > 0) {
            imageFiles.forEach(file => {
                formData.append('imageFiles[]', file as any);
            })
        }

        return this.putFromForm(`${this.rootApiUrl}/api/product/${productId}`, product, formData)
    }
    deleteProduct(productId: ProductModel): Promise<ResponseModel<boolean>> {
        return this.delete(`${this.rootApiUrl}/api/product/${productId}`);
    }
}
