package com.royal.util;

import com.royal.entity.User;
import com.royal.service.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserUtils {

    private static Set<String> set = new HashSet<String>();

    public static void initUserSet(){
        set = new HashSet<String>();
        IUserService userService = (IUserService) SpringContextUtils.getBean(
                "uServiceImpl");
        List<User> userList = userService.getAllFREEZE();
        for (User user : userList) {
            set.add(user.getLoginName());
        }
    }

    public static void addUser(String loginName){
        set.add(loginName);
    }

    public static void delUser(String loginName){
        set.remove(loginName);
    }

    public static boolean isUser(String loginName){
        return set.contains(loginName);
    }
}
