package cn.smbms.controller;


import cn.smbms.eception.MyEception;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.QueryProvider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static cn.smbms.tools.Constants.pageSize;

//供应商控制器
@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;
    //添加的方法
    @RequestMapping("add")
    public String add(Provider provider, HttpServletRequest request) throws MyEception{
        String str=null;
        User user =(User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (user!=null){
            provider.setCreatedBy(user.getId());
            provider.setCreationDate(new Date());
        }
        boolean add = providerService.add(provider);
        if (add=true){
            str="redirect:query";
        }else {
            str="error";
        }
        return str;
    }

    @RequestMapping("query")
    public String getProviderList (Model model, QueryProvider queryProvider) throws MyEception{

        if(queryProvider.getCurrentPageNo() == null){
            queryProvider.setCurrentPageNo(1);
        }
        String queryProName=queryProvider.getQueryProName();
        String queryProCode=queryProvider.getQueryProCode();

        //总数量（表）
        int totalCount	= providerService.getProviderCount(queryProvider);

        int totalPageCount;
        if(totalCount % pageSize == 0){
            totalPageCount = totalCount / pageSize;
        }else if(totalCount % pageSize > 0){
            totalPageCount = totalCount / pageSize + 1;
        }else{
            totalPageCount = 0;
        }

        Integer currentPageNo = queryProvider.getCurrentPageNo();

        //totalPageCount 查询出来0
        //curPage 1   初始化1
        //控制首页和尾页
        if (totalPageCount!=0){
            if(currentPageNo < 1){
                currentPageNo = 1;
            }else if(currentPageNo > totalPageCount){
                currentPageNo = totalPageCount;
            }
        }else {
            if(currentPageNo < 1){
                currentPageNo = 1;
            }else if(currentPageNo > totalPageCount){
                currentPageNo = 1;
            }
        }
        queryProvider.setCurrentPageNo(currentPageNo);
        queryProvider.setPageSize(Constants.pageSize);
        queryProvider.setStart((queryProvider.getCurrentPageNo()-1)*Constants.pageSize);
        List<Provider> providerList = providerService.getProviderList(queryProvider);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPageCount",totalPageCount);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("providerList",providerList);
        //System.out.println(providerList);
        return "providerlist";
    }
    @RequestMapping("/queryOne")
    public String queryOne (String proid,Model model) throws MyEception{
      Provider provider=providerService.getProviderById(proid);
      model.addAttribute("provider",provider);
        return "providerview";
    }

    @RequestMapping("/preModify")
    public String queryOneById(String proid,Model model) throws MyEception{
        Provider provider=providerService.getProviderById(proid);
        model.addAttribute("provider",provider);
        return "providermodify";
    }

    //修改供应商的方法  对应保存按钮
    @RequestMapping("modify")
    public String modify(Model model, Provider provider, String proid, HttpServletRequest request) throws MyEception{
        String str=null;
        User user =(User) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            if (providerService.getProviderById(proid)!=null){
                provider.setModifyBy(user.getId());
                provider.setModifyDate(new Date());
                providerService.modify(provider);
                boolean modify = providerService.modify(provider);
                if (modify=true){
                    str="redirect:query";    //重定向到query方法里面
                }
                else {
                    str="error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    @RequestMapping("/deleteProviderById")
    @ResponseBody
    public  HashMap<String, String> deleteProviderById(String proid) throws MyEception {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(proid!=null&&!"".equals(proid)){

            int flag = providerService.deleteProviderById(proid);
            if(flag == 0){ //删除成功
                resultMap.put("delResult", "true");
            }else if(flag == -1){ //删除失败
                resultMap.put("delResult", "false");
            }else if(flag > 0){ //该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        return resultMap;
    }
}
