import { message } from 'antd';
import services from 'apis';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import { CategoryUpdateModel } from 'models/category.model';
import { ResponseType } from 'models/response.model';
import { useHistory } from 'react-router-dom'
import CategoryForm from './category-form';

export default function CategoryAdd() {
    const history = useHistory();
    const onCategoryAdd = (category: CategoryUpdateModel) => {
        services.categoryApi.addCategory(category).then((res) => {
            if (res.statusCode === ResponseType.Success) {
                message.success("Thêm thành công", 1.5);
                history.push(UrlResolver.buildUrl(`${PageConstant.category}`));
                return;
            }
            message.error(`${res.message}`)
        }).catch((err) => {
            message.error("Đã xảy ra lỗi vui lòng thử lại");
        })
    }
    return (
        <>
            <CategoryForm category={null!} submitted={onCategoryAdd}></CategoryForm>
        </>
    )
}
