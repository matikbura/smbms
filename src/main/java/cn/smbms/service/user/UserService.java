package cn.smbms.service.user;

import cn.smbms.pojo.User;

import java.util.List;

public interface UserService {
    //修改用户密码
    void updateUserPassword(Integer id,String newpassword);
    //根据id获得用户
    User getUserById(Integer id);
    /**
     * 增加用户信息
     * @param user
     * @return
     */
    public boolean add(User user);

    /**
     * 用户登录
     * @param userCode
     * @param userPassword
     * @return
     */
    public User login(String userCode,String userPassword);

    /**
     * 根据条件查询用户列表
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public List<User> getUserList(String queryUserName,Integer queryUserRole,int start, int pageSize);
    /**
     * 根据条件查询用户表记录数
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public int getUserCount(String queryUserName,Integer queryUserRole);

    /**
     * 根据userCode查询出User
     * @param userCode
     * @return
     */
    public User selectUserCodeExist(String userCode);

    /**
     * 根据ID删除user
     * @param delId
     * @return
     */
    public boolean deleteUserById(Integer delId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean modify(User user);

    /**
     * 根据userId修改密码
     * @param id
     * @param pwd
     * @return
     */
    public boolean updatePwd(int id,String pwd);
}
