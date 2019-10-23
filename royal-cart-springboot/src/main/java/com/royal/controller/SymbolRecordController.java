package com.royal.controller;

import com.royal.entity.*;
import com.royal.entity.json.SymbolRecordJson;
import com.royal.service.*;
import com.royal.util.HttpUtils;
import com.royal.util.JSONUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royal.entity.json.Result;

import java.util.ArrayList;
import java.util.List;

/**
* 描述：产品价格记录控制层
* @author Royal
* @date 2018年12月25日 21:45:16
*/
@Controller
@RequestMapping("/symbolRecord")
public class SymbolRecordController extends BaseController {

    @Autowired
    private ISymbolRecordService symbolRecordService;
    @Autowired
    private ISymbolRecordM1Service symbolRecordM1Service;
    @Autowired
    private ISymbolRecordM5Service symbolRecordM5Service;
    @Autowired
    private ISymbolRecordM15Service symbolRecordM15Service;
    @Autowired
    private ISymbolRecordM30Service symbolRecordM30Service;
    @Autowired
    private ISymbolRecordM60Service symbolRecordM60Service;
    @Autowired
    private ISymbolRecordH4Service symbolRecordH4Service;
    @Autowired
    private ISymbolRecordD1Service symbolRecordD1Service;
    @Autowired
    private ISymbolRecordD7Service symbolRecordD7Service;

	/**
	 * 获取K线图
	 * @return
	 */
	@RequestMapping(value = "/getSymbolRecord")
	@ResponseBody
    public Result getSymbolRecord(String symbolCode,int period){
		String json = HttpUtils.doGet("http://api.xfortunes.com/price/records?server=LIVE&symbol="+symbolCode+"_&period="+period);
		JSONObject jsonObject = JSONUtils.toJSONObject(json);
		Result result = new Result();
		result.setMsgCode(jsonObject.getString("code"));
		result.setMsg(jsonObject.getString("message"));
		result.setData(jsonObject.getJSONArray("dataObject"));
		return result;
	}



    /**
    * 描述:初始化K线历史
    */
    @RequestMapping(value = "/init")
	@ResponseBody
    public Result init(String symbolCode,int period) throws Exception {
		try {
			int index = 0;
			String json = HttpUtils.doGet("http://api.xfortunes.com/price/records?server=LIVE&symbol="+symbolCode+"_&period="+"period");
			JSONObject jsonObject = JSONUtils.toJSONObject(json);
			JSONObject data = jsonObject.getJSONObject("dataObject");
			List<SymbolRecordJson> list = JSONUtils.toList(data, SymbolRecordJson.class);
			if(period == 1){
				List<SymbolRecordM1> symbolRecordM1List = new ArrayList<SymbolRecordM1>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordM1 symbolRecordM = new SymbolRecordM1();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordM1List.add(symbolRecordM);
				}
				symbolRecordM1Service.deleteBySymbolCode(new SymbolRecordM1(),symbolCode);
				index = symbolRecordM1Service.addList(symbolRecordM1List);
			}else if(period == 5){
				List<SymbolRecordM5> symbolRecordM5List = new ArrayList<SymbolRecordM5>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordM5 symbolRecordM = new SymbolRecordM5();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordM5List.add(symbolRecordM);
				}
				symbolRecordM5Service.deleteBySymbolCode(new SymbolRecordM5(),symbolCode);
				index = symbolRecordM5Service.addList(symbolRecordM5List);
			}else if(period == 15){
				List<SymbolRecordM15> symbolRecordM15List = new ArrayList<SymbolRecordM15>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordM15 symbolRecordM = new SymbolRecordM15();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordM15List.add(symbolRecordM);
				}
				symbolRecordM15Service.deleteBySymbolCode(new SymbolRecordM15(),symbolCode);
				index = symbolRecordM15Service.addList(symbolRecordM15List);
			}else if(period == 30){
				List<SymbolRecordM30> symbolRecordM30List = new ArrayList<SymbolRecordM30>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordM30 symbolRecordM = new SymbolRecordM30();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordM30List.add(symbolRecordM);
				}
				symbolRecordM30Service.deleteBySymbolCode(new SymbolRecordM30(),symbolCode);
				index = symbolRecordM30Service.addList(symbolRecordM30List);
			}else if(period == 60){
				List<SymbolRecordM60> symbolRecordM60List = new ArrayList<SymbolRecordM60>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordM60 symbolRecordM = new SymbolRecordM60();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordM60List.add(symbolRecordM);
				}
				symbolRecordM60Service.deleteBySymbolCode(new SymbolRecordM60(),symbolCode);
				index = symbolRecordM60Service.addList(symbolRecordM60List);
			}else if(period == 240){
				List<SymbolRecordH4> symbolRecordH4List = new ArrayList<SymbolRecordH4>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordH4 symbolRecordM = new SymbolRecordH4();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordH4List.add(symbolRecordM);
				}
				symbolRecordH4Service.deleteBySymbolCode(new SymbolRecordH4(),symbolCode);
				index = symbolRecordH4Service.addList(symbolRecordH4List);
			}else if(period == 1440){
				List<SymbolRecordD1> symbolRecordD1List = new ArrayList<SymbolRecordD1>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordD1 symbolRecordM = new SymbolRecordD1();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordD1List.add(symbolRecordM);
				}
				symbolRecordD1Service.deleteBySymbolCode(new SymbolRecordD1(),symbolCode);
				index = symbolRecordD1Service.addList(symbolRecordD1List);
			}else if(period == 10080){
				List<SymbolRecordD7> symbolRecordD7List = new ArrayList<SymbolRecordD7>();
				for (SymbolRecordJson symbolRecordJson : list) {
					SymbolRecordD7 symbolRecordM = new SymbolRecordD7();
					symbolRecordM.setSymbolCode(symbolCode);
					symbolRecordM.setMarketTime(symbolRecordJson.getTime().toString());
					symbolRecordM.setSymbolMax(symbolRecordJson.getHigh());
					symbolRecordM.setSymbolMin(symbolRecordJson.getLow());
					symbolRecordM.setSymbolOpen(symbolRecordJson.getOpen());
					symbolRecordM.setSymbolTurnover(symbolRecordJson.getVolume());
					symbolRecordM.setSymbolClose(symbolRecordJson.getClose());
					symbolRecordD7List.add(symbolRecordM);
				}
				symbolRecordD7Service.deleteBySymbolCode(new SymbolRecordD7(),symbolCode);
				index = symbolRecordD7Service.addList(symbolRecordD7List);
			}

			return new Result(index);
		} catch (Exception e) {
			logger.error("初始化K线历史出异常了", e);
			return new Result(e);
		}
    }



}