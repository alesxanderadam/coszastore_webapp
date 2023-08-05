package alticshaw.com.coszastore.service.imp;



import alticshaw.com.coszastore.payload.request.CategoryRequest;
import alticshaw.com.coszastore.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryServiceImp {
    List<CategoryResponse> getAllCategory();
    boolean addCategory(CategoryRequest category);
    boolean deleteCategory(CategoryRequest category);
    boolean updateCategory(CategoryRequest category);
}
