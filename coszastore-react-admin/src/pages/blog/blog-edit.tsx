import { useEffect, useState } from "react";
import services from "../../apis";
import { useHistory, useParams } from "react-router-dom";
import { UrlResolver } from "commons/url-resolver";
import { BlogModel, BlogUpdateModel } from "models/blog.model";
import { PageConstant } from "commons/page.constant";
import BlogForm from "./blog-form";
import { message } from "antd";

export default function BlogEdit() {
    const { id }: any = useParams();
    const [blog, setBlog] = useState<BlogModel>(null!);

    useEffect(() => {
        services.blogApi.getBlogById(id).then((res) => {
            setBlog(res);
        });
    }, []);

    const history = useHistory();

    const onEditBlog = (updateBlog: BlogUpdateModel) => {
        services.blogApi
            .update(blog.id, updateBlog)
            .then((res) => {
                if (res) {
                    message.success("Chỉnh sửa thành công", 1.5)
                    history.push(UrlResolver.buildUrl(`/${PageConstant.blog}`));
                    return;
                }
                message.error("Đã có trên hệ thống")
            })
            .catch((err) => {
                message.error("Đã xảy ra lỗi hệ thông")
            })
            .finally(() => { });
    };

    return (
        <>{blog && <BlogForm blog={blog} submitted={onEditBlog}></BlogForm>}</>
    );
}
