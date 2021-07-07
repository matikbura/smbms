package cn.smbms.service.bill;

import cn.smbms.mapper.BillMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.FuzzyQuery;
import cn.smbms.pojo.Provider;
import cn.smbms.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	private BillMapper billMapper;
	@Override
	public boolean add(Bill bill) {
		// TODO Auto-generated method stub
		return billMapper.add(bill) > 0;
	}

	@Override
	public boolean addBillByBillList(List<Bill> billList) {
		return billMapper.addBillByBillList(billList)>0;
	}




	//查询所有订单
	@Override
	public List<Bill> getAllBill(FuzzyQuery fuzzyQuery) {
		//设置当前页默认值
		if (fuzzyQuery.getCurrentPageNo()==null){
			fuzzyQuery.setCurrentPageNo(1);
		}
		//调用方法获取总记录数
		int countBill=billMapper.getCountBill(fuzzyQuery);
		//设置总记录数
		fuzzyQuery.setTotalCount(countBill);
		//设置总页数 fuzzyQuery.getTotalPageCount() == 0||
		if (fuzzyQuery.getTotalPageCount()==null) {
			fuzzyQuery.setTotalPageCount(1);
		}
		if (fuzzyQuery.getTotalPageCount() >= 0) {
			fuzzyQuery.setTotalPageCount(fuzzyQuery.getTotalCount() % Constants.pageSize == 0 ? fuzzyQuery.getTotalCount() / Constants.pageSize : (fuzzyQuery.getTotalCount() / Constants.pageSize) + 1);
			if (fuzzyQuery.getCurrentPageNo()>=0&&fuzzyQuery.getCurrentPageNo()<=fuzzyQuery.getTotalPageCount())
				fuzzyQuery.setCurrentPageNo((fuzzyQuery.getCurrentPageNo()-1)*Constants.pageSize);

		}
		//设置每一页的大小
		fuzzyQuery.setPageSize(Constants.pageSize);
		List<Bill> billList = billMapper.getAllBill(fuzzyQuery);
		return billList;
	}
	//根据id查看订单
	@Override
	public Bill getBillById(Integer billid) {
		Bill billById = billMapper.getBillById(billid);
		//调用获取所有供应商的方法
		List<Provider> providerIdAndName = billMapper.getProviderIdAndName();
		for (Provider provider : providerIdAndName) {
			if (billById.getProviderId()==provider.getId()){
				billById.setProviderName(provider.getProName());
				break;
			}
		}
		return billById;
	}
	//添加订单
	@Override
	public int addBill(Bill bill) {
		//按照系统时间设置订单创建时间
		bill.setCreationDate(new Date());
		//生成随机的订单编码
		bill.setBillCode(UUID.randomUUID().toString().replaceAll("-",""));
		return billMapper.add(bill);
	}
	//查询供应商返回json字符串
	@Override
	public List<Provider> getProviderIdAndName() {
		return billMapper.getProviderIdAndName();
	}
	//根据id删除订单
	@Override
	public int deleteBillById(Integer pid) {
		return billMapper.deleteBillById(pid);
	}

	//根据订单id修改订单信息
	@Override
	public int updateBill(Bill bill) {
		//根据系统时间设置修改时间
		bill.setModifyDate(new Date());
		return billMapper.updateBill(bill);
	}
	//模糊查询订单
	@Override
	public List<Bill> getBillList(FuzzyQuery fuzzyQuery) {
		//设置当前页默认值
		if (fuzzyQuery.getCurrentPageNo()==null){
			fuzzyQuery.setCurrentPageNo(1);
		}
		else if (fuzzyQuery.getCurrentPageNo()!=null&&fuzzyQuery.getCurrentPageNo()<0){
			fuzzyQuery.setCurrentPageNo(1);
		}
		//调用方法获取总记录数
		int countBill=billMapper.getCountBill(fuzzyQuery);
		//设置总记录数
		fuzzyQuery.setTotalCount(countBill);
		System.out.println(fuzzyQuery);
		//设置总页数
		if (fuzzyQuery.getTotalPageCount()==null) {
			fuzzyQuery.setTotalPageCount(1);
		}
		//当总页数大于等于0的时候
		if (fuzzyQuery.getTotalPageCount() >= 0) {
			//计算总页数
			fuzzyQuery.setTotalPageCount(fuzzyQuery.getTotalCount() % Constants.pageSize == 0 ? fuzzyQuery.getTotalCount() / Constants.pageSize : (fuzzyQuery.getTotalCount() / Constants.pageSize) + 1);
			//当计算的结果等于0的时候，设置总页数默认值
			if (fuzzyQuery.getTotalPageCount()==0){
				fuzzyQuery.setTotalPageCount(1);
			}
			//当未计算的当前页大于总页数时
			if(fuzzyQuery.getCurrentPageNo()>fuzzyQuery.getTotalPageCount()){
				//设置当前页为总页数（有一种情况是设置完的当前页为0，那么会进去下面一个if语句中，从而导致SQL语句中出现负数）
				fuzzyQuery.setCurrentPageNo((fuzzyQuery.getTotalPageCount()-1)*Constants.pageSize);
				//所以避免上面的情况发生，需要做一个边界值的设置
				if (fuzzyQuery.getCurrentPageNo()==0){
					fuzzyQuery.setCurrentPageNo(1);
				}
			}
			if (fuzzyQuery.getCurrentPageNo()>=0&&fuzzyQuery.getCurrentPageNo()<=fuzzyQuery.getTotalPageCount()) {
				fuzzyQuery.setCurrentPageNo((fuzzyQuery.getCurrentPageNo() - 1) * Constants.pageSize);
			}

		}
		//设置每一页的大小
		fuzzyQuery.setPageSize(Constants.pageSize);
		//调用方法
		List<Bill> billList = billMapper.getBillList(fuzzyQuery);
		return billList;
	}

	@Override
	public List<Bill> getAllBillForExcel() {
		return billMapper.getAllBillForExcel();
	}

	@Override
	public List<Bill> queryAllName(String queryProductName) {
		return billMapper.queryAllName(queryProductName);
	}

}
