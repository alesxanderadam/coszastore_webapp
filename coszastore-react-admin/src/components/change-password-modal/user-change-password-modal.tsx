import { LockOutlined } from "@ant-design/icons";
import { Input, Form, message, Modal } from "antd";
import services from "apis";
import { UserChangePasswordModel } from "models/change-password.model";

export default function UserChangePasswordModal({ handleOk, userId, handleCancel, isModalOpen, confirmLoading }: any) {
    const [form] = Form.useForm();
    const layout = {
        wrapperCol: { span: 20 },
    };
    const onSubmit = (values: UserChangePasswordModel) => {
        values.userId = userId
        services.authApi.changePassword(values).then((res) => {
            if (res === true) {
                handleOk()
                setTimeout(() => {
                    message.success(" Đổi thành công ")
                    form.resetFields()
                }, 1100)
                return;
            } else {
                message.error("Mật khẩu phải giống nhau và có trên 6 kí tự")
            }
        }).catch((err) => {
            message.error(" Lỗi hệ thống")
        })
    };
    return (
        <>
            <Modal
                open={isModalOpen}
                onCancel={handleCancel}
                className="v-scroll-modal"
                title="Đổi mật khẩu"
                okText={"Lưu"}
                cancelText="Đóng"
                width="650px"
                onOk={form.submit}
                confirmLoading={confirmLoading}
            >
                <Form form={form} {...layout} name="change-password-modal" onFinish={onSubmit}>
                    <Form.Item className="ms-md-5 ms-sm-5" name="password" rules={[{ required: true, message: "* Mật khẩu không được bỏ trống" }]}>
                        <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Nhập mật khẩu" />
                    </Form.Item>
                    <Form.Item className="ms-md-5 ms-sm-5" name="confirmPassword" rules={[{ required: true, message: '* Xác nhận mật khẩu không được bỏ trống' }]}>
                        <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Xác nhận mật khẩu" />
                    </Form.Item>
                </Form>
            </Modal>

        </>

    )
}