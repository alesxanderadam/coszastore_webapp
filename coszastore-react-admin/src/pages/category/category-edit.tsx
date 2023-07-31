import { message } from 'antd';
import services from 'apis';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import { CategoryModel, CategoryUpdateModel } from 'models/category.model';
import { ResponseType } from 'models/response.model';
import { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom'
import CategoryForm from './category-form';

export const CategoryEdit = () => {
    const { id }: any = useParams();
    const [category, setCategory] = useState<CategoryModel>(null!);
    useEffect(() => {
        services.categoryApi.getCategoryById(id).then((res) => {
            setCategory(res);
        })
    }, [])
    const history = useHistory();
    const onEditCategory = (updateCategory: CategoryUpdateModel) => {
        services.categoryApi.updateCategory(category.id, updateCategory).then((res) => {
            if (res.statusCode === ResponseType.Success) {
                message.success("Sửa thành công", 1.5)
                history.push(UrlResolver.buildUrl(`${PageConstant.category}`));
                return;
            }
            message.error(`${res.message}`)
        }).catch((err) => {
            message.error(" Đã xảy ra lỗi hệ thông");
        })
    }
    return (
        <>
            {category && <CategoryForm category={category} submitted={onEditCategory} ></CategoryForm>}
        </>
    )
}
