package cn.smbms.mapper;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.QueryProvider;

import java.util.List;

public interface ProviderMapper {

    int add(Provider provider);//添加方法

    int modify(Provider provider);//修改方法

    Provider getProviderById(Integer id);//根据id查看

    List<Provider> getProviderList(QueryProvider queryProvider);//查询学生，模糊查询

    void deleteProviderById(Integer delId);//根据id删除学生

    int getProviderCount(QueryProvider queryProvider);//分页
}
