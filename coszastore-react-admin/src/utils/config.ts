import axios from "axios"
import { PageRouteConstant } from "commons/page.constant";
import { isExpired } from "react-jwt";
export const DOMAIN = 'localhost:8080'
export const ACCESS_TOKEN = "accessToken"
export const USER_LOGIN = "userLogin"
export const http = axios.create({
    baseURL: DOMAIN,
    timeout: 20000,
});

http.interceptors.request.use((response) => {

    response.headers = {
        ...response.headers,
        Authorization: `Bearer ${settings.getStore(ACCESS_TOKEN)}`,
    };
    return response;
}, (error) => {
    return Promise.reject(error)
})

http.interceptors.response.use(
    (result) => {
        return result;
    },
    (error) => {
        if (error.response?.status === 400 || error.response?.status === 404) { window.location.href = PageRouteConstant.LOGIN_PAGE }
        if (error.response?.status === 401 || error.response?.status === 403) {
            const isMyTokenExpired = isExpired(settings.getStore(ACCESS_TOKEN));
            if (isMyTokenExpired) {
                settings.clearStorage(ACCESS_TOKEN);
                settings.clearStorage(USER_LOGIN);
                window.location.href = PageRouteConstant.LOGIN_PAGE;
            }
            // history.push("/login");
        }
        return Promise.reject(error)
    }
)
export const settings = {
    setStorageJson: (name, data) => {
        data = JSON.stringify(data);
        localStorage.setItem(name, data);
    },
    setStorage: (name, data) => {
        localStorage.setItem(name, data)
    },
    getStorageJson: (name) => {
        if (localStorage.getItem(name)) {
            const dataStore = localStorage.getItem(name);
            if (typeof dataStore == 'string') {
                const data = JSON.parse(dataStore);
                return data;
            }
            return undefined;
        }
        return;
    },
    getStore: (name) => {
        if (localStorage.getItem(name)) {
            const data = localStorage.getItem(name);
            return data;
        }
        return;
    },
    setCookieJson: (name, value, days) => {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        value = JSON.stringify(value);
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    },
    getCookieJson: (name) => {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return JSON.parse(c.substring(nameEQ.length, c.length));
        }
        return null;
    },
    setCookie: (name, value, days) => {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    },
    getCookie: (name) => {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    },
    eraseCookie: (name) => {
        document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    },
    clearStorage: (name) => {
        localStorage.removeItem(name);
    }
}
