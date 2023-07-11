import { Row, Col, Card, Table, Button, Modal, message } from "antd";
import { useState, useEffect } from "react";
import services from "../../apis";
import { Link, useHistory } from "react-router-dom";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { BlogModel, BlogUpdateModel, IBlogPagingRequest } from "models/blog.model";
import { DeleteOutlined, EditOutlined, ExclamationCircleFilled } from "@ant-design/icons";
import RenderStatus from "components/commons/RenderStatus";
import { ColumnsType } from "antd/lib/table";

function Blog() {
    const history = useHistory();
    const [data, setData] = useState<BlogModel[]>([]);
    const [totalItems, setTotalItems] = useState<number>(0);
    const [filter, setFilter] = useState<IBlogPagingRequest>({
        pageIndex: 0,
        pageSize: 10,
        search: ''
    });
    const [loading, setLoading] = useState(false);

    const columns: ColumnsType<BlogModel> = [
        {
            title: "Tin Tức",
            dataIndex: "title",
            width: 80,
            fixed: "left"
        },
        {
            title: "Mô Tả Nhanh",
            dataIndex: "shortDescription",
            width: 100,
            render: (shortDescription: string) => (
                <div dangerouslySetInnerHTML={{ __html: shortDescription }} />
            )
        },
        {
            title: "Trạng Thái",
            render: (data: BlogUpdateModel) => (
                <>
                    <RenderStatus status={data.status} />
                </>
            ),
            width: 100,
        },
        {
            title: "Thao tác",
            width: 50,
            fixed: "right",
            render: (data: BlogModel) => (
                <>
                    <div className="ant-employed d-flex align-items-center justify-content-center">
                        <Button className="mx-2 table-action-button" onClick={() => { history.push(UrlResolver.buildUrl(`/${PageConstant.blog}/${data.id}/edit`)); }} type="default">
                            <EditOutlined />
                        </Button>
                        <Button className="table-action-button" onClick={() => { showDeleteConfirm(data); }}>
                            <DeleteOutlined style={{ color: '#e90000' }} />
                        </Button>
                    </div>
                </>
            ),
        },
    ];

    const { confirm } = Modal;
    const showDeleteConfirm = (data: any) => {
        confirm({
            title: "Bạn muốn xóa blog này?",
            icon: <ExclamationCircleFilled />,
            content: `Tiêu Đề:  ${data.title} sẽ bị xóa`,
            okText: "Yes",
            okType: "primary",
            cancelText: "No",
            onOk() {
                services.blogApi.delBlog(data.id).then(() => {
                    message.success("Xóa thành công", 1.5)

                    setFilter({
                        ...filter,
                        pageIndex: 0
                    });
                });
            },
            onCancel() {
                console.log("Cancel");
            },
        });
    };

    useEffect(() => {
        setLoading(true);

        services.blogApi.getBlogPaging(filter).then((res) => {
            setData(res.items || []);
            setTotalItems(res.totalCount);
            setLoading(false);
        });
    }, [filter]);

    const onPageChange = (page: number, size: number) => {
        setFilter({
            ...filter,
            pageIndex: page - 1,
            pageSize: size
        })
    }

    return (
        <>
            <div className="tabled">
                <Row gutter={[24, 0]}>
                    <Col xs="24" xl={24}>
                        <Card bordered={false} className="criclebox tablespace mb-24" title="Bảng tin tức"
                            extra={
                                <>
                                    <Link to={`${PageConstant.blog}/add`}>  <Button type="primary">Tạo mới +</Button></Link>
                                </>
                            }>
                            <div className="table-responsive">
                                <Table className="ant-border-space"
                                    loading={loading}
                                    columns={columns}
                                    dataSource={data}
                                    pagination={{
                                        total: totalItems ?? 0,
                                        current: filter.pageIndex + 1,
                                        pageSize: filter.pageSize,
                                        onChange: onPageChange
                                    }}
                                    sticky
                                    size="middle"
                                    scroll={{ x: 1300 }}
                                />
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
        </>
    );
}

export default Blog;
