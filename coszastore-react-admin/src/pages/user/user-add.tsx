import services from "../../apis";
import { UserUpdateModel } from "../../models/user.model";
import UserForm from "./user-form";
import { useHistory } from "react-router-dom";
import { UrlResolver } from "commons/url-resolver";
import { PageConstant } from "commons/page.constant";
import { message } from "antd";

export default function AddUser() {
    const history = useHistory();

    const onAddUser = (user: UserUpdateModel) => {
        services.userApi
            .addUser(user)
            .then((res) => {
                if (res) {
                    message.success('Thêm thành công')
                    history.push(UrlResolver.buildUrl(`/${PageConstant.user}`));
                    return;
                }
                message.error("Đã có trên hệ thống")
            })
            .catch((err) => {
                message.error('Đã xảy ra lỗi hệ thống')
            })
            .finally(() => { });
    };

    return (
        <>
            <UserForm user={null!} submitted={onAddUser}></UserForm>
        </>
    );
}
