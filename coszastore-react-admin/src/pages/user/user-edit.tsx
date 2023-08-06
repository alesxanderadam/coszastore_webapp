import React, { useEffect, useState } from "react";
import services from "../../apis";
import { UserModel, UserUpdateModel } from "../../models/user.model";
import UserForm from "./user-form";
import { useHistory, useParams } from "react-router-dom";
import { UrlResolver } from "commons/url-resolver";
import { PageConstant } from "commons/page.constant";
import { message } from "antd";

export default function UserEdit() {
    const { id }: any = useParams();
    const [user, setUser] = useState<UserModel>(null!);

    useEffect(() => {
        services.userApi.getUserById(id).then((res) => {
            setUser(res.data);
        });
    }, []);
    const history = useHistory();

    const onEditUser = (updateUser: UserUpdateModel) => {
        services.userApi
            .update(user.id, updateUser)
            .then((res) => {
                if (res) {
                    message.success('Sửa thành công')
                    history.push(UrlResolver.buildUrl(`/${PageConstant.user}`));
                    return;
                }
                message.error("Đã có trên hệ thống");
            })
            .catch((err) => {
                message.error('Đã xảy ra lỗi hệ thống')
            })
            .finally(() => { });
    };

    return (
        <>{user && <UserForm user={user} submitted={onEditUser}></UserForm>}</>
    );
}
