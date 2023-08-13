import { Size } from "models/size.moel";
import Fetcher from "./fetcher";
import { ResponseModel } from "models/response.model";

export default class SizeApi extends Fetcher {
    constructor() {
        super();
    }

    getSizes(): Promise<ResponseModel<Size[]>> {
        return this.get(`${this.rootApiUrl}/api/size`);
    }

    getSizeById(id: string): Promise<ResponseModel<Size>> {
        return this.get(`${this.rootApiUrl}/api/size/${id}`);
    }
    addSize(Size: Size): Promise<ResponseModel<Size>> {
        return this.post(`${this.rootApiUrl}/api/size`, Size)
    }
    update(SizeID: string, Size: Size): Promise<ResponseModel<Size>> {
        return this.put(`${this.rootApiUrl}/api/size/${SizeID}`, Size)
    }
    delSize(SizeId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/size/${SizeId}`);
    }
}
