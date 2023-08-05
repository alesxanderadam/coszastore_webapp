package alticshaw.com.coszastore.service;


import alticshaw.com.coszastore.entity.CategoryEntity;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.payload.request.CategoryRequest;
import alticshaw.com.coszastore.payload.response.CategoryResponse;
import alticshaw.com.coszastore.repository.CategoryRepository;
import alticshaw.com.coszastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;
//    @Autowired
//    private RedisTemplate redisTemplate;
    @Override
//    @Cacheable("listCategory")
    public List<CategoryResponse> getAllCategory() {
        List<CategoryResponse> responeList=new ArrayList<>();
//        if(redisTemplate.hasKey("listCategory")){
//             String data=redisTemplate.opsForValue().get("listCategory").toString();
//            Type listType = new TypeToken<ArrayList<CategoryResponse>>(){}.getType();
//            responeList = new Gson().fromJson(data, listType);
//        }else {
            List<CategoryEntity> list=categoryRepository.findAll();

            for (CategoryEntity data : list) {
                CategoryResponse categoryRespone=new CategoryResponse();
                categoryRespone.setId(data.getId());
                categoryRespone.setName(data.getName());
                responeList.add(categoryRespone);
            }
//            Gson gson=new Gson();
//            String data=gson.toJson(responeList);
//            redisTemplate.opsForValue().set("listCategory", data);


        return responeList;
    }

    @Override
    public boolean addCategory(CategoryRequest request) {
        boolean isSuccess=false;
        try {
            CategoryEntity category=new CategoryEntity();
            category.setName(request.getName());
            categoryRepository.save(category);
            isSuccess=true;
        }catch (Exception e){
            throw new CustomException("Error addCategory "+e.getMessage());
        }

        return isSuccess;
    }

    @Override
    public boolean deleteCategory(CategoryRequest request) {
        boolean isSuccess=false;
        try{
            CategoryEntity category=categoryRepository.getReferenceById(request.getId());
            categoryRepository.delete(category);
            isSuccess=true;
        }catch (Exception e){
            throw new CustomException("Error deleteCategory "+e.getMessage());
        }

        return isSuccess;
    }

    @Override
    public boolean updateCategory(CategoryRequest request) {
        boolean isSuccess=false;
        try{
            CategoryEntity category=categoryRepository.getReferenceById(request.getId());
            category.setName(request.getName());
            categoryRepository.save(category);
            isSuccess=true;
        }catch (Exception e){
            throw new CustomException("Error updateCategory "+e.getMessage());
        }

        return isSuccess;
    }
}
