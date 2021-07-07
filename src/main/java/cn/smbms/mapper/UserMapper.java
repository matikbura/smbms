package cn.smbms.mapper;

import cn.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //查询用户登录数据
    User login(@Param("userCode")String userCode, @Param("userPassword") String userPassword);

    //修改用户密码
    void updateUserPassword(User user);
    //根据id获得用户
    User getUserById(Integer id);
    int add(User user);

    User getLoginUser(String userCode, String userCode1);

    List<User> getUserList(@Param("userName") String queryUserName,
                           @Param("userRole") Integer queryUserRole, @Param("pageNo") int start, @Param("pageSize") int pageSize);

    User selectUserCodeExist(String userCode);

    int deleteUserById(Integer delId);

    int modify(User user);

    int updatePwd(int id, String pwd);

    int getUserCount(@Param("userName") String queryUserName, @Param("userRole") Integer queryUserRole);
}
