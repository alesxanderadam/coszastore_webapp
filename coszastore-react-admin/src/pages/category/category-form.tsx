import { Row, Col, Card, Button, Input, Form, Select, message } from "antd";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { CategoryModel, CategoryUpdateModel } from "models/category.model";
import { useEffect } from "react";
import { Link } from "react-router-dom";

const CategoryForm = ({
    category,
    submitted,
}: {
    category?: CategoryModel;
    submitted: (category: CategoryUpdateModel) => void;
}) => {
    const [form] = Form.useForm();

    useEffect(() => {
        if (category) {
            form.setFieldsValue(category);
        }
    }, [category]);

    const onSubmit = (values: CategoryModel) => {
        submitted(values);
    };

    const layout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 },
    };
    const validateMessages = {
        required: '${label} chưa nhập !!',
    };
    return (
        <div className="tabled">
            <Row gutter={[24, 0]}>
                <Col xs="24" xl={24}>
                    <Card bordered={false} className="criclebox tablespace mb-24" title={category ? "Sửa danh mục" : "Thêm danh mục"}
                        extra={
                            <Link to={UrlResolver.buildUrl(`/${PageConstant.category}`)}>
                                <Button type="dashed">Trở lại</Button>
                            </Link>
                        }
                    ></Card>
                </Col>
            </Row>
            <Row key={1}>
                <Col xs="24" xl={24}>
                    <Card bordered={false} className="criclebox">
                        <Form
                            form={form}
                            {...layout} name="nest-messages" onFinish={onSubmit} validateMessages={validateMessages}>
                            <Form.Item name="code" label="Mã" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item name="name" label="Tên" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            <Form.Item name="status" label="Trạng thái" initialValue={1}>
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
                                <Button type="primary" htmlType="submit"> {category ? "Chỉnh sửa" : "Thêm "} </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div >
    );
};
export default CategoryForm;
