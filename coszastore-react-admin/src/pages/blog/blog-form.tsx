import { Row, Col, Card, Button, Input, Form, Select, message } from "antd";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { BlogModel, BlogUpdateModel } from "models/blog.model";
import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
// @ts-ignore
// import Editor from 'ckeditor5-custom-build/build/ckeditor';
// @ts-ignore
import { CKEditor } from '@ckeditor/ckeditor5-react'

const BlogForm = ({
    blog,
    submitted,
}: {
    blog?: BlogModel;
    submitted: (blog: BlogUpdateModel) => void;
}) => {
    const [form] = Form.useForm();
    const [shortDescriptionInit, setShortDescriptionInit] = useState<string>('');
    const [descriptionInit, setDescriptionInit] = useState<string>('');

    useEffect(() => {
        if (blog) {
            form.setFieldsValue(blog);
            setShortDescriptionInit(blog.shortDescription);
            setDescriptionInit(blog.description);
        }
    }, [blog]);

    const onSubmit = (values: BlogUpdateModel) => {
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
                    <Card bordered={false} className="criclebox tablespace mb-24" title={blog ? "Sửa tin tức" : "Thêm tin tức"}
                        extra={
                            <Link to={UrlResolver.buildUrl(`/${PageConstant.blog}`)}>
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
                            <Form.Item name="title" label="Tiêu Đề" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>

                            {/* <Form.Item name="shortDescription" label="Mô Tả Nhanh">
                                <CKEditor
                                    editor={Editor}
                                    data={shortDescriptionInit}
                                    onChange={(event: any, editor: any) => {
                                        const data = editor.getData();
                                        form.setFieldsValue({
                                            shortDescription: data
                                        });
                                    }}
                                />
                            </Form.Item>

                            <Form.Item name="description" label="Mô Tả">
                                <CKEditor
                                    editor={Editor}
                                    data={descriptionInit}
                                    onChange={(event: any, editor: any) => {
                                        const data = editor.getData();
                                        form.setFieldsValue({
                                            description: data
                                        });
                                    }}
                                />
                            </Form.Item> */}

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
                                <Button type="primary" htmlType="submit"> {blog ? "Chỉnh sửa" : "Thêm "} </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div >
    );
};
export default BlogForm;
