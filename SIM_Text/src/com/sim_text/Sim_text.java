package com.sim_text;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
public class Sim_text extends  ListActivity {

	private TelephonyManager telMgr;
	private List<String> item=new ArrayList<String>();
	private List<String> value=new ArrayList<String>();
	 

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	/* 載入main.xml Layout */
	setContentView(R.layout.main);
	 telMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
	  
     if (telMgr.getSimState() == telMgr.SIM_STATE_READY) {  
         Log.i("MyList", "良好");  
     } else if (telMgr.getSimState() == telMgr.SIM_STATE_ABSENT) {  
         Log.i("MyList", "无SIM卡");  
     } else {  
         Log.i("MyList", "SIM卡被锁定或未知的状态");  
     }  

     Log.i("MyList", "电话状态[0 无活动/1 响铃/2 摘机]:" + getCallState());  
     Log.i("MyList", "电话方位:" + getCellLocation());  
     Log.i("MyList", "唯一的设备ID:" + getDeviceId());  
     Log.i("MyList", "设备的软件版本号:" + getDeviceSoftwareVersion());  
     Log.i("MyList", "手机号:" + getLine1Number());  
     Log.i("MyList", "附近的电话的信息:" + getNeighboringCellInfo());  
     Log.i("MyList", "获取ISO标准的国家码，即国际长途区号:" + getNetworkCountryIso());  
     Log.i("MyList", "MCC+MNC:" + getNetworkOperator());  
     Log.i("MyList", "(当前已注册的用户)的名字:" + getNetworkOperatorName());  
     Log.i("MyList", "当前使用的网络类型:" + getNetworkType());  
     Log.i("MyList", "手机类型:" + getPhoneType());  
     Log.i("MyList", "SIM卡的国家码:" + getSimCountryIso());  
     Log.i("MyList", "获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字:" + getSimOperator());  
     Log.i("MyList", "服务商名称:" + getSimOperatorName());  
     Log.i("MyList", "SIM卡的序列号:" + getSimSerialNumber());  
     Log.i("MyList", "SIM的状态信息:" + getSimState());  
     Log.i("MyList", "唯一的用户ID:" + getSubscriberId());  
     Log.i("MyList", "取得和语音邮件相关的标签，即为识别符:" + getVoiceMailAlphaTag());  
     Log.i("MyList", "获取语音邮件号码:" + getVoiceMailNumber());  
     Log.i("MyList", "ICC卡是否存在:" + hasIccCard());  
     Log.i("MyList", "是否漫游:" + isNetworkRoaming());  
     Log.i("MyList", "获取数据活动状态:" + getDataActivity());  
     Log.i("MyList", "获取数据连接状态:" + getDataState());  

     // telMgr.listen(PhoneStateListener listener, int events) ;  
     // 解释：  
     // IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)  
     // IMSI共有15位，其结构如下：  
     // MCC+MNC+MIN  
     // MCC：Mobile Country Code，移动国家码，共3位，中国为460;  
     // MNC:Mobile NetworkCode，移动网络码，共2位  
     // 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03  
     // 合起来就是（也是Android手机中APN配置文件中的代码）：  
     // 中国移动：46000 46002  
     // 中国联通：46001  
     // 中国电信：46003  
     // 举例，一个典型的IMSI号码为460030912121001  
     // IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称  
     // IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的  
     // 其组成为：  
     // 1. 前6位数(TAC)是”型号核准号码”，一般代表机型  
     // 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地  
     // 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号  
     // 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用 
	 
	}
	 /** 
     * 电话状态：<br/> 
     * CALL_STATE_IDLE 无任何状态时<br/> 
     * CALL_STATE_OFFHOOK 接起电话时<br/> 
     * CALL_STATE_RINGING 电话进来时 
     *  
     * @return 
     */  
    private int getCallState() {  
        return telMgr.getCallState();  
    }  
  
    /** 
     * 返回当前移动终端的位置 <br/> 
     *  
     * @return 
     */  
    private CellLocation getCellLocation() {  
        CellLocation location = telMgr.getCellLocation();  
  
        // 请求位置更新，如果更新将产生广播，接收对象为注册LISTEN_CELL_LOCATION的对象，需要的permission名称为ACCESS_COARSE_LOCATION。  
        // location.requestLocationUpdate();  
  
        return location;  
    }  
  
    /** 
     * 唯一的设备ID：<br/> 
     * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID<br/> 
     * 需要权限：android.permission.READ_PHONE_STATE 
     *  
     * @return null if device ID is not available. 
     */  
    private String getDeviceId() {  
        return telMgr.getDeviceId();  
    }  
  
    /** 
     * 返回移动终端的软件版本：<br/> 
     * 例如：GSM手机的IMEI/SV码。<br/> 
     *  
     * @return null if the software version is not available. 
     */  
    private String getDeviceSoftwareVersion() {  
        return telMgr.getDeviceSoftwareVersion();  
    }  
  
    /** 
     * 手机号：<br/> 
     * 对于GSM网络来说即MSISDN 
     *  
     * @return null if it is unavailable. 
     */  
    private String getLine1Number() {  
        return telMgr.getLine1Number();  
    }  
  
    /** 
     * 返回当前移动终端附近移动终端的信息:<br/> 
     * 类型：List<NeighboringCellInfo><br/> 
     * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES 
     *  
     * @return 
     */  
    private List<NeighboringCellInfo> getNeighboringCellInfo() {  
        // List<NeighboringCellInfo> infos = telMgr.getNeighboringCellInfo();  
        // for (NeighboringCellInfo info : infos) {  
        // // 获取邻居小区号  
        // int cid = info.getCid();  
        //  
        // // 获取邻居小区LAC，LAC:  
        // // 位置区域码。为了确定移动台的位置，每个GSM/PLMN的覆盖区都被划分成许多位置区，LAC则用于标识不同的位置区。  
        // info.getLac();  
        // info.getNetworkType();  
        // info.getPsc();  
        //  
        // // 获取邻居小区信号强度  
        // info.getRssi();  
        // }  
  
        return telMgr.getNeighboringCellInfo();  
    }  
  
    /** 
     * 获取ISO标准的国家码，即国际长途区号。<br/> 
     * 注意：仅当用户已在网络注册后有效。<br/> 
     * 在CDMA网络中结果也许不可靠。<br/> 
     *  
     * @return 
     */  
    private String getNetworkCountryIso() {  
        return telMgr.getNetworkCountryIso();  
    }  
  
    /** 
     * MCC+MNC(mobile country code + mobile network code)<br/> 
     * 注意：仅当用户已在网络注册时有效。<br/> 
     * 在CDMA网络中结果也许不可靠。<br/> 
     *  
     * @return 
     */  
    private String getNetworkOperator() {  
        return telMgr.getNetworkOperator();  
    }  
  
    /** 
     * 按照字母次序的current registered operator(当前已注册的用户)的名字<br/> 
     * 注意：仅当用户已在网络注册时有效。<br/> 
     * 在CDMA网络中结果也许不可靠。 
     *  
     * @return 
     */  
    private String getNetworkOperatorName() {  
        return telMgr.getNetworkOperatorName();  
    }  
  
    /** 
     * 当前使用的网络类型：<br/> 
     * NETWORK_TYPE_UNKNOWN 网络类型未知 0<br/> 
     * NETWORK_TYPE_GPRS GPRS网络 1<br/> 
     * NETWORK_TYPE_EDGE EDGE网络 2<br/> 
     * NETWORK_TYPE_UMTS UMTS网络 3<br/> 
     * NETWORK_TYPE_HSDPA HSDPA网络 8<br/> 
     * NETWORK_TYPE_HSUPA HSUPA网络 9<br/> 
     * NETWORK_TYPE_HSPA HSPA网络 10<br/> 
     * NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4<br/> 
     * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5<br/> 
     * NETWORK_TYPE_EVDO_A EVDO网络, revision A. 6<br/> 
     * NETWORK_TYPE_1xRTT 1xRTT网络 7<br/> 
     * 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO<br/> 
     *  
     * @return 
     */  
    private int getNetworkType() {  
        return telMgr.getNetworkType();  
    }  
  
    /** 
     * 返回移动终端的类型：<br/> 
     * PHONE_TYPE_CDMA 手机制式为CDMA，电信<br/> 
     * PHONE_TYPE_GSM 手机制式为GSM，移动和联通<br/> 
     * PHONE_TYPE_NONE 手机制式未知<br/> 
     *  
     * @return 
     */  
    private int getPhoneType() {  
        return telMgr.getPhoneType();  
    }  
  
    /** 
     * 获取ISO国家码，相当于提供SIM卡的国家码。 
     *  
     * @return Returns the ISO country code equivalent for the SIM provider's 
     *         country code. 
     */  
    private String getSimCountryIso() {  
        return telMgr.getSimCountryIso();  
    }  
  
    /** 
     * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.<br/> 
     * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断). 
     *  
     * @return Returns the MCC+MNC (mobile country code + mobile network code) 
     *         of the provider of the SIM. 5 or 6 decimal digits. 
     */  
    private String getSimOperator() {  
        return telMgr.getSimOperator();  
    }  
  
    /** 
     * 服务商名称：<br/> 
     * 例如：中国移动、联通<br/> 
     * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断). 
     *  
     * @return 
     */  
    private String getSimOperatorName() {  
        return telMgr.getSimOperatorName();  
    }  
  
    /** 
     * SIM卡的序列号：<br/> 
     * 需要权限：READ_PHONE_STATE 
     *  
     * @return 
     */  
    private String getSimSerialNumber() {  
        return telMgr.getSimSerialNumber();  
    }  
  
    /** 
     * SIM的状态信息：<br/> 
     * SIM_STATE_UNKNOWN 未知状态 0<br/> 
     * SIM_STATE_ABSENT 没插卡 1<br/> 
     * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2<br/> 
     * SIM_STATE_PUK_REQUIRED 锁定状态，需要用户的PUK码解锁 3<br/> 
     * SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4<br/> 
     * SIM_STATE_READY 就绪状态 5 
     *  
     * @return 
     */  
    private int getSimState() {  
        return telMgr.getSimState();  
    }  
  
    /** 
     * 唯一的用户ID：<br/> 
     * 例如：IMSI(国际移动用户识别码) for a GSM phone.<br/> 
     * 需要权限：READ_PHONE_STATE 
     *  
     * @return 
     */  
    private String getSubscriberId() {  
        return telMgr.getSubscriberId();  
    }  
  
    /** 
     * 取得和语音邮件相关的标签，即为识别符<br/> 
     * 需要权限：READ_PHONE_STATE 
     *  
     * @return 
     */  
    private String getVoiceMailAlphaTag() {  
        return telMgr.getVoiceMailAlphaTag();  
    }  
  
    /** 
     * 获取语音邮件号码：<br/> 
     * 需要权限：READ_PHONE_STATE 
     *  
     * @return 
     */  
    private String getVoiceMailNumber() {  
        return telMgr.getVoiceMailNumber();  
    }  
  
    /** 
     * ICC卡是否存在 
     *  
     * @return 
     */  
    private boolean hasIccCard() {  
        return telMgr.hasIccCard();  
    }  
  
    /** 
     * 是否漫游在GSM用途下) 
     *  
     * @return 
     */  
    private boolean isNetworkRoaming() {  
        return telMgr.isNetworkRoaming();  
    }  
  
    /** 
     * 获取数据活动状态<br/> 
     * DATA_ACTIVITY_IN 数据连接状态：活动，正在接受数据<br/> 
     * DATA_ACTIVITY_OUT 数据连接状态：活动，正在发送数据<br/> 
     * DATA_ACTIVITY_INOUT 数据连接状态：活动，正在接受和发送数据<br/> 
     * DATA_ACTIVITY_NONE 数据连接状态：活动，但无数据发送和接受<br/> 
     *  
     * @return 
     */  
    private int getDataActivity() {  
        return telMgr.getDataActivity();  
    }  
  
    /** 
     * 获取数据连接状态<br/> 
     * DATA_CONNECTED 数据连接状态：已连接<br/> 
     * DATA_CONNECTING 数据连接状态：正在连接<br/> 
     * DATA_DISCONNECTED 数据连接状态：断开<br/> 
     * DATA_SUSPENDED 数据连接状态：暂停<br/> 
     *  
     * @return 
     */  
    private int getDataState() {  
        return telMgr.getDataState();  
    }  

}
