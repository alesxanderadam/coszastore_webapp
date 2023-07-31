import { Row, Col, Card, Button, Input, Form, Select, Switch, Upload, Modal, InputNumber, Image } from "antd";
import services from "apis";
import { PageConstant } from "commons/page.constant";
import { UrlResolver } from "commons/url-resolver";
import { CategoryModel } from "models/category.model";
import { ProductModel, ProductUpdateModel } from 'models/product.model'
import { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import type { RcFile, UploadProps } from 'antd/es/upload';
import type { UploadFile } from 'antd/es/upload/interface';
import { PlusOutlined } from "@ant-design/icons";
import utils from "utils";
// @ts-ignore
import Editor from 'ckeditor5-custom-build/build/ckeditor';
// @ts-ignore
import { CKEditor } from '@ckeditor/ckeditor5-react'

const getBase64 = (file: RcFile): Promise<string> => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
});

const ProductForm = ({
    product,
    submitted,
}: {
    product?: ProductModel;
    submitted: (product: ProductUpdateModel, imageFiles: UploadFile[]) => void;
}) => {
    const [previewOpen, setPreviewOpen] = useState(false);
    const [previewImage, setPreviewImage] = useState('');
    const [previewTitle, setPreviewTitle] = useState('');
    const [descriptionInit, setDescriptionInit] = useState<string>('');
    const [images, setimages] = useState<string[]>([]);
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const [categorys, setCategorys] = useState<CategoryModel[]>([])

    const handleCancel = () => setPreviewOpen(false);
    const handlePreview = async (file: UploadFile) => {
        if (!file.thumbUrl && !file.preview) {
            file.preview = await getBase64(file.originFileObj as RcFile);
        }
        setPreviewImage(file.thumbUrl || (file.preview as string));
        setPreviewOpen(true);
        setPreviewTitle(file.name || file.thumbUrl!.substring(file.thumbUrl!.lastIndexOf('/') + 1));
    };
    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        setFileList(newFileList);
    };
    const uploadButton = (
        <div>
            <PlusOutlined />
            <div style={{ marginTop: 8, width: '100%' }}>Upload</div>
        </div>
    );

    const [form] = Form.useForm();
    // const [filter, setFilter] = useState<ICategoryRequest>({
    //     pageIndex: 0,
    //     pageSize: 10,
    //     search: '',
    // });
    useEffect(() => {
        if (product) {
            form.setFieldsValue(product)
            if (!product.description) {
                product.description = ''
                setDescriptionInit(product.description);
            }
            // product.productImages.map((item) => {
            //     images.push(item.id)
            //     console.log(item)
            // })
        }
    }, [product])


    const onSubmit = (values: ProductModel) => {
        // values.keepedProductImageIds = images
        const fileUpload = fileList.map(x => x.originFileObj);
        submitted(values, fileUpload);
    };

    const layout = {
        labelCol: { sm: 9, md: 7, lg: 9, xl: 6, xxl: 4 },
        wrapperCol: { sm: 12, md: 14, span: 16, xxl: 16 },
    };
    const validateMessages = {
        required: '${label} chưa nhập!',
    };

    useEffect(() => {
        services.categoryApi.getCategory(filter).then((res) => {
            setCategorys(res.items || [])
        })
    }, [])

    return (
        <div className="tabled">
            <Row gutter={[24, 0]}>
                <Col xs={24} xl={24}>
                    <Card bordered={false} className="criclebox tablespace mb-24" title={product ? "Sửa thông tin sản phẩm" : "Thêm thông tin sản phẩm"}
                        extra={
                            <>
                                <Button type="primary" onClick={() => { form.submit() }}> {product ? "Lưu" : "Thêm "} </Button>
                                <Link to={UrlResolver.buildUrl(`/${PageConstant.product}`)}>
                                    <Button type="dashed" className="mx-3">Trở lại</Button>
                                </Link>
                            </>
                        }
                    ></Card>
                </Col>
            </Row >
            <Row key={1}>
                <Col span={24}>
                    <Card bordered={false} className="criclebox">
                        <Form className="p-3" form={form} {...layout} name="nest-messages" onFinish={onSubmit} validateMessages={validateMessages} labelAlign="left" initialValues={{ size: "small" }}>
                            <Row gutter={[12, 12]}>
                                <Col sm={{ span: 12 }} md={{ span: 12 }} lg={{ span: 12 }} xl={{ span: 12 }} span={24}>
                                    <Form.Item name="categoryId" label="Danh mục" rules={[{ required: true }]}>
                                        <Select placeholder="Danh mục"
                                            options={
                                                categorys.map((item) => {
                                                    return {
                                                        label: `${item.name}`,
                                                        value: `${item.id}`
                                                    }
                                                })
                                            }
                                        />
                                    </Form.Item>
                                    <Form.Item name="code" label="Mã" rules={[{ required: true }]}>
                                        <Input />
                                    </Form.Item>

                                    <Form.Item name="name" label="Tên" rules={[{ required: true }]}>
                                        <Input />
                                    </Form.Item>

                                    <Form.Item name="basicUnit" label="Đơn vị cơ bản " rules={[{ required: true }]}>
                                        <Input />
                                    </Form.Item>
                                </Col>

                                <Col sm={{ span: 12 }} md={{ span: 12 }} lg={{ span: 12 }} xl={{ span: 12 }} span={24}>
                                    <Form.Item name="salePrice" label="Giá bán" >
                                        <InputNumber formatter={utils.$number.numberFormatter} className="w-100" />
                                    </Form.Item>

                                    <Form.Item name="importPrice" label="Giá vốn" >
                                        <InputNumber formatter={utils.$number.numberFormatter} className="w-100" />
                                    </Form.Item>

                                    <Form.Item initialValue={false} valuePropName="checked" name="isNewProduct" label="Sản phẩm mới">
                                        <Switch />
                                    </Form.Item>

                                    <Form.Item initialValue={false} valuePropName="checked" name="isBestSelling" label="Sản bán chạy">
                                        <Switch />
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
                                </Col>
                            </Row>

                            {/* <Form.Item wrapperCol={{ span: 20 }} labelCol={{ span: 3, xxl: 2 }} name="description" label="Mô Tả">
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

                            <Form.Item wrapperCol={{ span: 20 }} labelCol={{ span: 3, xl: 3, xxl: 2 }} name="imageFiles" label="Hình ảnh">
                                <Upload
                                    action={null}
                                    listType="picture-card"
                                    fileList={fileList}
                                    onPreview={handlePreview}
                                    onChange={handleChange}
                                >
                                    {fileList.length >= 8 ? null : uploadButton}

                                </Upload>
                                <Modal open={previewOpen} title={previewTitle} footer={null} onCancel={handleCancel}>
                                    <img alt="example" style={{ width: '100%' }} src={previewImage} />
                                </Modal>
                                <div className='row'>
                                    {
                                        product.productImages.map((item) => {
                                            return <div className="col-4 mt-2">
                                                <Image width='250px' height='200px' src={UrlResolver.getUrlImage(item.imageUrl)} alt='...' ></Image>
                                            </div>
                                        })
                                    }

                                </div>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div >
    )

}

export default ProductForm
