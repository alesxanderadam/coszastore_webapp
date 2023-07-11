import { Button, Card, Col, Form, Row, Input } from 'antd';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import RoleUpdateModel, { RoleModel } from 'models/role.model';
import { useEffect } from 'react'
import { Link } from 'react-router-dom';

const RoleForm = ({ role, submitted }: { role?: RoleModel, submitted: (role: RoleUpdateModel) => void }) => {
    const [form] = Form.useForm();
    useEffect(() => {
        if (role) {
            form.setFieldsValue(role);
        }
    }, [role]);
    const onSubmit = (values: RoleUpdateModel) => {
        submitted(values);
    };
    const layout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 },
    };
    const validateMessages = {
        required: "${label} is required!",
    };

    return (
        <div className='tabled'>
            <Row gutter={[24, 0]}>
                <Col xs='24' xl={24}>
                    <Card bordered={false} className="criclebox tablespace mb-4" title={role ? "Sửa chức danh" : "Thêm chức danh"}
                        extra={
                            <>
                                <Link to={UrlResolver.buildUrl(`/${PageConstant.role}`)}>
                                    <Button type='dashed'>Trở lại</Button>
                                </Link>
                            </>
                        }
                    ></Card>
                </Col>
            </Row>
            <Row key={1}>
                <Col xs='24' xl={24}>
                    <Card bordered={false} className='criclebox'>
                        <Form form={form} {...layout} name="nest-messages" onFinish={onSubmit} validateMessages={validateMessages}>
                            <Form.Item name='name' label="Người dùng" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                                <Button type="primary" htmlType="submit">
                                    {role ? "Chỉnh sửa" : "Thêm"}
                                </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div>
    )
}

export default RoleForm