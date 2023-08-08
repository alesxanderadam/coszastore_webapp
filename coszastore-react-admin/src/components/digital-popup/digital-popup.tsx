import { Modal, message } from "antd"

export interface IDigitalPopupProps {
    title: string,
    icon: any,
    content: string,
    callback: () => void
}


export const DigitalPopup = ({ title, icon, content, callback }: IDigitalPopupProps) => {
    const { confirm } = Modal;
    return () => {
        confirm({
            title,
            icon,
            content,
            okText: "Đồng ý",
            okType: "primary",
            cancelText: "Đóng",
            onOk() {
                callback();
            }
        })
    }
}