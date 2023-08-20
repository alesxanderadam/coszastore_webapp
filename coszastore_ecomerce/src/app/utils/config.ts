import axios from "axios"
import { BASE_IP_API } from "./base"
import { signOut } from "next-auth/react";

export const TOKEN_USER_MOVIE: string = "token_user_movie"

export const http = axios.create({
    baseURL: BASE_IP_API,
    timeout: 10000,
});

http.interceptors.request.use((config) => {
    config.headers = {
        ...config.headers,
        Authorization: `Bearer ${settings.getCookie(TOKEN_USER_MOVIE)}`,
    };
    return config;
}, (err) => {
    return Promise.reject(err);
});

http.interceptors.response.use((response) => {
    return response
}, async (error) => {
    try {
        if (error.response?.status === 401 || error.response?.status === 404) {
            if (error.response?.status === 401) {
                settings.eraseCookie(TOKEN_USER_MOVIE);
                await signOut();
                return;
            }
            if (error.response?.status === 404) {
                return;
            }
        }
        return Promise.reject(error)
    } catch (err) {
        return;
    }
})


export const settings = {
    setStorageJson: (name: string, data: any): void => {
        data = JSON.stringify(data);
        localStorage.setItem(name, data);
    },
    setStorage: (name: string, data: string): void => {
        localStorage.setItem(name, data)
    },
    getStorageJson: (name: string): any | undefined => {
        if (localStorage.getItem(name)) {
            const dataStore: string | undefined | null = localStorage.getItem(name);
            if (typeof dataStore == 'string') {
                const data = JSON.parse(dataStore);
                return data;
            }
            return undefined;
        }
        return;
    },
    getStore: (name: string): string | null | undefined | boolean | any => {
        if (localStorage.getItem(name)) {
            const data: string | null | undefined = localStorage.getItem(name);
            return data;
        }
        return;
    },
    setCookieJson: (name: string, value: any, days: number): void => {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        value = JSON.stringify(value);
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    },
    getCookieJson: (name: string): any => {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return JSON.parse(c.substring(nameEQ.length, c.length));
        }
        return null;
    },
    setCookie: (name: string, value: string, days: number): void => {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    },
    getCookie: (name: string): string | null => {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    },
    eraseCookie: (name: string): void => {
        document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    },
    clearStorage: (name: string) => {
        localStorage.removeItem(name);
    }

}

