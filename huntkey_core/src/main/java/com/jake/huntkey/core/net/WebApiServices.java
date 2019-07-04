package com.jake.huntkey.core.net;

import com.jake.huntkey.core.netbean.Get7DayFpyRateResponse;
import com.jake.huntkey.core.netbean.Get7DayJdRateResponse;
import com.jake.huntkey.core.netbean.Get7DayTcrRateResponse;
import com.jake.huntkey.core.netbean.GetJdTop5Response;
import com.jake.huntkey.core.netbean.GetNbrInfoResponse;
import com.jake.huntkey.core.netbean.GetPageParameterResponse;
import com.jake.huntkey.core.netbean.GetQueryHeadResponse;
import com.jake.huntkey.core.netbean.GetQueryRouteResponse;
import com.jake.huntkey.core.netbean.GetQueryWarnResponse;
import com.jake.huntkey.core.netbean.GetWeekEmpRateResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface WebApiServices {

    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @return 获取页面参数
     */
    @GET("api/Report/GetPageParameter")
    Observable<GetPageParameterResponse> GetPageParameter(@Query("sid") String sid, @Query("lineId") String lineId);


    /**
     * @param sid 服务器ID
     * @return 获取预警参数
     */
    @GET("api/Report/GetQueryWarn")
    Observable<GetQueryWarnResponse> GetQueryWarn(@Query("sid") String sid);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取工站数据
     */
    @GET("api/Report/GetQueryHead")
    Observable<GetQueryHeadResponse> GetQueryHead(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid  服务器ID
     * @param part 产品料号
     * @return 获取途程相关工站信息
     */
    @GET("api/Report/GetQueryRoute")
    Observable<GetQueryRouteResponse> GetQueryRoute(@Query("sid") String sid, @Query("part") String part);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取线别工单信息
     */
    @GET("api/Report/GetNbrInfo")
    Observable<GetNbrInfoResponse> GetNbrInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 统计近七天的直通率，损失率，综合率
     */
    @GET("api/Report/Get7DayFpyRate")
    Observable<Get7DayFpyRateResponse> Get7DayFpyRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);

    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return• 统计近七天的直通率，损失率，综合率
     */
    @GET("api/Report/Get7DayJdRate")
    Observable<Get7DayJdRateResponse> Get7DayJdRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return• 统计近七天的直通率，损失率，综合率
     */
    @GET("api/Report/GetJdTop5")
    Observable<GetJdTop5Response> GetJdTop5(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId, @Query("flag") String flag);


    /**
     * @param sid
     * @param lineId
     * @param acctId
     * @return 统计近七天的达成率，A班，B班完成数量
     */
    @GET("api/Report/Get7DayTcrRate")
    Observable<Get7DayTcrRateResponse> Get7DayTcrRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);

    /**
     * @param deptCode 部门编码
     * @return 获取两周出勤率
     */
    @GET("api/Report/GetWeekEmpRate")
    Observable<GetWeekEmpRateResponse> GetWeekEmpRate(@Query("deptCode") String deptCode);


}
