package cn.smbms.pojo;

import java.util.Date;

public class QueryProviderHSZ {
    private String queryProCode; //供应商编码
    private String queryProName; //供应商名称
    private Date queryMinDate;  //最小时间
    private Date queryMaxDate;  //最大时间
    private String queryMinDateStr;  //最小时间字符串
    private String queryMaxDateStr;  //最大时间字符串
    private Integer currentPageNo;   //当前页
    private Integer start;  //分页起始位置
    private Integer pageSize;   //页长


    public String getQueryProCode() {
        return queryProCode;
    }

    public void setQueryProCode(String queryProCode) {
        this.queryProCode = queryProCode;
    }

    public String getQueryProName() {
        return queryProName;
    }

    public void setQueryProName(String queryProName) {
        this.queryProName = queryProName;
    }

    public Date getQueryMinDate() {
        return queryMinDate;
    }

    public void setQueryMinDate(Date queryMinDate) {
        this.queryMinDate = queryMinDate;
    }

    public Date getQueryMaxDate() {
        return queryMaxDate;
    }

    public void setQueryMaxDate(Date queryMaxDate) {
        this.queryMaxDate = queryMaxDate;
    }

    public String getQueryMinDateStr() {
        return queryMinDateStr;
    }

    public void setQueryMinDateStr(String queryMinDateStr) {
        this.queryMinDateStr = queryMinDateStr;
    }

    public String getQueryMaxDateStr() {
        return queryMaxDateStr;
    }

    public void setQueryMaxDateStr(String queryMaxDateStr) {
        this.queryMaxDateStr = queryMaxDateStr;
    }

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QueryProviderHSZ{" +
                "queryProCode='" + queryProCode + '\'' +
                ", queryProName='" + queryProName + '\'' +
                ", queryMinDate=" + queryMinDate +
                ", queryMaxDate=" + queryMaxDate +
                ", queryMinDateStr='" + queryMinDateStr + '\'' +
                ", queryMaxDateStr='" + queryMaxDateStr + '\'' +
                ", currentPageNo=" + currentPageNo +
                ", start=" + start +
                ", pageSize=" + pageSize +
                '}';
    }
}
