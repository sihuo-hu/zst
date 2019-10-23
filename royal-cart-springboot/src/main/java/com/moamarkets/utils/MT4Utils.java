//package com.moamarkets.utils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.coderallen.mt4.CManagerFactory;
//import com.coderallen.mt4.CManagerInterface;
//import com.coderallen.mt4.ChartInfo;
//import com.coderallen.mt4.MarginLevel;
//import com.coderallen.mt4.RateInfo;
//import com.coderallen.mt4.SWIGTYPE_p_int;
//import com.coderallen.mt4.SWIGTYPE_p_time_t;
//import com.coderallen.mt4.SymbolInfo;
//import com.coderallen.mt4.TradeRecord;
//import com.coderallen.mt4.TradeTransInfo;
//import com.coderallen.mt4.UserRecord;
//import com.coderallen.mt4.XMT4Manager;
//import com.coderallen.mt4.XMT4ManagerConstants;
//import com.moamarkets.common.Constants;
//
///**
// * 写成静态类后在大并发情况下出现MT4拒连情况
// * Created by Allen on 27/05/2017.
// * coder.allen@hotmail.com
// */
//
//public class MT4Utils {
//    public CManagerFactory cfactory;
//    public CManagerInterface cManagerInterface;
//
//    private String ip;
//    private int login;
//    private String password;
//
//    static {
////    	System.loadLibrary("mtmanapi64");
////    	System.loadLibrary("mt4_connector");
//    	System.load("C:\\SERVER\\ROOT\\dlls\\mtmanapi64.dll");
//        System.load("C:\\SERVER\\ROOT\\dlls\\mt4_connector.dll");
//    }
//
//
//    public MT4Utils(String ip,int login,String password) throws Exception{
//		this.ip = ip;
//		this.login = login;
//		this.password = password;
//
//		this.cfactory = new CManagerFactory();
//        this.cManagerInterface = this.cfactory.Create(cfactory.Version());
//        connect();//连接
//        login();//登陆
//
//	}
//
//
//    public boolean connect() throws Exception{
//        int connResult = cManagerInterface.Connect(ip);
//        if(connResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//        	throw new Exception(cManagerInterface.ErrorDescription(connResult));
//        }
//    }
//
//    public boolean disConnect() throws Exception{
//    	int connResult = cManagerInterface.Disconnect();
//        if(connResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//        	throw new Exception(cManagerInterface.ErrorDescription(connResult));
//        }
//    }
//
//    public boolean isConnected() throws Exception{
//        if (cManagerInterface.IsConnected() == 1){
//        	return true;
//        }
//        return false;
//    }
//
//    public void disConnected() throws Exception{
//        if (cManagerInterface.Disconnect() == 1){
//
//        }
//    }
//
//    public boolean login() throws Exception{
//        int loginResult = cManagerInterface.Login(login,password);
//        if(loginResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//            throw new Exception(cManagerInterface.ErrorDescription(loginResult));
//        }
//    }
//
//    /**
//     * 获取用户资料
//     * @param username
//     * @return
//     * @throws Exception
//     */
//    public UserRecord getUserRecordbyUsername(int username) throws Exception{
//        SWIGTYPE_p_int logins = XMT4Manager.new_intp();
//        XMT4Manager.intp_assign(logins,username);
//        SWIGTYPE_p_int total = XMT4Manager.new_intp();
//        XMT4Manager.intp_assign(total,1);
//        UserRecord result = cManagerInterface.UserRecordsRequest(logins,total);
//        XMT4Manager.delete_intp(logins);
//        XMT4Manager.delete_intp(total);
//        return result;
//    }
//
//    /**
//     * 获取用户扩展属性如余额可用保证金等
//     * @param username
//     * @return
//     * @throws Exception
//     */
//    public MarginLevel getMarginLevelbyUsername(int username) throws Exception{
//    	MarginLevel ml = new MarginLevel();
//    	cManagerInterface.MarginLevelRequest(username,ml);
//
//        return ml;
//    }
//
//
//
//    /**
//     * 修改密码
//     * @param username
//     * @param password
//     * @param type
//     * @return
//     * @throws Exception
//     */
//    public boolean changePassword(int username,String password,String type) throws Exception {
//        int changeResult = cManagerInterface.UserPasswordSet(username,password,type.equals("MASTER") ? 0 : 1,0);
//        if(changeResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//            throw new Exception(cManagerInterface.ErrorDescription(changeResult));
//        }
//    }
//
//    /**
//     * 检查密码
//     * @param username
//     * @param password
//     * @return
//     * @throws Exception
//     */
//    public boolean checkPassword(int username,String password) throws Exception{
//        int checkResult = cManagerInterface.UserPasswordCheck(username,password);
//        if (checkResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    /**
//     * 执行交易
//     * @param tradeTransInfo
//     * @return
//     * @throws Exception
//     */
//    public boolean doTrade(TradeTransInfo tradeTransInfo) throws Exception{
//        int tradeResult = cManagerInterface.TradeTransaction(tradeTransInfo);
//
//        if(tradeResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//            throw new Exception(cManagerInterface.ErrorDescription(tradeResult));
//        }
//    }
//
//    /**
//     * 获取订单记录
//     * @param ticket
//     * @return
//     * @throws Exception
//     */
//    public TradeRecord getOrder(int ticket) throws Exception{
//    	SWIGTYPE_p_int ticketN = XMT4Manager.new_intp();
//        XMT4Manager.intp_assign(ticketN,ticket);
//        SWIGTYPE_p_int total = XMT4Manager.new_intp();
//        XMT4Manager.intp_assign(total,1);
//
//        TradeRecord tradeResult = cManagerInterface.TradeRecordsRequest(ticketN,total);
//
//        if(tradeResult != null){
//            return tradeResult;
//        }else {
//            throw new Exception("Could not find that TradeRecord");
//        }
//    }
//
//    /**
//     * 创建MT4账户
//     * @param ur
//     * @return
//     * @throws Exception
//     */
//    public boolean createAccount(UserRecord ur) throws Exception {
//        int newResult = cManagerInterface.UserRecordNew(ur);
//        if (newResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//            throw new Exception(cManagerInterface.ErrorDescription(newResult));
//        }
//    }
//
//    /**
//     * 更新MT4账户资料
//     * @param ur
//     * @return
//     * @throws Exception
//     */
//    public boolean updateAccount(UserRecord ur) throws Exception {
//        int newResult = cManagerInterface.UserRecordUpdate(ur);
//        if (newResult == XMT4ManagerConstants.RET_OK){
//            return true;
//        }else {
//            throw new Exception(cManagerInterface.ErrorDescription(newResult));
//        }
//    }
//
//   /**
//    * 获得历史报价
//    * @param startTime
//    * @param endTime
//    * @param period
//    * @param symbol
//    * @return
//    */
//   public RateInfo getRateHistory(Date startTime,Date endTime,int period,String symbol) throws Exception {
//
//   		SWIGTYPE_p_int total = XMT4Manager.new_intp();
//       XMT4Manager.intp_assign(total,999);
//       //TODO 需要根据时间和间隔刻度计算有多少条-total
//       SWIGTYPE_p_time_t timesign = XMT4Manager.new_time_tp();
//
//       ChartInfo ci = new ChartInfo();
//       SWIGTYPE_p_time_t start = XMT4Manager.new_time_tp();//开始时间
//       XMT4Manager.time_tp_assign(start,startTime.getTime() / 1000);
//
//       SWIGTYPE_p_time_t end = XMT4Manager.new_time_tp();//结束时间
//       XMT4Manager.time_tp_assign(end,endTime.getTime() / 1000);
//
//       SWIGTYPE_p_time_t stamp = XMT4Manager.new_time_tp();
//       XMT4Manager.time_tp_assign(stamp,0);//时间戳
//
//       ci.setPeriod(period);//间隔单位
//       ci.setStart(start);
//       ci.setEnd(end);
//       ci.setSymbol(symbol);//品种
//       ci.setTimesign(stamp);
//       ci.setMode(XMT4ManagerConstants.CHART_RANGE_LAST);
//
//       RateInfo rateResult = cManagerInterface.ChartRequest(ci, timesign, total);
//
//       if(rateResult != null){
//           return rateResult;
//       }else {
//           throw new Exception("Could not find that RateResult");
//       }
//   }
//
//
//   public TradeRecord getOpeningOrders(String group) throws Exception {
//
//	   SWIGTYPE_p_int total = XMT4Manager.new_intp();
//	   XMT4Manager.intp_assign(total,9999);
//	   TradeRecord tradeResult = cManagerInterface.AdmTradesRequest(group, 1, total);
//
//	   if(tradeResult != null){
//           return tradeResult;
//       }else {
//           throw new Exception("Could not find that TradeResult");
//       }
//   }
//
//   /**
//    * 获得历史交易记录
//    * @param startTime
//    * @param endTime
//    * @param login
//    * @return
//    * @throws Exception
//    */
//   public TradeRecord getTradeHistory(Date startTime,Date endTime,int login) throws Exception {
//
//   		SWIGTYPE_p_int total = XMT4Manager.new_intp();
//   		XMT4Manager.intp_assign(total,999);
//
//   		SWIGTYPE_p_time_t start = XMT4Manager.new_time_tp();//开始时间
//   		XMT4Manager.time_tp_assign(start,startTime.getTime() / 1000);
//
//   		SWIGTYPE_p_time_t end = XMT4Manager.new_time_tp();//结束时间
//   		XMT4Manager.time_tp_assign(end,endTime.getTime() / 1000);
//
//   		SWIGTYPE_p_time_t stamp = XMT4Manager.new_time_tp();
//   		XMT4Manager.time_tp_assign(stamp,0);//时间戳
//
//   		TradeRecord tradeResult = cManagerInterface.TradesUserHistory(login, start, end, total);
//
//       if(tradeResult != null){
//           return tradeResult;
//       }else {
//           throw new Exception("Could not find that RateResult");
//       }
//   }
//
//
//   /**
//    * 根据品种名称获取品种信息
//    * @param symbol
//    * @return
//    * @throws Exception
//    */
//   public SymbolInfo getSymbolInfo(String symbol) throws Exception{
//	   SymbolInfo symbolInfo = new SymbolInfo();
//	   cManagerInterface.SymbolInfoGet(symbol, symbolInfo);
//
//	   if(symbolInfo != null){
//           return symbolInfo;
//       }else {
//           throw new Exception("Could not find that SymbolInfo");
//       }
//   }
//
//
//
//
//    public void memFree(Object obj){
//    	//TODO 需要传递要C++释放内存的指针
//    	cManagerInterface.MemFree(null);
//    }
//
//}
