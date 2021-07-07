package cn.smbms.service.provider;

import cn.smbms.mapper.BillMapper;
import cn.smbms.mapper.ProviderMapper;
import cn.smbms.mapper.ProviderMapperHsz;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.ProviderHSZ;
import cn.smbms.pojo.QueryProvider;
import cn.smbms.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	ProviderMapper providerMapper;
	@Autowired
	BillMapper billMapper;
	@Autowired
	ProviderMapperHsz providerMapperHsz;
	@Override
	public boolean add(Provider provider) {
		int flag=providerMapper.add(provider);
		if (flag!=0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Provider> getProviderList(QueryProvider queryProvider) {
		return providerMapper.getProviderList(queryProvider);
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
	 * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 *
	 *
	 * 以上逻辑后边要改
	 */
	//供应商删除数据进回收站思路：
	// 1.先根据 id查询信息  2.把信息封装到回收站实体类  3.调用回收站的添加方法  4.最后调用供应商的删除方法
	@Override
	public int deleteProviderById(String delId) {
		//根据 id查询订单表是否有信息，如果有信息就不删除，如果没信息就执行删除
		int billCount = billMapper.getBillCountByProviderId(Integer.valueOf(delId));
		if(billCount == 0){
			//根据获取 id信息
			Provider provider = providerMapper.getProviderById(Integer.valueOf(delId));
			System.out.println(provider);
			System.out.println("删除成功");
			ProviderHSZ providerHSZ = new ProviderHSZ();
			System.out.println(providerHSZ);
			providerHSZ.setProName(provider.getProName());
			providerHSZ.setProCode(provider.getProCode());
			providerHSZ.setProDesc(provider.getProDesc());
			providerHSZ.setProContact(provider.getProContact());
			providerHSZ.setProPhone(provider.getProPhone());
			providerHSZ.setProAddress(provider.getProAddress());
			providerHSZ.setProFax(provider.getProFax());
			providerHSZ.setCreatedBy(provider.getCreatedBy());
			providerHSZ.setCreationDate(provider.getCreationDate());
			providerHSZ.setModifyDate(provider.getModifyDate());
			providerHSZ.setModifyBy(provider.getModifyBy());
			providerHSZ.setDeletetime(new Date());
			//调用回收站的增加
			providerMapperHsz.addProviderHSZ(providerHSZ);
			//调用供应商的删除
			providerMapper.deleteProviderById(Integer.valueOf(delId));
		}
		return billCount;
	}

	@Override
	public PageBean<Provider> getProviderPage(String proName, String proCode, String pageSize, String curPage) {
		return null;
	}

	@Override
	public Provider getProviderById(String id) {
		return providerMapper.getProviderById(Integer.valueOf(id));
	}

	@Override
	public boolean modify(Provider provider) {
		return providerMapper.modify(provider) > 0;
	}

	@Override
	public int getProviderCount(QueryProvider queryProvider) {
		System.out.println(queryProvider);
		String queryProName = queryProvider.getQueryProName();
		String queryProCode = queryProvider.getQueryProCode();
		if (queryProName!=null && !"".equals(queryProName)){
			queryProvider.setQueryProName("%"+queryProName+"%");
		}
		if (queryProCode!=null && !"".equals(queryProCode)){
			queryProvider.setQueryProCode("%"+queryProCode+"%");
		}
		return providerMapper.getProviderCount(queryProvider);
	}
}
