import { SignInModel } from 'models/auth.model';
import { UserChangePasswordModel } from '../models/change-password.model';
import Fetcher from "./fetcher";
import { IUserInforModel } from "models/user.model";

export default class AuthApi extends Fetcher {
    constructor() {
        super();
    }

    getUserInfor(): Promise<IUserInforModel> {
        return this.get(`${this.rootApiUrl}/api/auth/infor`);
    }
    signIn(data: SignInModel): Promise<String> {
        return this.post(`${this.rootApiUrl}/api/auth/signIn`, data)
    }
    changePassword(Password: UserChangePasswordModel): Promise<Boolean> {
        return this.post(`${this.rootApiUrl}/api/auth/change-password`, Password);
    }
}
