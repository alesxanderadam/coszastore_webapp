import { message, UploadFile } from 'antd';
import services from 'apis';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import { ProductUpdateModel } from 'models/product.model';
import { ResponseType } from 'models/response.model';
import { useHistory } from 'react-router'
import ProductForm from './product-form';

const ProductAdd = () => {
    const history = useHistory();
    const onProductAdd = (product: ProductUpdateModel, imageFiles: UploadFile[]) => {
        services.productApi.addProduct(product, imageFiles).then((res) => {
            if (res.statusCode === ResponseType.Success) {
                message.success("Thêm thành công", 1.5);
                history.push(UrlResolver.buildUrl(`${PageConstant.product}`));
                return;
            }
            else {
                if (res.statusCode === ResponseType.Error) {
                    message.warning(`${res.message}`)
                    return;
                }
            }
        }).catch((err) => {
            message.error("Đã xảy ra lỗi vui lòng thử lại");
        })
    }
    return (
        <div>
            <ProductForm product={null!} submitted={onProductAdd}></ProductForm>
        </div>
    )
}

export default ProductAdd;
