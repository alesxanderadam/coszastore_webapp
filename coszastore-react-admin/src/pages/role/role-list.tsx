import { useEffect, useState } from 'react'
import { Row, Col, Card, Table, Button, message, Modal } from "antd";
import { Link, useHistory } from 'react-router-dom';
import { IRolePagingRequest, RoleModel } from 'models/role.model';
import services from 'apis';
import { PageConstant } from 'commons/page.constant';
import { UrlResolver } from 'commons/url-resolver';
import { ExclamationCircleFilled } from '@ant-design/icons';


export default function Role() {
    const history = useHistory();
    const [data, setData] = useState<RoleModel[]>([]);
    const [loading, setLoading] = useState(false);
    const [totalItems, setTotalItems] = useState<number>(0);
    const [filter, setFilter] = useState<IRolePagingRequest>({
        pageIndex: 0,
        pageSize: 10,
        search: ''
    });
    var getData = () => {
        setLoading(true);
        services.roleApi.getRolePaging(filter).then((res) => {
            setData(res.items || [])
            setTotalItems(res.totalCount);
            setLoading(false);
        })

    }
    const columns = [
        {
            title: 'Chức danh',
            dataIndex: 'name'
        },

        {
            title: 'Action',
            width: 50,
            render: (data: RoleModel) => (
                <>
                    <div className="ant-employed d-flex align-items-center justify-content-center">
                        <Button className='mx-2' type="default" onClick={() => {
                            history.push(UrlResolver.buildUrl(`/${PageConstant.role}/${data.id}/edit`));
                        }}>
                            Chỉnh sửa
                        </Button>

                        <Button type="default" onClick={() => { showDeleteConfirm(data) }}>
                            Xóa
                        </Button>
                    </div>
                </>
            ),
        }
    ]
    const { confirm } = Modal;
    const showDeleteConfirm = (data: any) => {
        confirm({
            title: 'Bạn muốn xóa chức danh này',
            icon: <ExclamationCircleFilled />,
            content: `... ${data.name} sẽ bị xóa !`,
            okText: 'Đồng ý',
            okType: 'primary',
            cancelText: 'Không',
            onOk() {
                services.roleApi.delRole(data.id).then((res) => {
                    message.success("Xóa thành công", 1.5);

                    setFilter({
                        ...filter,
                        pageIndex: 0
                    });
                })
            },
            onCancel() { }
        })
    }
    const onPageChange = (page: number, size: number) => {
        setFilter({
            ...filter,
            pageIndex: page - 1,
            pageSize: size
        })
    }
    useEffect(() => {
        getData();
    }, [filter])
    return (
        <>
            <div className="tabled">
                <Row gutter={[24, 0]}>
                    <Col xs="24" xl={24}>
                        <Card bordered={false} className="criclebox tablespace mb-24" title="Chức danh" extra={
                            <>
                                <Link to={`${PageConstant.role}/add`}>
                                    {/* <Button type="primary">Tạo mới +</Button> */}
                                </Link>
                            </>
                        }
                        >
                            <div className="table-responsive">
                                <Table loading={loading} columns={columns} dataSource={data} pagination={{
                                    total: totalItems ?? 0,
                                    current: filter.pageIndex + 1,
                                    pageSize: filter.pageSize,
                                    onChange: onPageChange,
                                }} className="ant-border-space" />
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
        </>
    )
}
