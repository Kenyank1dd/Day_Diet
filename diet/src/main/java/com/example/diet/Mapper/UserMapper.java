package com.example.diet.Mapper;

import com.example.diet.Domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();
}
