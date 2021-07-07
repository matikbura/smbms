package cn.smbms.service.providerHsz;

import cn.smbms.pojo.ProviderHSZ;
import cn.smbms.pojo.QueryProviderHSZ;

import java.util.List;

public interface ProviderServiceHsz {
    //查询用户，并且模糊查询
    List<ProviderHSZ> getProviderList(QueryProviderHSZ queryProviderHSZ);
    //查询表中的总条数
    int getUserCount(QueryProviderHSZ queryProviderHSZ);
    //添加信息
    int addProviderHSZ(ProviderHSZ providerHSZ);
    //根据 id恢复信息
    //1.根据id 查询信息
    ProviderHSZ getProviderHSZById(Integer id);
    //2.根据 id恢复信息
    boolean deleteProviderById(Integer id);
}
