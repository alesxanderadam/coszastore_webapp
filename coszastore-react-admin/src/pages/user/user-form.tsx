import { Row, Col, Card, Button, Input, Form, Select } from "antd";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { UserModel, UserUpdateModel } from "models/user.model";
import { useEffect } from "react";
import { Link } from "react-router-dom";

const UserForm = ({
    user,
    submitted,
}: {
    user?: UserModel;
    submitted: (user: UserUpdateModel) => void;
}) => {
    const [form] = Form.useForm();
    useEffect(() => {
        if (user) {
            form.setFieldsValue(user);
        }
    }, [user]);

    const onSubmit = (values: UserUpdateModel) => {
        submitted(values);
    };

    const layout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 },
    };
    const validateMessages = {
        required: '${label} không được bỏ trống!',
        types: {
            email: '${label} không phải là Email!',
        },
    };

    const isEdit = Boolean(user);
    return (
        <div className="tabled">
            <Row gutter={[24, 0]}>
                <Col xs={24} xl={24}>
                    <Card bordered={false} className="criclebox tablespace mb-24" title={user ? "Chỉnh sửa " : "Thêm người dùng "}
                        extra={
                            <>
                                <Link to={UrlResolver.buildUrl(`/${PageConstant.user}`)}><Button type="dashed">Trở lại</Button></Link>
                            </>
                        }>
                    </Card>
                </Col>
            </Row>
            <Row key={1}>
                <Col xs={24} xl={24}>
                    <Card bordered={false} className="criclebox">
                        <Form form={form} {...layout} name="nest-messages" onFinish={onSubmit} validateMessages={validateMessages}>
                            <Form.Item name="name" label="Họ và tên" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item name="userName" label="Tên đăng nhập" rules={[{ required: true }]}>
                                <Input disabled={isEdit} />
                            </Form.Item>

                            <Form.Item name="email" label="Email" rules={[{ required: true, type: "email" }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item name="address" label="Địa chỉ" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item name="phoneNumber" label="Số điện thoại" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item initialValue={1} name="status" label="Trạng thái">
                                <Select style={{ width: 180 }} options={[
                                    {
                                        value: 1,
                                        label: "Hoạt động",
                                    },
                                    {
                                        value: 0,
                                        label: "Không Hoạt động",
                                    },
                                ]}
                                />
                            </Form.Item>
                            <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                                <Button type="primary" htmlType="submit"> {user ? 'Chỉnh sửa' : 'Thêm'} </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div >
    );
};
export default UserForm;
