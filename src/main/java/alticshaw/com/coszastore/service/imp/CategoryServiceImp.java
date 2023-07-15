package alticshaw.com.coszastore.service.imp;



import alticshaw.com.coszastore.entity.CategoryEntity;
import alticshaw.com.coszastore.payload.request.CategoryRequest;
import alticshaw.com.coszastore.payload.response.CategoryRespone;

import java.util.List;

public interface CategoryServiceImp {
    List<CategoryRespone> getAllCategory();
    boolean addCategory(CategoryRequest category);
    boolean deleteCategory(int id);
    boolean updateCategory(CategoryRequest category);
}
