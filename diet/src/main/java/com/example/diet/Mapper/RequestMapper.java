package com.example.diet.Mapper;


import com.example.diet.Domain.Request;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RequestMapper {

    void saveRequest(Request request);

    List<Request> getAllRequestByUsrid(String usrId);

    void deleteRequest(Request request);

    String findIdbyPhone(String phone);

    String findNamebyId(String fromUsrId);

}
