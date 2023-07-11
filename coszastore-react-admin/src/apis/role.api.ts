import RoleUpdateModel, { IRolePagingRequest, RoleModel } from './../models/role.model';
import Fetcher from './fetcher'
import { IPaginationModel } from 'models/pagination.model';

export default class RoleApi extends Fetcher {
    constructor() {
        super();
    }

    getRolePaging(request: IRolePagingRequest): Promise<IPaginationModel<RoleModel>> {
        return this.get(`${this.rootApiUrl}/api/role/paging`, request);
    }
    getRoles(): Promise<RoleModel[]> {
        return this.get(`${this.rootApiUrl}/api/role`);
    }

    getRoleById(id: string): Promise<RoleModel> {
        return this.get(`${this.rootApiUrl}/api/role/${id}`);
    }
    addRole(role: RoleUpdateModel): Promise<RoleModel> {
        return this.post(`${this.rootApiUrl}/api/role`, role)
    }
    update(roleID: string, role: RoleUpdateModel): Promise<RoleModel> {
        return this.put(`${this.rootApiUrl}/api/role/${roleID}`, role)
    }
    delRole(roleId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/role/${roleId}`);
    }
}
