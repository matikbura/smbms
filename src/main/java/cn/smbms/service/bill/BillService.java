package cn.smbms.service.bill;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.FuzzyQuery;
import cn.smbms.pojo.Provider;

import java.util.List;

public interface BillService {
	/**
	 * 增加订单
	 * @param bill
	 * @return
	 */
	public boolean add(Bill bill);
	boolean addBillByBillList(List<Bill> billList);
	//查询所有订单
	List<Bill> getAllBill(FuzzyQuery fuzzyQuery);
	//根据id查询订单
	Bill getBillById(Integer billid);
	//添加订单
	int addBill(Bill bill);
	//查询供应商返回json字符串
	List<Provider> getProviderIdAndName();
	//根据id删除订单
	int deleteBillById(Integer pid);
	//根据订单id修改订单信息
	int updateBill(Bill bill);
	//模糊查询订单
	List<Bill> getBillList(FuzzyQuery fuzzyQuery);

    List<Bill> getAllBillForExcel();

    List<Bill> queryAllName(String queryProductName);
}
