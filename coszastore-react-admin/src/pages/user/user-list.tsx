import { Row, Col, Card, Table, Button, Avatar, Typography, Modal, message } from "antd";
import { useState, useEffect } from "react";
import services from "../../apis";
import { Link, useHistory } from "react-router-dom";
import { IUserPagingRequest, UserModel, UserUpdateModel } from "../../models/user.model";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { DeleteOutlined, EditOutlined, ExclamationCircleFilled, RetweetOutlined } from "@ant-design/icons";
import RenderStatus from "components/commons/RenderStatus";
import UserChangePasswordModal from "../../components/change-password-modal/user-change-password-modal";
import type { ColumnsType } from 'antd/es/table';
const { Title } = Typography;

function User() {
    const history = useHistory();
    const [data, setData] = useState<UserModel[]>([]);
    const [userId, setUserId] = useState<string>("");
    const [loading, setLoading] = useState(false);
    const [totalItems, setTotalItems] = useState<number>(0);
    const [isModalOpen, setIsModalOpen] = useState<Boolean>(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [filter, setFilter] = useState<IUserPagingRequest>({
        pageIndex: 0,
        pageSize: 10,
        search: ''
    })
    var getData = () => {
        setLoading(false);
        // services.userApi.getUserPaging(filter).then((res) => {
        //     setData(res.items || [])
        //     setTotalItems(res.totalCount);
        //     setLoading(false);
        // });
        services.userApi.getUsers().then((res) => {
            setData(res.data || [])
        })
    };


    const columns: ColumnsType<UserModel> = [
        {
            key: 'name',
            title: "Người dùng",
            width: 230,
            fixed: 'left',
            render: (data: UserModel) => (
                <>
                    <Avatar.Group>
                        <Avatar className="shape-avatar" shape="square" size={40} src={data.avatar} ></Avatar>
                        <div className="avatar-info">
                            <Title level={5}>{data.userName}</Title>
                            <p>{data.email}</p>
                        </div>
                    </Avatar.Group>{" "}
                </>
            ),
        },
        {
            title: "Họ và tên",
            dataIndex: "username",
            width: 220,
        },
        {
            title: "Địa chỉ",
            dataIndex: "address",
            width: 220,
        },
        {
            title: "Trạng thái",
            render: (data: UserUpdateModel) => (
                <>
                    <RenderStatus status={data.status} />
                </>
            ),
            width: 220,
        },
        {
            title: "Số Điện Thoại",
            dataIndex: "phone_number",
            width: 220,
        },
        {
            title: "Địa chỉ",
            dataIndex: "address",
            width: 220,
        },
        {
            title: "Thao tác",
            width: 170,
            fixed: 'right',
            render: (data: UserModel, name) => (
                <>
                    <div className="ant-employed d-flex align-items-center justify-content-center">
                        <Button className="table-action-button" onClick={() => { history.push(UrlResolver.buildUrl(`/${PageConstant.user}/${data.id}/edit`)); }} type="default">
                            <EditOutlined />
                        </Button>

                        <Button className="mx-2 table-action-button" onClick={() => { showDeleteConfirm(name); }} >
                            <DeleteOutlined style={{ color: '#e90000' }} />
                        </Button>

                        <Button className="table-action-button" onClick={() => { showModal(data.id) }} type="default">
                            <RetweetOutlined style={{ color: '#08a' }} />
                        </Button>
                    </div>
                </>
            ),
        },
    ];
    const { confirm } = Modal;
    const showModal = (userId: string) => {
        setUserId(userId)
        setIsModalOpen(true);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const handleOk = () => {
        setConfirmLoading(true);
        setTimeout(() => {
            setIsModalOpen(false)
            setConfirmLoading(false);
        }, 1100);
    };
    const showDeleteConfirm = (data: any) => {
        confirm({
            title: "Xóa người dùng",
            icon: <ExclamationCircleFilled />,
            content: `Người dùng: ${data.name} sẽ bị được xóa? `,
            okText: "Đồng ý",
            okType: "primary",
            cancelText: "Không",
            onOk() {
                message.success('Xóa thành công')
                services.userApi.delUser(data.id).then(() => {
                    getData();
                });
            },
            onCancel() {
                console.log("Hủy");
            },
        });
    };

    useEffect(() => {
        getData();
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
                        <Card bordered={false} className="criclebox tablespace mb-24" title="Thông tin người dùng"
                            extra={
                                <>
                                    <Link to={`${PageConstant.user}/add`}>  <Button type="primary">Tạo mới +</Button></Link>
                                </>
                            }>
                            <div className="table-responsive">
                                <Table
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
            <UserChangePasswordModal handleOk={handleOk} userId={userId} handleCancel={handleCancel} isModalOpen={isModalOpen} confirmLoading={confirmLoading} />
        </>
    );
}

export default User;
