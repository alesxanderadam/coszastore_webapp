import RoleApi from "./role.api";
import BlogApi from "./blog.api";
import UserApi from "./user.api";
import AuthApi from "./auth.api";
import CategoryApi from "./category.api";
import ProductApi from "./product.api";

const userApi = new UserApi();
const roleApi = new RoleApi();
const blogApi = new BlogApi();
const categoryApi = new CategoryApi();
const authApi = new AuthApi();
const productApi = new ProductApi();

const services = {
  userApi,
  roleApi,
  blogApi,
  categoryApi,
  authApi,
  productApi,
};

export default services;
