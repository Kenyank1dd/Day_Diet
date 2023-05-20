package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Mapper.RecordMapper;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.RecordService;
import com.example.diet.Service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    @InvokeLog
    public Integer RecordCal(String userId){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        System.out.println(time);
        List<Integer> Cal = recordMapper.findCalByIdDate(Integer.parseInt(userId),time);
        if( Cal != null) {
            return Cal.get(0);
        }
        else {
            userMapper.InsertCal(Long.parseLong(userId),0,time);
            return recordMapper.RecordCal(Integer.parseInt(userId),time);
        }
    }

    @Override
    @InvokeLog
    public Integer UpdateCal(String userId,long cal_num){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        System.out.println(time);
        recordMapper.UpdateCal(Integer.parseInt(userId),cal_num,time);
        return recordMapper.RecordCal(Integer.parseInt(userId),time);
    }

    @Override
    @InvokeLog
    public void updateAllergens(String userId,String allergen){
        String[] allergens = allergen.split("、"); // 通过、分割字符串
        recordMapper.deleteAllergen(Integer.parseInt(userId));   //先把原来的删除
        for(int i=0;i<allergens.length;i++){   //插入每一个过敏源
            int all_id = 0;
            all_id = recordMapper.findIdByAllergens(allergens[i]);
            if(all_id != 0){
                recordMapper.InsertAllergen(Integer.parseInt(userId),all_id);
            }
        }
    }


    @Override
    @InvokeLog
    public void updateDisease(String userId,String disease){
        String[] diseases = disease.split("、"); // 通过、分割字符串
        recordMapper.deleteDisease(Integer.parseInt(userId));   //先把原来的删除
        for(int i=0;i<diseases.length;i++){   //插入每一个疾病
            int dis_id = 0;
            dis_id = recordMapper.findIdByDiseases(diseases[i]);
            if(dis_id != 0){
                recordMapper.InsertDisease(Integer.parseInt(userId),dis_id);
            }
        }
    }

    @Override
    @InvokeLog
    public Long getCal(String userId,String date){
        return recordMapper.getCal(Integer.parseInt(userId),date);
    }


    @Override
    @InvokeLog
    public Long getStep(String userId,String date){
        return recordMapper.getStep(Integer.parseInt(userId),date);
    }

}
