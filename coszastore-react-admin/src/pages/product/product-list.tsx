import { DeleteOutlined, EditOutlined, ExclamationCircleFilled } from "@ant-design/icons";
import { Row, Col, Card, Table, Button, Modal, message, Tag, InputNumber } from "antd";
import { ColumnsType } from "antd/lib/table";
import services from "apis";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import RenderStatus from "components/commons/RenderStatus";
import { ProductModel, ProductUpdateModel } from "models/product.model";
import { IUserPagingRequest } from "models/user.model";
import { useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import utils from "utils";
export const Product = () => {
    const history = useHistory();
    const [data, setData] = useState<ProductModel[]>([]);
    const [loading, setLoading] = useState(false);
    // const [totalItems, setTotalItems] = useState<number>(0);
    // const [filter, setFilter] = useState<IUserPagingRequest>({
    //     pageIndex: 0,
    //     pageSize: 10,
    //     search: ''
    // })
    const columns: ColumnsType<ProductModel> = [
        {
            title: "Mã",
            width: 120,
            fixed: 'left',
            render: (data) => (<h5 style={{ fontWeight: '700' }}>{data}</h5>)
        },
        {
            title: "Tên sản phẩm",
            dataIndex: "name",
            width: 220,
        },
        {
            title: "Danh mục",
            width: 220,
            render: (data: ProductModel) => (
                <>
                    {data.category.name}
                </>
            ),
        },
        {
            title: "Mô tả sản phẩm",
            dataIndex: "description",
            width: 250,
            render: (shortDescription: string) => (
                <div dangerouslySetInnerHTML={{ __html: shortDescription }} />
            )
        },
        // {
        //     title: "Sản Phẩm mới",
        //     width: 200,
        //     render: (data: ProductModel) => (
        //         <>
        //             <Tag color={data.isNewProduct === true ? "red" : "gold"}>{data.isNewProduct === true ? "Spản phẩm mới" : "Sản phẩm cũ"}</Tag>
        //         </>
        //     ),
        // },
        // {
        //     title: "Bán chạy nhất",
        //     width: 200,
        //     render: (data: ProductModel) => (
        //         <>
        //             <Tag color={data.isBestSelling === true ? "red" : "gold"}>{data.isBestSelling === true ? "Bán chạy" : "Bán chậm"}</Tag>
        //         </>
        //     ),
        // },
        // {
        //     title: "Đơn vị cơ bản",
        //     dataIndex: "basicUnit",
        //     width: 200,
        // },
        // {
        //     title: "Giá vốn",
        //     dataIndex: "importPrice",
        //     width: 200,
        //     render: (value) => utils.$number.numberFormatter(value)
        // },
        {
            title: "Giá bán",
            dataIndex: "price",
            width: 200,
            render: (value) => utils.$number.numberFormatter(value)
        },
        // {
        //     title: "Trạng thái",
        //     width: 220,
        //     render: (data: ProductModel) => (
        //         <>
        //             <RenderStatus status={data.} />
        //         </>
        //     ),
        // },
        {
            title: "Thao tác",
            width: 150,
            fixed: 'right',
            render: (data: ProductModel, name: ProductModel) => (
                <>
                    <div className="ant-employed d-flex align-items-center justify-content-center">
                        <Button className="table-action-button" onClick={() => { history.push(UrlResolver.buildUrl(`/${PageConstant.product}/${data.id}/edit`)); }} type="default">
                            <EditOutlined />
                        </Button>

                        <Button className="mx-2 table-action-button" onClick={() => { showDeleteConfirm(name); }} >
                            <DeleteOutlined style={{ color: '#e90000' }} />
                        </Button>
                    </div>
                </>
            ),
        }
    ]
    const { confirm } = Modal;
    const showDeleteConfirm = (data: any) => {
        confirm({
            title: "Xóa thông tin sản phẩm",
            icon: <ExclamationCircleFilled />,
            content: `Tên sản phẩm: ${data.name} sẽ bị được xóa? `,
            okText: "Đồng ý",
            okType: "primary",
            cancelText: "Không",
            onOk() {
                message.success('Xóa thành công')
                services.productApi.deleteProduct(data.id).then(() => {
                    setLoading(true);
                    services.productApi.getProduct().then((res) => {
                        setData(res.data || [])
                        // setTotalItems(res.totalCount);
                        setLoading(false);
                    });
                });
            },
            onCancel() {
                console.log("Hủy");
            },
        });
    };


    // const onPageChange = (page: number, size: number) => {
    //     setFilter({
    //         ...filter,
    //         pageIndex: page - 1,
    //         pageSize: size
    //     })
    // }

    useEffect(() => {
        setLoading(true);
        services.productApi.getProduct().then((res) => {
            setData(res.data || [])
            // setTotalItems(res.totalCount);
            setLoading(false);
        });
    }, [])
    return (
        <>
            <div className="tabled">
                <Row gutter={[24, 0]}>
                    <Col xs={24} xl={24}>
                        <Card bordered={false} className="criclebox tablespace mb-24" title="Danh sách sản phẩm" extra={
                            <>
                                <Link to={`${PageConstant.product}/add`}><Button type="primary">Tạo mới</Button></Link>
                            </>
                        }>
                            <div className="table-responsive">
                                {/*Attribute table:  pagination={{
                                    total: totalItems ?? 0,
                                    current: filter.pageIndex + 1,
                                    pageSize: filter.pageSize,
                                    onChange: onPageChange
                                }} */}
                                <Table loading={loading} columns={columns} dataSource={data}
                                    sticky
                                    size="middle"
                                    scroll={{ x: 1300 }}
                                    className="ant-border-space" />
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
        </>
    )
}
