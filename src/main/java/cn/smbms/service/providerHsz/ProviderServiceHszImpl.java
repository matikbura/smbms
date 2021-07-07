package cn.smbms.service.providerHsz;

import cn.smbms.mapper.ProviderMapper;
import cn.smbms.mapper.ProviderMapperHsz;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.ProviderHSZ;
import cn.smbms.pojo.QueryProviderHSZ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceHszImpl implements ProviderServiceHsz {
    @Autowired
    private ProviderMapperHsz providerMapperHsz;
    @Autowired
    private ProviderMapper providerMapper;
    @Override
    public boolean deleteProviderById(Integer id) {
        //回收站恢复数据思路：
        // 1.先根据 id查询信息  2.把信息封装到供应商实体类  3.调用供应商的添加方法  4.最后调用回收站的删除方法
        //根据 id查询信息
        ProviderHSZ providerHSZ = providerMapperHsz.getProviderHSZById(id);
        Provider provider = new Provider();
        provider.setProName(providerHSZ.getProName());
        provider.setProCode(providerHSZ.getProCode());
        provider.setProDesc(providerHSZ.getProDesc());
        provider.setProContact(providerHSZ.getProContact());
        provider.setProPhone(providerHSZ.getProPhone());
        provider.setProAddress(providerHSZ.getProAddress());
        provider.setProFax(providerHSZ.getProFax());
        provider.setCreatedBy(providerHSZ.getCreatedBy());
        provider.setCreationDate(providerHSZ.getCreationDate());
        provider.setModifyDate(providerHSZ.getModifyDate());
        provider.setModifyBy(providerHSZ.getModifyBy());
        //调用供应商的添加方法
        providerMapper.add(provider);
        return providerMapperHsz.deleteProviderById(id) > 0;
    }

    @Override
    public List<ProviderHSZ> getProviderList(QueryProviderHSZ queryProviderHSZ) {
        //解决sql注入问题，由业务层拼接模糊查询语句
        if (queryProviderHSZ.getQueryProName()!=null&&!"".equals(queryProviderHSZ.getQueryProName()))
        queryProviderHSZ.setQueryProName("%"+queryProviderHSZ.getQueryProName()+"%");
        if (queryProviderHSZ.getQueryProCode()!=null&&!"".equals(queryProviderHSZ.getQueryProCode()))
        queryProviderHSZ.setQueryProCode("%"+queryProviderHSZ.getQueryProCode()+"%");
        return providerMapperHsz.getProviderList(queryProviderHSZ);
    }

    @Override
    public int getUserCount(QueryProviderHSZ queryProviderHSZ) {
        //解决sql注入问题，由业务层拼接模糊查询语句
        if (queryProviderHSZ.getQueryProName()!=null&&!"".equals(queryProviderHSZ.getQueryProName()))
            queryProviderHSZ.setQueryProName("%"+queryProviderHSZ.getQueryProName()+"%");
        if (queryProviderHSZ.getQueryProCode()!=null&&!"".equals(queryProviderHSZ.getQueryProCode()))
            queryProviderHSZ.setQueryProCode("%"+queryProviderHSZ.getQueryProCode()+"%");
        return providerMapperHsz.getUserCount(queryProviderHSZ);
    }

    @Override
    public int addProviderHSZ(ProviderHSZ providerHSZ) {
        return providerMapperHsz.addProviderHSZ(providerHSZ);
    }

    @Override
    public ProviderHSZ getProviderHSZById(Integer id) {
        return providerMapperHsz.getProviderHSZById(id);
    }
}
