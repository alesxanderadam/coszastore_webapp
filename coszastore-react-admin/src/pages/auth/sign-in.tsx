import { Button, Col, Form, Input, Layout, Row, Typography, Switch, message } from 'antd'
import signinbg from "../../assets/images/img-signin.jpg";
import services from 'apis'
import { SignInModel } from 'models/auth.model'
import { Link } from 'react-router-dom'
import { ACCESS_TOKEN, settings } from 'utils/config'

const signIn = () => {
    if (settings.getStore(ACCESS_TOKEN)) {
        window.location.href = "/"
    }
    const { Title } = Typography;
    const { Content } = Layout;
    const onFinish = (values: SignInModel) => {
        services.authApi.signIn(values).then((res: any) => {
            if (res && res.data) {
                settings.setStorage(ACCESS_TOKEN, res?.data.accessToken)
                message.success("Đăng  nhập thành công")
                setTimeout(() => {
                    window.location.href = "/"
                }, 1000)
            } else {
                message.error("Sai tài khoản hoặc mật khẩu")
                return;
            }

        }).catch((e) => {
            console.log(e)
        })
    }
    const onChange = (checked: boolean) => {
        console.log(`switch to ${checked}`);
    }
    return (
        <Content className="signin">
            <Row gutter={[24, 0]} justify="space-around">
                <Col
                    xs={{ span: 24, offset: 0 }}
                    lg={{ span: 6, offset: 2 }}
                    md={{ span: 12 }}
                >
                    <Title className="mb-15">Sign In</Title>
                    <Title className="font-regular text-muted" level={5}>
                        Enter your email and password to sign in
                    </Title>
                    <Form
                        onFinish={onFinish}
                        layout="vertical"
                        className="row-col"
                    >
                        <Form.Item
                            className="username"
                            label="Email"
                            name="email"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input your email!",
                                },
                            ]}
                        >
                            <Input placeholder="Email" />
                        </Form.Item>

                        <Form.Item
                            className="username"
                            label="Password"
                            name="password"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input your password!",
                                },
                            ]}
                        >
                            <Input placeholder="Password" />
                        </Form.Item>

                        <Form.Item
                            name="remember"
                            className="aligin-center"
                            valuePropName="checked"
                        >
                            <Switch onChange={onChange} />
                            Remember me
                        </Form.Item>

                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{ width: "100%" }}
                            >
                                SIGN IN
                            </Button>
                        </Form.Item>
                        <p className="font-semibold text-muted">
                            Don't have an account?{" "}
                            <Link to="/sign-up" className="text-dark font-bold">
                                Sign Up
                            </Link>
                        </p>
                    </Form>
                </Col>
                <Col
                    className="sign-img"
                    style={{ padding: 12 }}
                    xs={{ span: 24 }}
                    lg={{ span: 12 }}
                    md={{ span: 12 }}
                >
                    <img src={signinbg} alt="" />
                </Col>
            </Row>
        </Content>
    )
}

export default signIn


