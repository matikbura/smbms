package cn.smbms.pojo;

public class QueryProvider {
    private String queryProName;
    private String queryProCode;
    private Integer pageSize;//页面大小
    private Integer start;//起始位置
    private Integer currentPageNo;//当前页面

    public QueryProvider() {
    }

    public QueryProvider(String queryProName, String queryProCode, Integer pageSize, Integer start, Integer currentPageNo) {
        this.queryProName = queryProName;
        this.queryProCode = queryProCode;
        this.pageSize = pageSize;
        this.start = start;
        this.currentPageNo = currentPageNo;
    }

    public String getQueryProName() {
        return queryProName;
    }

    public void setQueryProName(String queryProName) {
        this.queryProName = queryProName;
    }

    public String getQueryProCode() {
        return queryProCode;
    }

    public void setQueryProCode(String queryProCode) {
        this.queryProCode = queryProCode;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    @Override
    public String toString() {
        return "QueryProvider{" +
                "queryProName='" + queryProName + '\'' +
                ", queryProCode='" + queryProCode + '\'' +
                ", pageSize=" + pageSize +
                ", start=" + start +
                ", currentPageNo=" + currentPageNo +
                '}';
    }
}
