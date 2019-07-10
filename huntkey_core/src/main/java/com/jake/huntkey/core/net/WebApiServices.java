package com.jake.huntkey.core.net;

import com.jake.huntkey.core.netbean.Get20BdJianKongInfoResponse;
import com.jake.huntkey.core.netbean.Get20Be31DataResponse;
import com.jake.huntkey.core.netbean.Get20BeJianKongInfoResponse;
import com.jake.huntkey.core.netbean.Get7DayEmpRateResponse;
import com.jake.huntkey.core.netbean.Get7DayFpyRateResponse;
import com.jake.huntkey.core.netbean.Get7DayJdRateResponse;
import com.jake.huntkey.core.netbean.Get7DayTcrRateResponse;
import com.jake.huntkey.core.netbean.GetAttendanceRateResponse;
import com.jake.huntkey.core.netbean.GetDtInfoResponse;
import com.jake.huntkey.core.netbean.GetEmpRateResponse;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.GetFtyInfoResponse;
import com.jake.huntkey.core.netbean.GetJdRateResponse;
import com.jake.huntkey.core.netbean.GetJdTop5Response;
import com.jake.huntkey.core.netbean.GetJiePaiResponse;
import com.jake.huntkey.core.netbean.GetLineEmpRateResponse;
import com.jake.huntkey.core.netbean.GetNbrInfoResponse;
import com.jake.huntkey.core.netbean.GetOQtyResponse;
import com.jake.huntkey.core.netbean.GetPageParameterResponse;
import com.jake.huntkey.core.netbean.GetPeriodInfoResponse;
import com.jake.huntkey.core.netbean.GetQueryHeadResponse;
import com.jake.huntkey.core.netbean.GetQueryRouteResponse;
import com.jake.huntkey.core.netbean.GetQueryWarnResponse;
import com.jake.huntkey.core.netbean.GetRateResponse;
import com.jake.huntkey.core.netbean.GetSampleResponse;
import com.jake.huntkey.core.netbean.GetTcrRateResponse;
import com.jake.huntkey.core.netbean.GetWeekEmpRateResponse;
import com.jake.huntkey.core.netbean.GetWipDataResponse;
import com.jake.huntkey.core.netbean.GetWipHeadResponse;
import com.jake.huntkey.core.netbean.GetWipResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;

import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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
     * @param sid    服务器ID  5
     * @param lineId 线别ID  431
     * @param acctId 工厂ID   1
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
     * @return 统计近七天的直通率
     */
    @GET("api/Report/GetFpyRate")
    Observable<GetFpyRateResponse> GetFpyRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);

    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return• 统计近七天稼动率
     */
    @GET("api/Report/Get7DayJdRate")
    Observable<Get7DayJdRateResponse> Get7DayJdRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return• 统计近七天稼动Top5
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


    /**
     * @param deptCode 部门编码
     * @return 获取各线体出勤率
     */
    @GET("api/Report/GetLineEmpRate")
    Observable<GetLineEmpRateResponse> GetLineEmpRate(@Query("deptCode") String deptCode);


    /**
     * @param deptCode 部门编码
     * @return 获取七天出勤率
     */
    @GET("api/Report/Get7DayEmpRate")
    Observable<Get7DayEmpRateResponse> Get7DayEmpRate(@Query("deptCode") String deptCode);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取各个时段产能
     */
    @GET("api/Report/GetOQty")
    Observable<GetOQtyResponse> GetOQty(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 20BE页面获取监控信息
     */
    @GET("api/Report/Get20BeJianKongInfo")
    Observable<Get20BeJianKongInfoResponse> Get20BeJianKongInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid      服务器ID
     * @param lineId   线别ID
     * @param acctId   工厂ID
     * @param beginStr 起始时间
     * @param endStr   结束时间
     * @return 20BD页面获取监控信息
     */
    @GET("api/Report/Get20BdJianKongInfo")
    Observable<Get20BdJianKongInfoResponse> Get20BdJianKongInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId, @Query("beginStr") String beginStr, @Query("endStr") String endStr);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 生产看板基础信息和上一工单的信息
     */
    @GET("api/Report/GetRate")
    Observable<GetRateResponse> GetRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 抽验信息
     */
    @GET("api/Report/GetSample")
    Observable<GetSampleResponse> GetSample(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取wip表格列
     */
    @GET("api/Report/GetWipHead")
    Observable<GetWipHeadResponse> GetWipHead(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取wip数据
     */
    @GET("api/Report/GetWip")
    Observable<GetWipResponse> GetWip(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 工站监控查询
     */
    @GET("api/Report/GetJiePai")
    Observable<GetJiePaiResponse> GetJiePai(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId, @Query("beginStr") String beginStr, @Query("endStr") String endStr);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取各工站最小直通率top5
     */
    @GET("api/Report/GetFtyInfo")
    Observable<GetFtyInfoResponse> GetFtyInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 获取各工站最大损失率top5
     */
    @GET("api/Report/GetDtInfo")
    Observable<GetDtInfoResponse> GetDtInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param deptCode 部门ID
     * @return 获取当天出勤率
     */
    @GET("api/Report/GetAttendanceRate")
    Observable<GetAttendanceRateResponse> GetAttendanceRate(@Query("deptCode") String deptCode);


    /**
     * @param sid    服务器ID
     * @param lineId 线别ID
     * @param acctId 工厂ID
     * @return 统计32天的直通率，损失率，综合率
     */
    @GET("api/Report/GetPeriodInfo")
    Observable<GetPeriodInfoResponse> GetPeriodInfo(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid       服务器ID
     * @param deptCodes 部门ID
     * @param acctId    工厂ID
     * @return 统计32天的直通率，损失率，综合率
     */
    @GET("api/Report/Get20Be31Data")
    Observable<Get20Be31DataResponse> Get20Be31Data(@Query("sid") String sid, @Query("deptCodes") String deptCodes, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线体ID
     * @param acctId 工厂ID
     * @return 获取达成率图表数据
     */
    @GET("api/Report/GetTcrRate")
    Observable<GetTcrRateResponse> GetTcrRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param sid    服务器ID
     * @param lineId 线体ID
     * @param acctId 工厂ID
     * @return 获取稼动率图表数据
     */
    @GET("api/Report/GetJdRate")
    Observable<GetJdRateResponse> GetJdRate(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);


    /**
     * @param deptCode  部门ID
     * @param deptCodes 多个部门ID
     * @return 获取出勤率图表数据
     */
    @GET("api/Report/GetEmpRate")
    Observable<GetEmpRateResponse> GetEmpRate(@Query("deptCode") String deptCode, @Query("deptCodes") String deptCodes);


    /**
     * @return 获取Wip表格数据
     */
    @GET("api/Report/GetWipData")
    Observable<GetWipDataResponse> GetWipData(@Query("sid") String sid, @Query("lineId") String lineId, @Query("acctId") String acctId);

}
