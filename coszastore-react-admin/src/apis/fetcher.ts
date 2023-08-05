import { UrlResolver } from "commons/url-resolver";
import { UrlParamHelper } from "helpers/url-param.helper";
import { ACCESS_TOKEN, settings } from "utils/config";

class Fetcher {
    private _rootApiUrl: string;

    get rootApiUrl() {
        if (!this._rootApiUrl) {
            this._rootApiUrl = UrlResolver.GetRootApiUrl();
        }

        return this._rootApiUrl;
    }

    constructor() {
        this._rootApiUrl = UrlResolver.GetRootApiUrl();
    }

    get(url: string, request?: any) {
        let params = '';
        if (request) {
            params = new UrlParamHelper().buildParamUrl(request);
        }

        const requestUrl = params ? `${url}?${params}` : url;
        return fetch(requestUrl, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${settings.getStore(ACCESS_TOKEN)}`
            }
        }).then((response) => response.json());
    };

    post(url: string, data: any) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        }).then((response) => response.json());
    };

    postFromForm(url: string, data: any, formData?: FormData) {
        const requestDataForm = this.getFromData(data, formData);

        return fetch(url, {
            method: 'POST',
            body: requestDataForm
        }).then((response) => response.json());
    };

    put(url: string, data: any) {
        return fetch(url, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        }).then((response) => response.json());
    };

    putFromForm(url: string, data: any, formData?: FormData) {
        const requestDataForm = this.getFromData(data, formData);

        return fetch(url, {
            method: 'PUT',
            body: requestDataForm
        }).then((response) => response.json());
    };

    delete(url: string) {
        return fetch(url, { method: 'DELETE' }).then((response) => response.json());
    };

    private getFromData(data: any, formData?: FormData): FormData {
        const params: { key: string, value: string }[] = [];
        new UrlParamHelper().breakParamRecursive(data, '', params);

        const dataForm = formData ? formData : new FormData();
        params.forEach(x => {
            dataForm.append(x.key, x.value);
        });

        return dataForm;
    }
}

export default Fetcher;