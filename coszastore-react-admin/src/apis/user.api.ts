import { UserModel, UserUpdateModel } from "./../models/user.model";
import Fetcher from "./fetcher";
import { ResponseModel } from 'models/response.model';

// mock.onGet("/users").reply(200, {
//     users: UserMocks,
// });

// mock.onGet("/users/getById").reply((config: any) => {
//     const { userId } = config.routeQueries;

//     return [200, UserMocks.find((x) => x.id === userId)];
// });

// mock.onPost("/users").reply((config) => {
//     return new Promise(function (resolve) {
//         setTimeout(function () {
//             const data = JSON.parse(config.data);
//             UserMocks.push({ id: UserMocks.length + 1, ...data });
//             resolve([200, { message: "OK", result: true }]);
//         }, 1000);
//     });
// });

// mock.onPut("/users").reply((config) => {
//     const data = JSON.parse(config.data);

//     const user = UserMocks.find((x) => x.id === data.id);
//     user!.name = data.name;
//     user!.address = data.address;
//     user!.email = data.email;
//     user!.status = data.status;
//     user!.phoneNumber = data.phoneNumber;

//     return [200, { message: "OK", result: true }];
// });

// mock.onDelete("/users/:userId").reply((config: any) => {
//     const { userId } = config.routeParams;
//     const index = UserMocks.filter((x) => x.id !== userId);
//     return [200, { message: "OK", result: true }];
// });

export default class UserApi extends Fetcher {
    constructor() {
        super();
    }

    // getUserPaging(request: IUserPagingRequest): Promise<IPaginationModel<UserModel>> {
    //     return this.get(`${this.rootApiUrl}/api/user/paging`, request);
    // }
    getUsers(): Promise<ResponseModel<UserModel[]>> {
        return this.get(`${this.rootApiUrl}/api/user`);
    }

    getUserById(id: string): Promise<ResponseModel<UserModel>> {
        return this.get(`${this.rootApiUrl}/api/user/${id}`);
    }

    addUser(user: UserUpdateModel): Promise<ResponseModel<UserModel>> {
        return this.post(`${this.rootApiUrl}/api/user`, user);
    }

    update(userId: string, user: UserUpdateModel): Promise<ResponseModel<UserModel>> {
        return this.put(`${this.rootApiUrl}/api/user/${userId}`, user);
    }

    delUser(userId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/user/${userId}`);
    }
}
