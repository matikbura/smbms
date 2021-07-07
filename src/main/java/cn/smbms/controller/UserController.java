package cn.smbms.controller;

import cn.smbms.eception.MyEception;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//控制器
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/login")
    public ModelAndView getUserList(String userCode, String userPassword,HttpSession session) throws MyEception{
        ModelAndView modelAndView = new ModelAndView();
        User user = this.userService.login(userCode, userPassword);
        if (user==null){//如果返回的数据集合长度为0 跳转到错误页面
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("frame");
        session.setAttribute("user",userCode);
        session.setAttribute("password",userPassword);
        session.setAttribute("id",user.getId());
        User userById = this.userService.getUserById(user.getId());
        session.setAttribute("userSession",userById);
        return modelAndView;
        }

    @RequestMapping("/updateUserPassword")
    public String updateUserPassword(String oldpassword,String newpassword,String rnewpassword,HttpSession session) throws MyEception{
        String password = (String) session.getAttribute("password");
        Integer id = (Integer) session.getAttribute("id");
        //System.out.println(password+"----------------------"+oldpassword+"--------------"+newpassword+"-------------------"+rnewpassword);
        if (oldpassword!=null&&newpassword!=null&&rnewpassword!=null) {
            if (password.equals(oldpassword)) {
                if (!newpassword.equals(oldpassword)) {
                    this.userService.updateUserPassword(id,newpassword);
                    return "pwdmodify";
                }
            }
        }

        return "error";
    }
    //退出
    @RequestMapping("/logout")
    public String exitSystem(HttpSession session) throws MyEception{
        //session.removeAttribute("user");
        session.removeAttribute("userSession");
        return "redirect:/login.jsp";
    }



    @RequestMapping("/getUserSession")
    @ResponseBody
    public User getUserSession(HttpSession session) throws MyEception{
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        User user1 = new User();
        if (user.getImgPath()!=null){
            user1.setImgPath(Constants.IMG_ADDRESS+user.getImgPath());
        }
        return  user1;
    }

    @RequestMapping("/pwdmodify")
    @ResponseBody
    public Map pwdmodify(String oldpassword,HttpSession session) throws MyEception{
        String password = (String) session.getAttribute("password");
        Map<String, String> resultMap = new HashMap<>();
        if(password==null){
            resultMap.put("result","sessionerror");
        }else if(oldpassword==null||"".equals(oldpassword)){    //旧密码输入为空
            resultMap.put("result","error");
        }else {
            if (oldpassword.equals(password)){    //如果输入的旧密码合格
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }
        System.out.println(oldpassword);
        System.out.println("-----------------------------");
        return resultMap;
    }




    @RequestMapping("/query")
    public String userQuery(Model model,
                            @RequestParam(value = "queryUserName",required = false) String queryUserName,
                            @RequestParam(value = "queryUserRole",required = false ) String queryUserRole,
                            @RequestParam(value ="currentPageNo",required = false) Integer currentPageNo) throws MyEception{
        Integer queryUserRole1=null;
        List<User> userList = null;
        //设置页面容量
        Integer  pageSize = Constants.pageSize;


        //判断queryUserName 如果空则设为空字符串
        if(queryUserName == null){
            queryUserName = "";
        }
        //判断queryUserRole
        if(queryUserRole != null&&!"".equals(queryUserRole)){
            queryUserRole1=Integer.valueOf(queryUserRole);
        }
        if (queryUserRole==null||"".equals(queryUserRole)){
            queryUserRole1=0;
        }
        //判断当前页  为空则复制为1
        if(currentPageNo == null){
            try{
                currentPageNo = 1;
            }catch(NumberFormatException e){
                return "redirect:error";
            }
        }
        System.out.println("queryUserName servlet--------"+queryUserName);
        System.out.println("queryUserRole servlet--------"+queryUserRole);
        System.out.println("query pageIndex--------- > " + currentPageNo);
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole1);
        System.out.println("总数量"+totalCount);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();
        System.out.println("总页数"+totalPageCount);

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        int start=(currentPageNo-1)*pageSize;

        userList = userService.getUserList(queryUserName,queryUserRole1,start, pageSize);
        for (User user : userList) {
            if (user.getImgPath()!=null&&!"".equals(user.getImgPath())){
                user.setImgPath(Constants.IMG_ADDRESS+user.getImgPath());
            }
        }
        model.addAttribute("userList",userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole1", queryUserRole1);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }
    @RequestMapping("/view")
    public String getUserById(Model model,Integer uid) throws MyEception{
        String view=null;
        if (uid != null){
            System.out.println("用户id"+uid);
            //调用userService
            User user = userService.getUserById(uid);
            if (user.getImgPath()!=null){
                user.setImgPath(Constants.IMG_ADDRESS+user.getImgPath());
            }
            List<Role> roleList = roleService.getRoleList();
            model.addAttribute("roleList",roleList);
            model.addAttribute("user",user);
            view="userview";
        }
        return view;
    }
    @RequestMapping("/preModify")
    public String preModify(Model model,Integer uid) throws MyEception{
        //调用userService 查询用户
        User user = userService.getUserById(uid);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList",roleList);
        model.addAttribute("user",user);
//        boolean modify = userService.modify(user);
        return "usermodify";
    }
    @RequestMapping("/modify")
    private String modify(Model model,User user,HttpSession session) throws MyEception {
        //获取userSession
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        //设置修改者的 setModifyBy  setModifyDate
        user.setModifyBy(userSession.getId());
        user.setModifyDate(new Date());
        String birthdayStr = user.getBirthdayStr();
        System.out.println("----------"+user.getId());
        //将string的 birthdayStr  装换 为Date
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr));
        } catch (ParseException e) {
            throw new MyEception("日期转换异常");
        }
        //调用 修改方法
        userService.modify(user);
        System.out.println(user.getUserName()+user.getUserRole());

        return "redirect:query";
    }
    //ajax
    @RequestMapping("/getrolelist")
    @ResponseBody
    public List<Role> getRoleList() throws MyEception {
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        System.out.println(roleList.get(0));
        return roleList;
    }
    @RequestMapping("/ucexist")
    @ResponseBody
    public HashMap<String, String> userCodeExist( String userCode) throws MyEception{
        //判断用户账号是否可用
        System.out.println(userCode);
        User user=null;
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }
        return resultMap;
    }
    //添加
    @RequestMapping("/add")
    public String addUser(Model model,User user,HttpSession session) throws MyEception {
        //获取userSession
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        //设置创建者setCreatedBy  setCreationDate
        user.setCreatedBy(userSession.getId());
        user.setCreationDate(new Date());
        //获取字符串的birthdayStr
        String birthdayStr = user.getBirthdayStr();
        System.out.println("----------"+user.getId());
        //将string的 birthdayStr  装换 为Date
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr));
        } catch (ParseException e) {
            throw new MyEception("日期转换异常");
        }
        System.out.println(user.getUserName());
        userService.add(user);
        return "redirect:query";
    }
    //删除用户
    @RequestMapping("/deluser")
    @ResponseBody
    public HashMap<String, String> delUser(Integer uid) throws MyEception{
        System.out.println("要删除的id"+uid);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(uid <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.deleteUserById(uid)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }
        return resultMap;
    }
    @RequestMapping("uploadHeadImage")
    @ResponseBody
    public String uploadHeadImage(@RequestParam("imgStr") String base64Data,HttpSession session) throws MyEception {
        HashMap<String,Object> hashMap=new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String dataPrix = "";
            String data = "";

            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            String imgName= UUID.randomUUID().toString();
            String tempFileName = imgName + suffix;

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);
            try{
                File file=null;
                //使用apache提供的工具类操作流
                String url = "D:\\study_home\\imgWork";
                FileUtils.writeByteArrayToFile(new File(url+"/" + tempFileName), bs);
            }catch(Exception ee){
                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }
            User user = userService.getUserById(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
            if (user.getImgPath()!=null&&!"".equals(user.getImgPath())){
                File file = new File("D:\\study_home\\imgWork\\"+user.getImgPath());
                file.delete();
            }
            user.setImgPath(tempFileName);
            userService.modify(user);
            session.setAttribute(Constants.USER_SESSION,user);
        }catch (Exception e){
            hashMap.put("end","upload err");
            e.printStackTrace();
        }
        return "ok";
    }
}
