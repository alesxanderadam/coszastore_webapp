import { message } from 'antd';
import services from 'apis';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import RoleUpdateModel, { RoleModel } from 'models/role.model';
import { useState, useEffect } from 'react'
import { useHistory, useParams } from 'react-router-dom'
import RoleForm from './role-form';

export default function RoleEdit() {
    const { id }: any = useParams();
    const [role, setRole] = useState<RoleModel>(null!);
    useEffect(() => {
        services.roleApi.getRoleById(id).then((res) => {
            setRole(res);
        })
    }, [])
    const history = useHistory();
    const onEditRole = (updateRole: RoleUpdateModel) => {
        services.roleApi.update(role.id, updateRole)
            .then((res) => {
                if (res) {
                    message.success('Sửa thành công', 1.5)
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
    }
    return (
        <>
            {
                role && (
                    <RoleForm role={role} submitted={onEditRole}></RoleForm>
                )
            }
        </>
    )
}
