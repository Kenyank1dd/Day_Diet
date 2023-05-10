package com.example.diet.Service.Impl;


import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.Request;
import com.example.diet.Mapper.RecipeMapper;
import com.example.diet.Mapper.RequestMapper;
import com.example.diet.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestMapper requestMapper;

    @Override
    @InvokeLog
    public void sendRequest(Request request) {
        requestMapper.saveRequest(request);
    }

    @Override
    @InvokeLog
    public List<Request> recieveRequest(String usrId) {
        return requestMapper.getAllRequestByUsrid(usrId);
    }

    @Override
    @InvokeLog
    public void deleteRequest(Request request) {
        requestMapper.deleteRequest(request);
    }


    @Override
    @InvokeLog
    public String findIdbyPhone(String phone){
        return requestMapper.findIdbyPhone(phone);
    }

    @Override
    @InvokeLog
    public String findNamebyId(String fromUsrId){
        return requestMapper.findNamebyId(fromUsrId);
    }
}
