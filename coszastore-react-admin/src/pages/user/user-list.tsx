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
import DigitalTable from "components/digital-table/digital-table";
import { DigitalPopup } from "components/digital-popup/digital-popup";
const { Title } = Typography;

function User() {
    const history = useHistory();
    const [userId, setUserId] = useState<string>("");
    const [reloadPage, setReloadPage] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState<Boolean>(false);
    const [confirmLoading, setConfirmLoading] = useState(false);


    const columns: ColumnsType<UserModel> = [
        {
            key: 'username',
            title: "Người dùng",
            width: 230,
            fixed: 'left',
            render: (data: UserModel) => (
                <>
                    <Avatar.Group>
                        <Avatar className="shape-avatar" shape="square" size={40} src={data.avatar} ></Avatar>
                        <div className="avatar-info">
                            <Title level={5}>{data.username}</Title>
                            <p>{data.email}</p>
                        </div>
                    </Avatar.Group>{" "}
                </>
            ),
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

                        <Button className="mx-2 table-action-button" onClick={() => handleDeleteUser(data)}>
                            <DeleteOutlined style={{ color: "#e90000" }} />
                        </Button>;

                        <Button className="table-action-button" onClick={() => { showModal(data.id) }} type="default">
                            <RetweetOutlined style={{ color: '#08a' }} />
                        </Button>
                    </div>
                </>
            ),
        },
    ];
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

    const handleDeleteUser = (data: any) => {
        const handleConfirmDelete = () => {
            services.userApi.delUser(data.id).then((res) => {
                console.log(res)
                message.success("Xóa thành công");
                setReloadPage([]);
            }).catch((err) => message.error("Có lỗi xảy ra " + err.message));
        };

        DigitalPopup({
            title: "Bạn muốn xóa người dùng này?",
            icon: <ExclamationCircleFilled />,
            content: `Người dùng: ${data.id} sẽ bị xóa !`,
            callback: handleConfirmDelete,
        })();
    }

    const getDataSource = async () => {
        return services.userApi.getUsers();
    }
    return (
        <>
            <div>
                <Card bordered={false} className="criclebox tablespace mb-24" title="Danh sách danh mục"
                    extra={
                        <>
                            <Link to={`${PageConstant.user}/add`}>  <Button type="primary">Tạo mới +</Button></Link>
                        </>
                    }
                />

                <DigitalTable reloadPage={reloadPage} columns={columns} getDataSource={getDataSource} />
            </div>
            <UserChangePasswordModal handleOk={handleOk} userId={userId} handleCancel={handleCancel} isModalOpen={isModalOpen} confirmLoading={confirmLoading} />
        </>
    );
}

export default User;
