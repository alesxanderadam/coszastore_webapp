import { BlogModel, IBlogPagingRequest } from "models/blog.model";
import { BlogUpdateModel } from "./../models/blog.model";
import Fetcher from "./fetcher";
import { IPaginationModel } from "models/pagination.model";

export default class BlogApi extends Fetcher {
    constructor() {
        super();
    }

    getBlogPaging(request: IBlogPagingRequest): Promise<IPaginationModel<BlogModel>> {
        return this.get(`${this.rootApiUrl}/api/blog/paging`, request);
    }

    getBlogs(): Promise<BlogModel[]> {
        return this.get(`${this.rootApiUrl}/api/blog`);
    }

    getBlogById(id: string): Promise<BlogModel> {
        return this.get(`${this.rootApiUrl}/api/blog/${id}`);
    }

    addBlog(blog: BlogUpdateModel): Promise<BlogModel> {
        return this.post(`${this.rootApiUrl}/api/blog`, blog);
    }

    update(blogId: string, blog: BlogUpdateModel): Promise<BlogModel> {
        return this.put(`${this.rootApiUrl}/api/blog/${blogId}`, blog);
    }

    delBlog(blogId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/blog/${blogId}`);
    }
}
