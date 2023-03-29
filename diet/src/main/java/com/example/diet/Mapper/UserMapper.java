package com.example.diet.Mapper;

import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();
    public List<Recipe> SearchRecipe(String rec_id);

    @MapKey("family_id")
    List<Map<String,Object>> findFamilyMessagebyId(Integer userId);
    @MapKey("family_id")
    List<Map<String,Object>> findFamilyAllergenbyId(Integer userId);


}
