/* tslint:disable #ts */
import { UploadFile } from 'antd/lib/upload/interface';
import _, { first, isArray, isEmpty, isEqual } from 'lodash';
import moment, { Moment } from 'moment';

export class Utils {
    static copy(obj, include = [], exclude = []) {
        return Object.keys(obj).reduce((target, k) => {
            if (exclude.length) {
                if (exclude.indexOf(k) < 0) {
                    target[k] = obj[k];
                }
            } else if (include.indexOf(k) > -1) {
                target[k] = obj[k];
            }
            return target;
        }, {});
    }

    static isEmpty(obj) {
        for (const key in obj) {
            if (obj.hasOwnProperty(key)) {
                return false;
            }
        }
        return true;
    }

    static isEmptyValue(obj) {
        for (const key in obj) {
            if (obj.hasOwnProperty(key)) {
                if (!isEmpty(obj[key])) return false;
            }
        }
        return true;
    }

    static getDiffs(objA, objB, fields: string[]) {
        if (!objA || !objB) {
            return null;
        }
        const temp = {};
        if (!fields || !fields.length) {
            return null;
        }
        Object.keys(objA).forEach((key) => {
            const nestedA = objA[key];
            const nestedB = objB[key];
            if (!(fields || []).includes(key) || (!nestedA && !nestedB)) {
                return null;
            }
            if (Array.isArray(nestedA) || Array.isArray(nestedB)) {
                if (!isEqual(nestedA && nestedA.sort(), nestedB && nestedB.sort())) {
                    temp[key] = { previous: nestedA, current: nestedB };
                    return null;
                }
            }
            if (typeof nestedA === 'object' && typeof nestedB === 'object') {
                const nestedDiff = this.getDiffs(nestedA, nestedB, fields);
                if (!Utils.isEmpty(nestedDiff)) {
                    temp[key] = nestedDiff;
                }
                return null;
            }
            if (nestedA === nestedB) {
                return null;
            }
            temp[key] = { previous: nestedA, current: nestedB };
        });
        return temp;
    }

    // On srcObject field name under format like: {'extensions.name': 'John'}
    // this function will process to {'extensions': {'name': 'John'}}
    static fieldWithDotToObject(srcObject: object): any {
        const targetObject = {};
        const allPropertiesInJson = Object.getOwnPropertyNames(srcObject);
        allPropertiesInJson.forEach((property) => {
            const value = srcObject[property];
            Utils.setPropertyValue(targetObject, property, value);
        });

        return targetObject;
    }

    // On srcObject field name under format like: {'extensions': {'name': 'John'}}
    // this function will process to {'extensions.name': 'John'}
    static objectToFieldWithDot(srcObject: object, processKeys?: string[]): any {
        const targetObject = {};
        Utils.objectToFieldWithDotRecursive(srcObject, targetObject, processKeys);

        return targetObject;
    }

    static objectToFieldWithDotRecursive(srcObject: object, targetObject: object, processKeys?: string[], parentProperty?: string): any {
        for (const key in srcObject) {
            if (!srcObject.hasOwnProperty(key)) {
                continue;
            }
            const notObject = typeof srcObject[key] !== 'object';
            const notProcessKey = !processKeys || !(processKeys || []).includes(key);
            const isArray = Array.isArray(srcObject[key]);
            if (notObject || isArray || notProcessKey) {
                if (parentProperty) {
                    targetObject[parentProperty + '.' + key] = srcObject[key];
                } else {
                    targetObject[key] = srcObject[key];
                }
            } else {
                const newPath = parentProperty ? parentProperty + '.' + key : key;
                Utils.objectToFieldWithDotRecursive(srcObject[key], targetObject, processKeys, newPath);
            }
        }
    }

    // Set new field and valu for object
    static setPropertyValue(obj: object, propertyRoute: string, value: any): void {
        if (typeof obj !== 'object' || typeof propertyRoute !== 'string') {
            return null;
        }
        const dotIndex = propertyRoute.indexOf('.');
        if (dotIndex >= 0) {
            const fieldName = propertyRoute.substring(0, dotIndex);
            if (!obj[fieldName]) {
                obj[fieldName] = {};
            }
            const remainingPropertyRoute = propertyRoute.substr(dotIndex + 1);

            return this.setPropertyValue(
                // tslint:disable-next-line: no-unsafe-any
                obj[fieldName],
                remainingPropertyRoute,
                value
            );
        }
        obj[propertyRoute] = value;
    }

    static removeUndefinedFields<T>(object: T): T {
        for (const key in object) {
            if (object.hasOwnProperty(key)) {
                const value = object[key];
                if (value === undefined) {
                    delete object[key];
                }
            }
        }

        return object;
    }

    static removeEmptyFields<T>(object: T): T {
        for (const key in object) {
            if (object.hasOwnProperty(key)) {
                const value = object[key] as unknown;
                if (typeof value === 'object' && !isEmpty(value)) {
                    this.removeEmptyFields(value);
                }
                if (typeof value === 'object') {
                    const obj = value as Array<string>;
                    if (isEmpty(obj) || (isArray(obj) && isEmpty(obj.filter((p) => p !== null)))) {
                        delete object[key];
                    }
                }
                if (typeof value === 'string' && value === '') {
                    delete object[key];
                }

                if (value === undefined) {
                    delete object[key];
                }
            }
        }

        return object;
    }

    static clone<T>(object: T): T {
        return JSON.parse(JSON.stringify(object));
    }

    static copyPartialObject<T>(src: Partial<T>, target: T): T {
        if (!src) return null;

        for (const key in src) {
            if (src.hasOwnProperty(key)) {
                if (src[key] !== undefined) {
                    target[key] = src[key];
                }
            }
        }

        return target;
    }

    static flattenArray<T>(array: T[], childrenFieldName: string = 'children', skipParent: boolean = false, flattenArrayParam?: T[]): T[] {
        const flattenArray: T[] = flattenArrayParam ?? [];
        for (const item of array) {
            const itemClone = _.clone(item);
            const childrenField = _.clone(itemClone[childrenFieldName]);
            const hasChildren = !isEmpty(itemClone[childrenFieldName]);

            if (!skipParent || !hasChildren) {
                delete itemClone[childrenFieldName];
                flattenArray.push(itemClone);
            }

            if (hasChildren) {
                Utils.flattenArray(childrenField, childrenFieldName, skipParent, flattenArray);
            }
        }

        return flattenArray;
    }

    static tryParseJSON(jsonString) {
        try {
            var o = JSON.parse(jsonString);

            // Handle non-exception-throwing cases:
            // Neither JSON.parse(false) or JSON.parse(1234) throw errors, hence the type-checking,
            // but... JSON.parse(null) returns null, and typeof null === "object",
            // so we must check for that, too. Thankfully, null is falsey, so this suffices:
            if (o && typeof o === 'object') {
                return o;
            }
        } catch (e) { }

        return null;
    }

    static extractUrls(content: string): string[] {
        if (isEmpty(content)) return null;

        const regex = /(https?:\/\/[^\s]+)/g;
        return content.match(regex) ?? [];
    }

    static extractFirstUrl(content: string): string {
        if (isEmpty(content)) return null;

        return first(Utils.extractUrls(content));
    }

    static getLastestLink(oldLinks: string[], newLinks: string[]): string {
        if (isEmpty(newLinks)) return null;

        if (isEmpty(oldLinks)) {
            return newLinks[newLinks.length - 1];
        }

        const newLink = newLinks.find((url) => !oldLinks.find((oldUrl) => oldUrl === url));

        return newLink;
    }

    static handleImgError(e) {
        e.target.onerror = null;
        e.target.src = 'https://i.imgur.com/T4ENOCj.png';
    }

    static extracUTCMomentDate(moment: Moment): {
        year: number;
        month: number;
        date: number;
    } {
        if (!moment) return null;

        return {
            year: moment.year(),
            month: moment.month(),
            date: moment.date(),
        };
    }

    static createMomentDate(year: number, month: number, date: number): Moment {
        if (!moment) return null;
        if (year === undefined || month === undefined || year === undefined) return null;

        const utcDate = moment(`${date}/${month + 1}/${year}`, 'DD/MM/YYYY');

        return utcDate.local();
    }

    static convertIsoDateTime(isoDateString: string): string {
        if (!isoDateString) return '---';

        return moment(isoDateString).format('DD/MM/YYYY HH:mm') || '---';
    }

    static convertIsoOnlyDate(isoDateString: string): string {
        if (!isoDateString) return '---';

        return moment(isoDateString).format('DD/MM/YYYY') || '---';
    }

    static convertIsoOnlyTime(isoDateString: string): string {
        if (!isoDateString) return '---';

        return moment(isoDateString).format('HH:mm') || '---';
    }

    static convertIsoWithFormat(isoDateString: string, format: string): string {
        if (!isoDateString) return '---';

        return moment(isoDateString).format(format) || '---';
    }

    static convertMomentDateTime(date: Moment): string {
        if (!date) return '---';

        return date.format('DD/MM/YYYY HH:mm A') || '---';
    }

    static convertMomentOnlyDate(date: Moment): string {
        if (!date) return '---';

        return date.format('DD/MM/YYYY') || '---';
    }

    static compareDateOnlyString(date1: string, date2: string): number {
        var momentA = moment(date1, 'DD/MM/YYYY');
        var momentB = moment(date2, 'DD/MM/YYYY');
        if (momentA > momentB) return 1;
        else if (momentA < momentB) return -1;
        else return 0;
    }

    static convertDateOnlyStringToDateDaily(date: string): string {
        if (!date) return '---';

        return (
            moment(date, 'DD/MM/YYYY').calendar(null, {
                lastWeek: 'DD/MM/YYYY',
                lastDay: '[Hôm qua]',
                sameDay: '[Hôm nay]',
                nextDay: '[Ngày mai]',
                nextWeek: 'DD/MM/YYYY',
                sameElse: 'DD/MM/YYYY',
            }) || '---'
        );
    }

    static convertIsoDateToDateDaily(isoDateString: string): string {
        if (!isoDateString) return '---';

        return (
            moment(isoDateString).calendar(null, {
                lastWeek: 'DD/MM/YYYY',
                lastDay: '[Hôm qua]',
                sameDay: '[Hôm nay]',
                nextDay: '[Ngày mai]',
                nextWeek: 'DD/MM/YYYY',
                sameElse: 'DD/MM/YYYY',
            }) || '---'
        );
    }

    static isToday(isoDateString: string) {
        if (!isoDateString) return false;
        const date = new Date(isoDateString);
        const today = new Date();

        return date.getDate() == today.getDate() && date.getMonth() == today.getMonth() && date.getFullYear() == today.getFullYear();
    }

    static convertIsoDateToFromStr(isoDateString: string, checkToday = true): string {
        if (!isoDateString) return '---';

        if (!Utils.isToday(isoDateString) && checkToday) return Utils.convertIsoDateTime(isoDateString);

        return moment(isoDateString).add(-1, 'second').fromNow() || '---';
    }

    static convertIsoDateToFromStrShort(isoDateString: string): string {
        if (!isoDateString) return '---';

        return moment(isoDateString).add(-1, 'second').fromNow(true);
    }

    static delay(ms: number) {
        return new Promise((resolve) => setTimeout(resolve, ms));
    }

    static getMediaUrl(file: UploadFile<any>): string {
        const response = file.response;
        if (response?.length > 0) {
            return response[0];
        }

        return null;
    }

    static replaceAt(target: string, index: number, replacement: string): string {
        return target.substr(0, index) + replacement + target.substr(index + replacement.length);
    }

    static getXorUserIdOnConversationId(conversationId: string, userId: string) {
        if (!conversationId || !userId) return null;
        const splitableStr = Utils.replaceAt(conversationId, 36, '/');
        const arr = splitableStr.split('/');

        if (isEmpty(arr)) return null;

        return arr.find((i) => i !== userId);
    }

    static addClass(root: string, className: string, condition: boolean): string {
        if (!condition || isEmpty(className)) return root;
        return `${root} ${className}`;
    }

    static popupwindow(url: string, title: string, w: number, h: number) {
        var y = window.outerHeight / 2 + window.screenY - h / 2;
        var x = window.outerWidth / 2 + window.screenX - w / 2;
        return window.open(
            url,
            title,
            'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' +
            w +
            ', height=' +
            h +
            ', top=' +
            y +
            ', left=' +
            x
        );
    }

    static printHtmlContent(htmlContent: string, w: number, h: number) {
        if (!htmlContent) return;

        const popupwindow = Utils.popupwindow(undefined, undefined, w, h);
        popupwindow.document.write(htmlContent);
        popupwindow.document.close();
        popupwindow.focus();
        popupwindow.print();
        popupwindow.onafterprint = function () {
            popupwindow.close();
        };
    }

    static processAntRangeToIsoString(dates: Moment[]) {
        if (dates?.length !== 2)
            return {
                startDate: null,
                endDate: null,
            };

        return {
            startDate: dates[0].toISOString(),
            endDate: dates[1].toISOString(),
        };
    }

    static datePickerDisableFutureDay(current: Moment) {
        return current > moment().endOf('day');
    }

    static datePickerDisableFutureAndCurrentDate(current: Moment) {
        return current >= moment().startOf('day');
    }

    static processPrice(value: number) {
        if (value === undefined || value === null || isNaN(value)) return '---';

        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
        }).format(value);
    }

    static readonly vnNumberFormatter = new Intl.NumberFormat('vi-VN');

    static formatVNNumber(value: number): string {
        if (value === undefined || value === null || isNaN(value)) return '---';

        return Utils.vnNumberFormatter.format(value);
    }

    // static clearEditorContent(editorState) {
    //     const blocks = editorState.getCurrentContent().getBlockMap().toList();
    //     const updatedSelection = editorState.getSelection().merge({
    //         anchorKey: blocks.first().get('key'),
    //         anchorOffset: 0,
    //         focusKey: blocks.last().get('key'),
    //         focusOffset: blocks.last().getLength(),
    //     });
    //     const newContentState = Modifier.removeRange(editorState.getCurrentContent(), updatedSelection, 'forward');
    //     return EditorState.push(editorState, newContentState, 'remove-range');
    // }

    static removeAccentsAndWhiteSpace(str: string) {
        return str
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .replace(/đ/g, 'd')
            .replace(/Đ/g, 'D')
            .replace(/ /g, ''); // All remove white space
    }

    static compareSearchText(searchText: string, value: string): boolean {
        const trimmedSearchText = searchText?.trim()?.toLocaleLowerCase();
        const trimmedValue = value?.trim().toLocaleLowerCase();

        if (isEmpty(trimmedSearchText)) return true;
        if (isEmpty(trimmedValue)) return false;

        const searchTextProcessed = Utils.removeAccentsAndWhiteSpace(trimmedSearchText);
        const valueProcessed = Utils.removeAccentsAndWhiteSpace(trimmedValue);

        return (valueProcessed || '').includes(searchTextProcessed);
    }

    static sortTrueOnTop(x: boolean, y: boolean): number {
        return x === y ? 0 : x ? -1 : 1;
    }

    static emptyGuid(): string {
        return '00000000-0000-0000-0000-000000000000';
    }
}
