export interface IPaginationModel<T> {
    pageIndex: number;
    totalPages: number;
    totalCount: number;
    items: T[];
}

export interface IPagingRequest {
    pageIndex: number;
    pageSize: number;
}