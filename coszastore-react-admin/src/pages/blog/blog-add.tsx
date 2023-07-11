import services from "../../apis";
import { useHistory } from "react-router-dom";
import { UrlResolver } from "commons/url-resolver";
import { BlogUpdateModel } from "models/blog.model";
import BlogForm from "./blog-form";
import { PageConstant } from "commons/page.constant";
import { message } from "antd";


export default function BlogAdd() {
    const history = useHistory();
    const onBlogAdd = (blog: BlogUpdateModel) => {
        services.blogApi
            .addBlog(blog)
            .then((res) => {
                if (res) {
                    message.success("Thêm thành công", 1.5);
                    history.push(UrlResolver.buildUrl(`${PageConstant.blog}`));
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
        <>
            <BlogForm blog={null!} submitted={onBlogAdd}></BlogForm>
        </>
    );
}