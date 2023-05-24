package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Domain.Settings;
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
import java.util.Map;

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
        Map<String,Object> Cal = null;
        Cal = recordMapper.findCalByIdDate(Integer.parseInt(userId),time);
        if( Cal.size() != 0) {
            Map<String,Object> temp = (Map<String, Object>) Cal.get(Integer.parseInt(userId));
            return (Integer) temp.get("cal_num");
        }
        else {
            userMapper.InsertCal(Long.parseLong(userId),0,time);
            return 0;
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
    public Integer getCal(String userId,String date){
        return recordMapper.getCal(Integer.parseInt(userId),date);
    }


    @Override
    @InvokeLog
    public Integer getStep(String userId,String date){
        return recordMapper.getStep(Integer.parseInt(userId),date);
    }



    //修改  查找  插入推荐参数
    @Override
    @InvokeLog
    public void UpdateSettings(String userId,Float[] settings){
        recordMapper.UpdateSettings(Integer.parseInt(userId),settings[0],settings[1],settings[2],settings[3],settings[4],settings[5]);
    }
    @Override
    @InvokeLog
    public void InsertSettings(Long userId,Float[] settings){
        recordMapper.InsertSettings(userId,settings[0],settings[1],settings[2],settings[3],settings[4],settings[5]);
    }
    @Override
    @InvokeLog
    public Settings getSettings(String userId){
        return recordMapper.getSettings(Integer.parseInt(userId));
    }

}
