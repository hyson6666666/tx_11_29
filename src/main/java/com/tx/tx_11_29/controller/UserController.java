package com.tx.tx_11_29.controller;

import com.tx.tx_11_29.entity.TbUser;
import com.tx.tx_11_29.service.ITbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ITbUserService userService;

    private Map<Long, Long> map = new ConcurrentHashMap<>();

    @PostMapping("/remark")
    public String remarkUser(@RequestBody TbUser user) {
        if (map.putIfAbsent(user.getUserId(), user.getUserId()) != null) {
            System.out.println("已经在处理");
            return "已经在处理";
        }

        try {
            userService.remark(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            map.remove(user.getUserId());
        }
        return "";
    }
}
