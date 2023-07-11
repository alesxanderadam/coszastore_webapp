import { Table } from "antd";
import { ColumnsType } from "antd/lib/table";
import { DEFAULT_PAGE_SIZE } from "commons/constants/common.constant";
import { IPaginationModel, IPagingRequest } from "models/pagination.model";
import { useEffect, useState } from "react";

export interface IDigitalTableProps {
    reloadPage: [];
    initPagingRequest?: IPagingRequest;
    columns?: ColumnsType<any>;
    getDataSource: (filter: IPagingRequest) => Promise<IPaginationModel<any>>;
}

const DigitalTable = ({
    reloadPage,
    initPagingRequest,
    columns,
    getDataSource
}: IDigitalTableProps) => {
    const [loading, setLoading] = useState<boolean>(false);
    const [dataPaging, setDataPaging] = useState<IPaginationModel<any>>({
        pageIndex: 0,
        totalPages: 0,
        totalCount: 0,
        items: []
    });

    const [filterParams, setFilterParams] = useState<IPagingRequest>(initPagingRequest ?? {
        pageIndex: 0,
        pageSize: DEFAULT_PAGE_SIZE
    });

    useEffect(() => {
        if (reloadPage) {
            setFilterParams({
                ...filterParams,
                pageIndex: 0
            })
        }
    }, [reloadPage])

    useEffect(() => {
        fetchDataPaging();
    }, [getDataSource, filterParams])

    const fetchDataPaging = async () => {
        setLoading(true);

        const res = await getDataSource(filterParams)
        setDataPaging(res);

        setLoading(false);
    }

    const onPageChange = (page: number, size: number) => {
        setFilterParams({
            ...filterParams,
            pageIndex: page - 1,
            pageSize: size
        })
    }

    return (
        <div className="digital-table">
            <Table className="ant-border-space"
                loading={loading}
                columns={columns}
                dataSource={dataPaging.items}
                pagination={{
                    total: dataPaging.totalCount ?? 0,
                    current: filterParams.pageIndex + 1,
                    pageSize: filterParams.pageSize,
                    onChange: onPageChange
                }} />
        </div>
    )
}

export default DigitalTable;