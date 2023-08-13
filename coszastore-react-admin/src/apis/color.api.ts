import { Color } from "models/color.model";
import Fetcher from "./fetcher";
import { ResponseModel } from "models/response.model";

export default class ColorApi extends Fetcher {
    constructor() {
        super();
    }

    getColors(): Promise<Color[]> {
        return this.get(`${this.rootApiUrl}/api/Color`);
    }

    getColorById(id: string): Promise<ResponseModel<Color>> {
        return this.get(`${this.rootApiUrl}/api/Color/${id}`);
    }
    addColor(Color: Color): Promise<ResponseModel<Color>> {
        return this.post(`${this.rootApiUrl}/api/Color`, Color)
    }
    update(ColorID: string, Color: Color): Promise<ResponseModel<Color>> {
        return this.put(`${this.rootApiUrl}/api/Color/${ColorID}`, Color)
    }
    delColor(ColorId: string): Promise<boolean> {
        return this.delete(`${this.rootApiUrl}/api/Color/${ColorId}`);
    }
}
