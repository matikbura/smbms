package cn.smbms.service.role;

import cn.smbms.mapper.RoleMapper;
import cn.smbms.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> getRoleList() {
		return roleMapper.getRoleList();
	}
	
}
