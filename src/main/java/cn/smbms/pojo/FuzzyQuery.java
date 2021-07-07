package cn.smbms.pojo;
//模糊查询实体类
public class FuzzyQuery {
    private String queryProductName;//模糊查询商品名
    private Integer queryProviderId;//供应商id
    private Integer queryIsPayment;//是否付款
    private Integer totalCount;//总记录数
    private Integer totalPageCount;//总页数
    private Integer currentPageNo;//当前页
    private Integer pageSize;//每一页大小

    public String getQueryProductName() {
        return queryProductName;
    }

    public void setQueryProductName(String queryProductName) {
        this.queryProductName = queryProductName;
    }

    public Integer getQueryProviderId() {
        return queryProviderId;
    }

    public void setQueryProviderId(Integer queryProviderId) {
        this.queryProviderId = queryProviderId;
    }

    public Integer getQueryIsPayment() {
        return queryIsPayment;
    }

    public void setQueryIsPayment(Integer queryIsPayment) {
        this.queryIsPayment = queryIsPayment;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "FuzzyQuery{" +
                "queryProductName='" + queryProductName + '\'' +
                ", queryProviderId=" + queryProviderId +
                ", queryIsPayment=" + queryIsPayment +
                ", totalCount=" + totalCount +
                ", totalPageCount=" + totalPageCount +
                ", currentPageNo=" + currentPageNo +
                '}';
    }
}
