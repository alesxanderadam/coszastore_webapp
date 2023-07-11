export class UrlResolver {
    static data: UrlResolver;

    publicUrl: string | undefined;
    rootApiUrl: string | undefined;

    public static resolve() {
        if (!UrlResolver.data) {
            UrlResolver.data = {
                publicUrl: process.env.PUBLIC_URL || '',
                rootApiUrl: process.env.REACT_APP_ROOT_API_URL || '',
            } as UrlResolver;
        }

        return UrlResolver.data;
    }

    public static buildUrl(url: string): string {
        let result = UrlResolver.data.publicUrl;

        if (!result) {
            return url.startsWith('/') ? url : `/${url}`;
        }

        result = result?.startsWith('/') ? result : `/${result}`;
        result += url.startsWith('/') ? url : `/${url}`;
        return result;
    }

    public static GetRootApiUrl() {
        const urlResolver = UrlResolver.resolve();

        return urlResolver.rootApiUrl;
    }

    public static getUrlImage(imageUrl: any) {
        return `${UrlResolver.resolve().rootApiUrl}${imageUrl}`;
    }
}