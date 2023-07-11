/*!
=========================================================
* Muse Ant Design Dashboard - v1.0.0
=========================================================
* Product Page: https://www.creative-tim.com/product/muse-ant-design-dashboard
* Copyright 2021 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/muse-ant-design-dashboard/blob/main/LICENSE.md)
* Coded by Creative Tim
=========================================================
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/
import { Switch, Route, Redirect } from "react-router-dom";
import Home from "./pages/Home";
import Tables from "./pages/Tables";
import Billing from "./pages/Billing";
import Rtl from "./pages/Rtl";
import Profile from "./pages/Profile";
// import SignUp from "./pages/SignUp";
// import SignIn from "./pages/SignIn";
import Main from "./components/layout/Main";
import "antd/dist/antd.css";
import "./assets/styles/main.css";
import "./assets/styles/responsive.css";
import User from "./pages/user/user-list";
import AddUser from "./pages/user/user-add";
import { UrlResolver } from "commons/url-resolver";
import { PageConstant, PageRouteConstant } from "commons/page.constant";
import UserEdit from "pages/user/user-edit";
import Role from "pages/role/role-list";
import AddRole from "pages/role/role-add";
import RoleEdit from "pages/role/role-edit";
import Blog from "pages/blog/blog-list";
import BlogAdd from 'pages/blog/blog-add'
import BlogEdit from "pages/blog/blog-edit";
import { useEffect, useState } from "react";
import { IUserInforModel } from "models/user.model";
import services from "apis";
import { useHistory } from "react-router-dom";
import Category from "pages/category/category-list";
import CategoryAdd from "pages/category/category-add";
import { CategoryEdit } from "pages/category/category-edit";
import { Product } from "pages/product/product-list";
import ProductAdd from "pages/product/product-add";
import { ProductEdit } from "pages/product/product-edit";
import './assets/scss/style.scss'
UrlResolver.resolve();

function App() {
	const [userInfor, setUserInfor] = useState<IUserInforModel>(null!);
	const history = useHistory();

	useEffect(() => {
		services.authApi.getUserInfor()
			.then((response) => {
				if (response) {
					setUserInfor(response);
					return;
				}

				// Unauthoried
				window.location.href = PageRouteConstant.LOGIN_PAGE;
			})
			.catch((e) => {
				// Unauthoried
				window.location.href = PageRouteConstant.LOGIN_PAGE;
			});
	}, []);

	return (
		<div className="App">
			<Switch>
				{/* <Route path={UrlResolver.buildUrl(`/sign-up`)} exact component={SignUp} />
				<Route path={UrlResolver.buildUrl(`/sign-in`)} exact component={SignIn} /> */}
				<Main>
					<Route exact path={UrlResolver.buildUrl(`/dashboard`)} component={Home} />
					<Route exact path={UrlResolver.buildUrl(`/admin/tables`)} component={Tables} />
					<Route exact path={UrlResolver.buildUrl(`/admin/billing`)} component={Billing} />
					<Route exact path={UrlResolver.buildUrl(`/admin/rtl`)} component={Rtl} />
					<Route exact path={UrlResolver.buildUrl(`/profile`)} component={Profile} />

					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.blog}`)} component={Blog} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.blog}/add`)} component={BlogAdd} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.blog}/:id/edit`)} component={BlogEdit} />

					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.role}`)} component={Role} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.role}/add`)} component={AddRole} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.role}/:id/edit`)} component={RoleEdit} />

					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.user}`)} component={User} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.user}/add`)} component={AddUser} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.user}/:id/edit`)} component={UserEdit} />

					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.category}`)} component={Category} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.category}/add`)} component={CategoryAdd} />
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.category}/:id/edit`)} component={CategoryEdit} />

					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.product}`)} component={Product}></Route>
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.product}/add`)} component={ProductAdd}></Route>
					<Route exact path={UrlResolver.buildUrl(`/${PageConstant.product}/:id/edit`)} component={ProductEdit} />
				</Main>
			</Switch>
		</div>
	);
}

export default App;
