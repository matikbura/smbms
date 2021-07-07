package cn.smbms.mapper;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.FuzzyQuery;
import cn.smbms.pojo.Provider;

import java.util.List;

public interface BillMapper {
    int modify(Bill bill);
    int addBillByBillList(List<Bill> billList);
    //查询所有订单
    List<Bill> getAllBill(FuzzyQuery fuzzyQuery);
    //添加订单
    int add(Bill bill);
    //模糊查询订单
    List<Bill> getBillList( FuzzyQuery fuzzyQuery);
    //根据订单id删除订单
    int deleteBillById(Integer id);
    //根据订单id获取订单
    Bill getBillById(Integer id);
    //修改订单
    int updateBill(Bill bill);
    //根据供应商id查询订单数量
    int getBillCountByProviderId(Integer providerId);
    //查询供应商返回json字符串
    List<Provider> getProviderIdAndName();
    //查询订单数量
    int getCountBill(FuzzyQuery fuzzyQuery);

    List<Bill> getAllBillForExcel();

    List<Bill> queryAllName(String queryProductName);
}
