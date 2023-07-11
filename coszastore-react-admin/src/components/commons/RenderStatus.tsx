import { Tag } from "antd";
import { Status } from "models/enums/status.enum";



export default function RenderStatus(props: { status: Status }) {
  return (
    <div>
      <Tag color={props.status === Status.Active ? "green" : "volcano"}>{props.status === Status.Active ? "Hoạt động " : "Không hoạt động"}</Tag>
    </div>
  );
}
