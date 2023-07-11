import { CategoryModel, ICategoryRequest, CategoryUpdateModel } from './../models/category.model';
import { IPaginationModel } from './../models/pagination.model';
import Fetcher from './fetcher'
import { ResponseModel } from 'models/response.model';

export default class CategoryApi extends Fetcher {
    constructor() {
        super();
    }

    getCategory(request: ICategoryRequest): Promise<IPaginationModel<CategoryModel>> {
        return this.get(`${this.rootApiUrl}/api/category`, request);
    }
    getCategoryById(id: string): Promise<CategoryModel> {
        return this.get(`${this.rootApiUrl}/api/category/${id}`);
    }
    addCategory(category: CategoryUpdateModel): Promise<ResponseModel<CategoryModel>> {
        return this.post(`${this.rootApiUrl}/api/category`, category)
    }
    updateCategory(categoryId: string, category: CategoryUpdateModel): Promise<ResponseModel<CategoryModel>> {
        return this.put(`${this.rootApiUrl}/api/category/${categoryId}`, category)
    }
    delCategory(categoryId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/category/${categoryId}`);
    }
}
