package cn.smbms.service.user;

import cn.smbms.mapper.UserMapper;
import cn.smbms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //根据用户名和密码获得用户数据的集合
    @Override
    public User login(String userCode, String userPassword) {
        return userMapper.login(userCode,userPassword);
    }

    @Override
        public void updateUserPassword(Integer id,String newpassword) {
        //根据id获得user对象
        User user = this.userMapper.getUserById(id);
        //把新密码赋值给老密码
        user.setUserPassword(newpassword);
        //根据user对象修改用户数据
        this.userMapper.updateUserPassword(user);
    }

    @Override
    public User getUserById(Integer id) {
        return this.userMapper.getUserById(id);
    }

    @Override
    public boolean add(User user) {
        // TODO Auto-generated method stub
        boolean flag = false;
        int updateRows = userMapper.add(user);
        if(updateRows > 0){
            flag = true;
            System.out.println("add success!");
        }else{
            System.out.println("add failed!");
        }
        return flag;
    }
    @Override
    public List<User> getUserList(String queryUserName,Integer queryUserRole,int start, int pageSize) {
        // TODO Auto-generated method stub
        queryUserName="%"+queryUserName+"%";

        return userMapper.getUserList(queryUserName,queryUserRole,start,pageSize);
    }
    @Override
    public User selectUserCodeExist(String userCode) {
        return userMapper.selectUserCodeExist(userCode);
    }
    @Override
    public boolean deleteUserById(Integer delId) {
        // TODO Auto-generated method stub
        return userMapper.deleteUserById(delId) > 0;

    }
    @Override
    public boolean  modify(User user) {
        // TODO Auto-generated method stub
        return userMapper.modify(user)>0;
    }
    @Override
    public boolean updatePwd(int id, String pwd) {
        // TODO Auto-generated method stub
        return userMapper.updatePwd(id,pwd) > 0;
    }
    @Override
    public int getUserCount(String queryUserName, Integer queryUserRole) {
        // TODO Auto-generated method stub
        queryUserName="%"+queryUserName+"%";
        return userMapper.getUserCount(queryUserName,queryUserRole);
    }
}
