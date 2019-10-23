package com.royal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.royal.service.IPaySwitchService;

/**
* 描述：支付通道开关控制层
* @author Royal
* @date 2019年07月08日 10:46:27
*/
@Controller
@RequestMapping("/paySwitch")
public class PaySwitchController extends BaseController {

    @Autowired
    private IPaySwitchService paySwitchService;
    


}