package com.example.demo.service.impl;

import com.example.demo.constants.Constants;
import com.example.demo.mapper.login.LoginMapper;
import com.example.demo.service.LoginService;
import com.example.demo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation = Propagation.REQUIRES_NEW)
    public HashMap<String, Object> login(HashMap<String, Object> paramMap) throws Exception {

        log.debug("LoginServiceImpl login START");

        HashMap<String, Object> map = new HashMap<String, Object>();

        log.debug("lastLoginIp      :"+paramMap.get("lastLoginIp"));
        log.debug("sessionId        :"+paramMap.get("sessionId"));

        log.debug("userCd           :"+paramMap.get("userCd"));
        log.debug("password         :"+paramMap.get("password"));

        try {
            boolean check = true;

            if(check == true) {
                boolean check2 = true;
                List<Map<String, Object>> listMap = loginMapper.login(paramMap);
                int listMapSize = listMap.size();

                if(null==listMap ||listMapSize==0){
                    map.put("msgCode",Constants.FAIL);
                    map.put("msgDesc","존재하지 않는 아이디입니다.");
                }else{
                    String password     = String.valueOf(listMap.get(0).get("password"));
                    String passwordEnc  = SecurityUtil.encryptSHA256(String.valueOf(paramMap.get("password")));

                    HashMap<String, Object> loginUpdateParamMap = new HashMap<String, Object>();
                    log.debug("password    : "+password);
                    log.debug("passwordEnc : "+passwordEnc);
                    if(password.equals(passwordEnc)) {
                        map.put("loginId", listMap.get(0).get("loginId"));
                        map.put("msgCode", Constants.OK);
                        map.put("msgDesc", "로그인되었습니다.");
                        map.put("success",true);
                    }else{

                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("LoginServiceImpl login END");
        return map;
    }

}
