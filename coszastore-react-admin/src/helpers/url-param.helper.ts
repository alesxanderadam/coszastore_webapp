export enum ValueType {
    Null = 0,
    Primitive = 1,
    Object = 2,
    Date = 3,
    Function = 4
}

export class UrlParamHelper {
    public buildParamUrl(input: any): string {
        if (!input) {
            return '';
        }

        const params: {key: string, value: string}[] = [];
        this.breakParamRecursive(input, '', params);

        return params.map(x => `${x.key}=${x.value}`).join('&');
    }

    public breakParamRecursive(input: any, currentParamKey: string, params: {key: string, value: string}[]) {
        if (Array.isArray(input)) {
            if (input.length === 0) {
                return;
            }

            for (let i = 0; i < input.length; i++) {
                const element = input[i];

                const paramKey = `${currentParamKey}[${i}]`;
                this.breakParamRecursive(element, paramKey, params);
            }

            return;
        }

        const valueType = this.getValueType(input);

        switch (valueType) {
            case ValueType.Null:
                params.push({key: currentParamKey, value: ''});
                break;
            case ValueType.Primitive:
                params.push({key: currentParamKey, value: input});
                break;
            case ValueType.Date:
                params.push({key: currentParamKey, value: (input as Date).toISOString()});

                break;
            case ValueType.Object:
                for (const [key, value] of Object.entries(input)) {
                    const paramKey = currentParamKey ? `${currentParamKey}.${key}` : key;
                    this.breakParamRecursive(value, paramKey, params);
                }
                break;
            default:
                break;
        }
    }

    private getValueType(val: any): ValueType {
        if (val === null) {
            return ValueType.Null;
        }

        if (val instanceof Date) {
            return ValueType.Date;
        }

        if (typeof val == "object") {
            return ValueType.Object;
        }

        if (typeof val == "function") {
            return ValueType.Function;
        }

        return ValueType.Primitive;
    }
}
