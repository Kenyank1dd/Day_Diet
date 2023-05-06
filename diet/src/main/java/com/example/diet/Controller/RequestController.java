package com.example.diet.Controller;

import com.example.diet.Domain.Request;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Domain.User;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RequestService;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @PostMapping("/request/send")
    public ResponseResult sendRequest(@RequestBody Request request) {
        User user = userService.findUserbyId(request.getTo_usr_id());
        if(user == null) return new ResponseResult(201,"无该用户");
        System.out.println(request);
        requestService.sendRequest(request);
        return new ResponseResult(200,"成功发送请求");
    }

    @PostMapping("/request/receive")
    public ResponseResult recieveRequest(@CurrentUserId String usrId) {
        List<Request> requestList = requestService.recieveRequest(usrId);
        return new ResponseResult(200,"返回成功",requestList);
    }

    @PostMapping("/request/process")
    public ResponseResult RequestProcess(@RequestBody Request request, @RequestParam("type") Integer type) {
        if(type == 1) {
            userService.InsertFamilyRelation(request);
            requestService.deleteRequest(request);
            return new ResponseResult(200,"增加成功");
        }
        else if(type == 0) {
            requestService.deleteRequest(request);
            return new ResponseResult(200,"删除成功");
        }
        return new ResponseResult(200,"111");
    }
}
