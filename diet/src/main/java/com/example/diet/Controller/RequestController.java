package com.example.diet.Controller;

import com.example.diet.Domain.Request;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Domain.User;
import com.example.diet.Domain.UsrFamily;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RequestService;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @PostMapping("/request/send")
    public ResponseResult sendRequest(@CurrentUserId String usrId,@RequestParam("phone") String phone,@RequestParam("req_msg") String req_msg){
        Request request = new Request();
        String to_user_id = requestService.findIdbyPhone(phone);    //先通过phonenumber得到要关联账号的id
        request.setTo_usr_id(to_user_id);
        request.setFrom_usr_id(usrId);
        request.setReq_msg(req_msg);
        User user = userService.findUserbyId(request.getTo_usr_id());
        if(user == null) return new ResponseResult(201,"无该用户");
        System.out.println(request);
        requestService.sendRequest(request);
        return new ResponseResult(200,"成功发送请求");
    }

    @PostMapping("/request/receive")
    public ResponseResult recieveRequest(@CurrentUserId String usrId) {
        List<Request> requestList = requestService.recieveRequest(usrId);
        //因为前端需要每个id的username   所以用to_usr_id 来存一下username
        for(Request request : requestList) {
            request.setTo_usr_id(requestService.findNamebyId(request.getFrom_usr_id()));
        }
        return new ResponseResult(200,"返回成功",requestList);
    }

    @PostMapping("/request/process")
    public ResponseResult RequestProcess(@CurrentUserId String usrId , @org.springframework.web.bind.annotation.RequestBody Map map, @RequestParam("type") Integer type) {
        System.out.println(type);
        System.out.println(map);
        Request request = new Request();
        Integer req_id = (Integer) map.get("req_id");
        String from_usr_id = map.get("id").toString();
        System.out.println(1);
        String req_msg = map.get("relation").toString();
        System.out.println(222);
        request.setReq_id(req_id);
        request.setReq_msg(req_msg);
        request.setFrom_usr_id(from_usr_id);
        request.setTo_usr_id(usrId);
        System.out.println(333);
        if(type == 1) {
            userService.InsertFamilyRelation(request);
            requestService.deleteRequest(request);
            return new ResponseResult(200,"增加成功");
        }
        else if(type == 0) {
            System.out.println(444);
            requestService.deleteRequest(request);
            return new ResponseResult(200,"删除成功");
        }
        return new ResponseResult(201,"111");
    }
}
