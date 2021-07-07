package cn.smbms.controller;

import cn.smbms.eception.MyEception;
import cn.smbms.pojo.ProviderHSZ;
import cn.smbms.pojo.QueryProviderHSZ;
import cn.smbms.service.providerHsz.ProviderServiceHsz;
import cn.smbms.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/providerHsz")
public class ProviderControllerHsz {
    @Autowired
    private ProviderServiceHsz providerServiceHsz;
    @RequestMapping("/query")
    public String getProviderLists(QueryProviderHSZ queryProviderHSZ, Model model) throws MyEception{
        try {
            //如果当前页为空时，设置当前页为 1
            if (queryProviderHSZ.getCurrentPageNo()==null){
                queryProviderHSZ.setCurrentPageNo(1);
            }
            //设置页长
            queryProviderHSZ.setPageSize(Constants.pageSize);

            //获取开始和结束时间
            String queryMinDateStr = queryProviderHSZ.getQueryMinDateStr();
            String queryMaxDateStr = queryProviderHSZ.getQueryMaxDateStr();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //如果最小时间字符串不为空且不为空字符串，就转为 date类型
            if (queryMinDateStr!=null&&!"".equals(queryMinDateStr)){
                queryProviderHSZ.setQueryMinDate(sdf.parse(queryMinDateStr));
            }
            if (queryMaxDateStr!=null&&!"".equals(queryMaxDateStr)){
                queryProviderHSZ.setQueryMaxDate(sdf.parse(queryMaxDateStr));
            }
            //获取供应商 名称和编码
            String queryProName = queryProviderHSZ.getQueryProName();
            String queryProCode = queryProviderHSZ.getQueryProCode();
            //调用 service层方法 获取信息总条数
            int totalCount	= providerServiceHsz.getUserCount(queryProviderHSZ);
            System.out.println(totalCount);
            //定义总页数
            Integer totalPageCount;
            //如果总条数(查询数)%页大小 = 0 ，总页数就等于 总条数(查询数)/页大小
            if(totalCount % queryProviderHSZ.getPageSize() == 0){
                totalPageCount = totalCount / queryProviderHSZ.getPageSize();
            }else if(totalCount % queryProviderHSZ.getPageSize() > 0){
                //如果总条数(查询数)%页大小 > 0 ，总页数就等于 总条数(查询数)/页大小+1
                totalPageCount = totalCount / queryProviderHSZ.getPageSize() + 1;
            }else{
                totalPageCount = 0;
            }
            if (totalCount==0){
                totalPageCount=1;
            }
            //控制首页和尾页
            if(queryProviderHSZ.getCurrentPageNo() < 1){
                 queryProviderHSZ.setCurrentPageNo(1);
            }else if(queryProviderHSZ.getCurrentPageNo() > totalPageCount){
                queryProviderHSZ.setCurrentPageNo(totalPageCount);
            }
            //设置起始位置 页大小 * (当前页 -1)
            queryProviderHSZ.setStart(Constants.pageSize*(queryProviderHSZ.getCurrentPageNo()-1));
            //模糊查询 + 分页
            List<ProviderHSZ> providerList = providerServiceHsz.getProviderList(queryProviderHSZ);
            model.addAttribute("providerList",providerList);
            model.addAttribute("queryProName",queryProName);
            model.addAttribute("queryProCode",queryProCode);
            model.addAttribute("queryMinDate",queryProviderHSZ.getQueryMinDate());
            model.addAttribute("queryMaxDate",queryProviderHSZ.getQueryMaxDate());
            model.addAttribute("totalPageCount", totalPageCount);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currentPageNo", queryProviderHSZ.getCurrentPageNo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "hsz";
    }
    @RequestMapping("/delete")
    @ResponseBody   //把响应的java对象，转换成json格式的数据
    public Map deleteProviderById(Integer id) throws MyEception{
        Map<String,String> map = new HashMap<>();
        if (providerServiceHsz.getProviderHSZById(id)!=null){
            if (providerServiceHsz.deleteProviderById(id)){ //删除成功
                map.put("delResult","true");
            }else{
                map.put("delResult","false");   //删除失败
            }
        }else {
            map.put("delResult","notexist");  //不存在
        }
        return map;
    }
    @RequestMapping("/add")
    public String addProviderHSZ(ProviderHSZ providerHSZ) throws MyEception{
        int i = providerServiceHsz.addProviderHSZ(providerHSZ);
        System.out.println(i>0?"添加成功":"添加失败");
        return "hsz";
    }
    @RequestMapping("/queryById")
    public void getProviderHSZById(Integer id,Model model) throws MyEception {
        ProviderHSZ providerHSZById = providerServiceHsz.getProviderHSZById(id);
        System.out.println("根据id查询的信息:"+providerHSZById);
    }
}
