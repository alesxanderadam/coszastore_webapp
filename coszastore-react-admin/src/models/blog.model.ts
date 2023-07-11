import { object } from "prop-types";
import { Status } from "./enums/status.enum";
import { IPagingRequest } from "./pagination.model";

export class BlogUpdateModel {
    title!: string;
    shortDescription!: string;
    description!: string;
    status!: Status;
}

export class BlogModel extends BlogUpdateModel {
    id!: string;
}

export interface IBlogPagingRequest extends IPagingRequest {
    search: string;
}
