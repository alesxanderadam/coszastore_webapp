package alticshaw.com.coszastore.service;


import alticshaw.com.coszastore.entity.CategoryEntity;
import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.payload.request.CategoryRequest;
import alticshaw.com.coszastore.payload.response.CategoryRespone;
import alticshaw.com.coszastore.repository.CategoryRepository;
import alticshaw.com.coszastore.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
//    @Cacheable("listCategory")
    public List<CategoryRespone> getAllCategory() {
        List<CategoryRespone> responeList=new ArrayList<>();
        if(redisTemplate.hasKey("listCategory")){
             String data=redisTemplate.opsForValue().get("listCategory").toString();
            Type listType = new TypeToken<ArrayList<CategoryRespone>>(){}.getType();
            responeList = new Gson().fromJson(data, listType);
        }else {
            List<CategoryEntity> list=categoryRepository.findAll();

            for (CategoryEntity data : list) {
                CategoryRespone categoryRespone=new CategoryRespone();
                categoryRespone.setId(data.getId());
                categoryRespone.setName(data.getName());
                responeList.add(categoryRespone);
            }
            Gson gson=new Gson();
            String data=gson.toJson(responeList);
            redisTemplate.opsForValue().set("listCategory", data);
        }

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
    public boolean deleteCategory(int id) {
        boolean isSuccess=false;
        try{
            CategoryEntity category=categoryRepository.getReferenceById(id);
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
