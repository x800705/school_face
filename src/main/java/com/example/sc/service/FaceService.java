package com.example.sc.service;

import com.example.sc.mapper.FaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sc.entity.face;

@Service
public class FaceService {
    @Autowired
    FaceMapper faceMapper;

    public face is_use(int id){
        return faceMapper.is_use(id);
    }

    public Boolean pay(face f) {return faceMapper.pay(f);}

}
