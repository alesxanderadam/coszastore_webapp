import { DeleteOutlined, EditOutlined, ExclamationCircleFilled } from "@ant-design/icons";
import { Card, Button, Modal, message, Tag } from "antd";
import { ColumnsType } from "antd/lib/table";
import services from "apis";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import DigitalTable from "components/digital-table/digital-table";
import { ProductModel } from "models/product.model";
import { useState, useEffect, useCallback } from "react";
import { Link, useHistory } from "react-router-dom";
import utils from "utils";
export const Product = () => {
    const history = useHistory();
    const [data, setData] = useState<ProductModel[]>([]);
    const [reloadPage, setReloadPage] = useState(null);
    const columns: ColumnsType<ProductModel> = [
        {
            title: "Mã",
            width: 120,
            dataIndex: "id",
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
            title: "Số lượng",
            width: 220,
            dataIndex: "quantity",
        },
        {
            title: "Mô tả sản phẩm",
            dataIndex: "description",
            width: 250,
            render: (shortDescription: string) => (
                <div dangerouslySetInnerHTML={{ __html: shortDescription }} />
            )
        },
        {
            title: "Kích thước",
            dataIndex: "dimensions",
            width: 200,
            render: (shortDescription: string) => (
                <div dangerouslySetInnerHTML={{ __html: shortDescription }} />
            )
        },
        {
            title: "Vật liệu",
            dataIndex: "materials",
            width: 200,
            render: (shortDescription: string) => (
                <div dangerouslySetInnerHTML={{ __html: shortDescription }} />
            )
        },
        {
            title: "Sản Phẩm mới",
            width: 200,
            render: (data: ProductModel) => (
                <>
                    <Tag color={data.is_new_product === 1 ? "red" : "gold"}>{data.is_new_product === 1 ? "Sản phẩm mới" : "Sản phẩm cũ"}</Tag>
                </>
            ),
        },
        {
            title: "Bán chạy nhất",
            width: 200,
            render: (data: ProductModel) => (
                <>
                    <Tag color={data.is_best_selling === 1 ? "red" : "gold"}>{data.is_best_selling === 1 ? "Bán chạy" : "Bán chậm"}</Tag>
                </>
            ),
        },
        {
            title: "Giá vốn",
            dataIndex: "import_price",
            width: 200,
            render: (value) => utils.$number.numberFormatter(value)
        },
        {
            title: "Giá bán",
            dataIndex: "price",
            width: 200,
            render: (value) => utils.$number.numberFormatter(value)
        },
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
    const showDeleteConfirm = useCallback(
        (data: any) => {
            confirm({
                title: "Xóa thông tin sản phẩm",
                icon: <ExclamationCircleFilled />,
                content: `Tên sản phẩm: ${data.name} sẽ bị được xóa? `,
                okText: "Đồng ý",
                okType: "primary",
                cancelText: "Không",
                onOk() {
                    services.productApi.deleteProduct(data.id).then(() => {
                        services.productApi.getProduct().then(() => {
                            message.success('Xóa thành công')
                            setReloadPage([]);;
                        });

                    });
                },
            });
        }, [])

    const getDataSource = async () => {
        return services.productApi.getProduct();
    }

    return (
        <div>
            <Card bordered={false} className="criclebox tablespace mb-24" title="Danh sách sản phẩm"
                extra={
                    <>
                        <Link to={`${PageConstant.product}/add`}>  <Button type="primary">Tạo mới +</Button></Link>
                    </>
                }
            />

            <DigitalTable reloadPage={reloadPage} columns={columns} getDataSource={getDataSource} />
        </div>
    )
}
