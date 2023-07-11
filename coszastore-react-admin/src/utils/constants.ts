const APP_CONST = {
    ORGANIZATION: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
            },
            {
                id: 2,
                name: 'Không Hoạt động',
            },
        ],
    },
    USER: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    ROLE: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    WAREHOUSE_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
            },
            {
                id: 0,
                name: 'Không hoạt động',
            },
        ],
    },
    CUSTOMER_STATUS_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không hoạt động',
                color: '#c00',
            },
        ],
    },
    MENU_CONTROL: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
            },
            {
                id: 0,
                name: 'Không hoạt động',
            },
        ],
    },
    SHIPPING_ORDER: {
        STATUS: [
            {
                id: 0,
                name: 'Nháp',
                color: '#c1c157',
            },
            {
                id: 1,
                name: 'Chờ duyệt',
                color: '#c1c157',
            },
            {
                id: 3,
                name: 'Hủy',
                color: '#c00',
            },
            {
                id: 2,
                name: 'Đã duyệt',
                color: '#069255',
            },
            {
                id: 4,
                name: 'Hoàn thành',
                color: '#069255',
            },
        ],
    },
    STATISTICS: {
        STATUS: [
            {
                id: 1,
                name: 'Chờ duyệt',
                color: '#c1c157',
            },
            {
                id: 2,
                name: 'Đã duyệt',
                color: '#069255',
            },
        ],
    },
    COMMON: {
        MODAL_MODE: {
            ADD: 'ADD',
            EDIT: 'EDIT',
            CANCEL: 'CANCEL',
            VIEW: 'VIEW',
        },
        DATE_TIME_FORMAT: {
            'DD-MM-YYYY': 'DD-MM-YYYY',
            'YYYY-MM-DD': 'YYYY-MM-DD',
        },
        DAY_OF_WEEK: {
            MONDAY: 'Thứ hai',
            TUESDAY: 'Thứ ba',
            WEDNESDAY: 'Thứ tư',
            THURSDAY: 'Thứ năm',
            FRIDAY: 'Thứ sáu',
            SATURDAY: 'Thứ bảy',
            SUNDAY: 'Chủ nhật',
        },
    },
    DISTRIBUTOR_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    PROVIDER_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    ROUTE_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    CHANNEL_INFO: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    PAYMENT_METHOD: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    UNIT_TYPE: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    CURRENCY_UNIT: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
                color: '#069255',
            },
            {
                id: 0,
                name: 'Không Hoạt động',
                color: '#c00',
            },
        ],
    },
    INBOUND: {
        DOCUMENT_TYPE: [
            // {
            //     id: 1,
            //     value: 'CustomerReturn',
            //     name: 'Nhập theo đơn hàng',
            // },
            {
                id: 2,
                value: 'BuyFromProvider',
                name: 'Nhập mua từ nhà cung cấp',
            },
            {
                id: 3,
                value: 'WarehouseTransferring',
                name: 'Nhập chuyển kho',
            },
            {
                id: 4,
                value: 'EditQuantity',
                name: 'Nhập điều chỉnh',
            },
        ],
        STATUS: [
            {
                id: 1,
                value: 'Pending',
                name: 'Chờ duyệt',
                color: 'yellow',
            },
            {
                id: 3,
                value: 'Done',
                name: 'Hoàn thành',
                color: 'green',
            },
            {
                id: 4,
                value: 'Cancel',
                name: 'Hủy',
                color: 'red',
            },
        ],
    },
    OUTBOUND: {
        STATUS: [
            {
                id: 1,
                name: 'Chờ duyệt',
                value: 'pending',
                color: 'yellow',
            },
            {
                id: 3,
                name: 'Hoàn thành',
                value: 'done',
                color: 'green',
            },
            {
                id: 4,
                name: 'Hủy bỏ',
                value: 'cancel',
                color: 'red',
            },
        ],
        DOCUMENT_TYPE: [
            {
                id: 1,
                name: 'Xuất theo đơn đặt hàng',
                value: 'CustomerOrder',
            },
            // {
            //   id: 2,
            //   name: 'Xuất hàng trả nhà cung cấp',
            //   value: 'ReturnToProvider',
            // },
            {
                id: 3,
                name: 'Xuất chuyển kho',
                value: 'WarehouseTransferring',
            },
            {
                id: 4,
                name: 'Xuất điều chỉnh',
                value: 'EditQuantity',
            },
        ],
    },
    PERMISSION: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
            },
            {
                id: 0,
                name: 'Không hoạt động',
            },
        ],
    },
    ROLE_PERMISSION: {
        STATUS: [
            {
                id: 1,
                name: 'Hoạt động',
            },
            {
                id: 0,
                name: 'Không hoạt động',
            },
        ],
    },
    COMMON_STATUS: [
        {
            id: 1,
            status: 1,
            statusName: 'Hoạt động',
        },
        {
            id: 2,
            status: 0,
            statusName: 'Không hoạt động',
        },
    ],
};

export default APP_CONST;
