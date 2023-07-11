import moment, { isMoment } from 'moment';
import $const from 'utils/constants';

const datetime = {
    formatDatetime(value: any, format = $const.COMMON.DATE_TIME_FORMAT['DD-MM-YYYY']) {
        if (!value) return value;
        if (isMoment(value)) return moment(value).format(format);
        return moment(value).format(format);
    },

    stringToDateTime(str: string) {
        if (!str) return undefined;
        return moment(str);
    },

    currentFirstDate() {
        var date = new Date();
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        return moment(firstDay).format($const.COMMON.DATE_TIME_FORMAT['YYYY-MM-DD']);
    },

    currentLastDate() {
        var date = new Date();
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        return moment(lastDay).format($const.COMMON.DATE_TIME_FORMAT['YYYY-MM-DD']);
    },

    firstDateOfMonth() {
        var date = new Date();
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        return moment(firstDay);
    },

    getToday() {
        var date = new Date();
        var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        return moment(today);
    },

    currentDate() {
        return moment();
    },

    systemFormatDate() {
        return 'DD-MM-YYYY';
    },

    systemFormatMonth() {
        return 'MM-YYYY';
    },

    DAY_OF_WEEKS: {
        Monday: 'Thứ 2',
        Tuesday: 'Thứ 3',
        Wednesday: 'Thứ 4',
        Thursday: 'Thứ 5',
        Friday: 'Thứ 6',
        Saturday: 'Thứ 7',
        Sunday: 'Chủ nhật',
    },
};

export default datetime;
