import { useHistory, useParams } from "react-router-dom"
import { useState, useEffect } from 'react'
import { ProductModel } from "models/product.model";
import services from "apis";
import { message } from "antd";
import { UrlResolver } from "commons/url-resolver";
import { PageConstant } from "commons/page.constant";
import ProductForm from "./product-form";
import { ResponseType } from "models/response.model";
import { UploadFile } from "antd/es/upload";
export const ProductEdit = () => {
    const { id }: any = useParams();
    const [product, setProduct] = useState<ProductModel>(null!);
    useEffect(() => {
        services.productApi.getProductById(id).then((res) => {
            setProduct(res)
        })
    }, [])
    const history = useHistory()
    const onEditProduct = (updateProduct: any, imageFiles: UploadFile[]) => {
        services.productApi.updateProduct(product.id, updateProduct, imageFiles).then((res) => {
            if (res.responseType === ResponseType.Success) {
                message.success("Sửa thành công", 1.5)
                history.push(UrlResolver.buildUrl(`${PageConstant.product}`))
                return;
            }
            else {
                if (res.responseType === ResponseType.Error) {
                    message.warning(`${res.message}`)
                    return;
                }
            }
        }).catch((err) => {
            message.error("Đã xảy ra lỗi hệ thống")
        })
    }
    return (
        <>
            {product && <ProductForm product={product} submitted={onEditProduct} ></ProductForm>}
        </>
    )
}