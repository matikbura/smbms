package cn.smbms.service.provider;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.QueryProvider;
import cn.smbms.tools.PageBean;

import java.util.List;

public interface ProviderService {


	/**
	 * 增加供应商
	 * @param provider
	 * @return
	 */
	public boolean add(Provider provider);


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 * @param proName
	 * @return
	 */
	public List<Provider> getProviderList(QueryProvider queryProvider);
	
	/**
	 * 通过proId删除Provider
	 * @param delId
	 * @return
	 */
	public int deleteProviderById(String delId);

	public PageBean<Provider> getProviderPage(String proName,String proCode,String pageSize,String curPage);
	
	/**
	 * 通过proId获取Provider
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id);
	
	/**
	 * 修改供应商信息
	 * @param
	 * @return
	 */
	public boolean modify(Provider provider);

	int getProviderCount(QueryProvider queryProvider);

}
