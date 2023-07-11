import services from "apis";
import RoleForm from "./role-form";
import { useHistory } from "react-router-dom";
import { UrlResolver } from "commons/url-resolver";
import RoleUpdateModel from "models/role.model";
import { message } from "antd";
import { PageConstant } from "commons/page.constant";

export default function AddRole() {
    const history = useHistory();

    const onAddRole = (role: RoleUpdateModel) => {
        services.roleApi.addRole(role)
            .then((res) => {
                if (res) {
                    message.success('Thêm thành công', 1.5)
                    history.push(UrlResolver.buildUrl(`${PageConstant.role}`));
                    return;
                }
                message.error("Đã có tên này trên hệ thống ")
            })
            .catch((err) => {
                message.error("Đã xảy ra lỗi hệ thông")
            })
            .finally(() => {

            });

    };

    return (
        <>
            <RoleForm role={null!} submitted={onAddRole}></RoleForm>
        </>
    );
}
