package com.example.diet.Service;

import com.example.diet.Domain.Request;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RequestService {

    void sendRequest(Request request);

    List<Request> recieveRequest(String usrId);

    void deleteRequest(Request request);
}
