package cn.smbms.controller;

import cn.smbms.eception.MyEception;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.FuzzyQuery;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.ExcelpoiUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    BillService billService;
    @RequestMapping("/addBillByExcelFile")
    @ResponseBody
    public String addBillByExcelFile(MultipartFile hello, HttpSession session) throws MyEception {
        HashMap<String,String> map = new HashMap<>();
        map.put("订单编码","billCode");
        map.put("订单描述","productDesc");
        map.put("商品名称","productName");
        map.put("商品单位","productUnit");
        map.put("商品数量","productCount");
        map.put("总金额","totalPrice");
        map.put("供应商","providerId");
        map.put("是否付款","isPayment");
        List<Bill> billList = null;
        try {
            billList = ExcelpoiUtils.excelToList(hello, Bill.class, map, "YYYY:dd:hh");
        } catch (Exception e) {
           throw new MyEception("文件读取出错");
        }
        Integer id = ((User) (session.getAttribute(Constants.USER_SESSION))).getId();
        for (Bill bill : billList) {
            bill.setCreatedBy(id);
            bill.setCreationDate(new Date());
            bill.setBillCode(UUID.randomUUID().toString().replaceAll("-",""));
            billService.addBill(bill);
        }
        return "true";
    }
    @RequestMapping("/getExcelFileByBill")
    public void getExcelFileByBill(HttpServletResponse response) throws MyEception {
        List<Bill> allBill = billService.getAllBillForExcel();
        ExcelpoiUtils.setResponseHeader(response,"hello.xls");
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("订单编码","billCode");
        map.put("订单描述","productDesc");
        map.put("商品名称","productName");
        map.put("商品单位","productUnit");
        map.put("商品数量","productCount");
        map.put("总金额","totalPrice");
        map.put("供应商","providerId");
        map.put("是否付款","isPayment");
        try {
           HSSFWorkbook hello = ExcelpoiUtils.getHSSFWorkbook("导出全部订单", map, allBill, null);
            hello.write(response.getOutputStream());
        } catch (IOException e) {
            throw new MyEception("导出订单失败");
        } catch (InvocationTargetException e) {
            throw new MyEception("导出订单失败");
        } catch (NoSuchMethodException e) {
            throw new MyEception("导出订单失败");
        } catch (IllegalAccessException e) {
            throw new MyEception("导出订单失败");
        }
    }




    //获取所有的订单
    @RequestMapping("/queryAllBill")
    public String getAllbill(Model model, FuzzyQuery fuzzyQuery) throws MyEception{
        List<Bill>list=null;
        try {
            //当没有模糊查询时，查询所有订单
            if (fuzzyQuery.getQueryProductName()==null&&fuzzyQuery.getQueryIsPayment()==null&&fuzzyQuery.getQueryProviderId()==null) {
                list = billService.getAllBill(fuzzyQuery);
                //分页
                model.addAttribute("totalCount",fuzzyQuery.getTotalCount());
                model.addAttribute("totalPageCount",fuzzyQuery.getTotalPageCount());
                model.addAttribute("currentPageNo",(fuzzyQuery.getCurrentPageNo()/ Constants.pageSize)+1);
            }
            else {
                //当模糊查询存在时
                if (fuzzyQuery.getQueryProductName()!=null||fuzzyQuery.getQueryProviderId()!=0||fuzzyQuery.getQueryIsPayment()!=0){
                    list=billService.getBillList(fuzzyQuery);
                    if (fuzzyQuery.getTotalPageCount()==null) {
                        fuzzyQuery.setTotalPageCount(1);
                    }
                }
                //模糊查询的数据回显
                model.addAttribute("queryProductName", fuzzyQuery.getQueryProductName());
                model.addAttribute("queryProviderId", fuzzyQuery.getQueryProviderId());
                model.addAttribute("queryIsPayment", fuzzyQuery.getQueryIsPayment());
                //分页
                model.addAttribute("totalCount",fuzzyQuery.getTotalCount());
                model.addAttribute("totalPageCount",fuzzyQuery.getTotalPageCount());
                model.addAttribute("currentPageNo",(fuzzyQuery.getCurrentPageNo()/ Constants.pageSize)+1);
            }
            List<Provider> providerIdAndName = billService.getProviderIdAndName();
            model.addAttribute("providerList",providerIdAndName);
            model.addAttribute("billList",list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "billlist";
    }
    //根据订单id查看订单
    @RequestMapping("/queryBillById")
    public String getBillById(Model model,Integer billid) throws MyEception{
        Bill bill=billService.getBillById(billid);
        model.addAttribute("bill",bill);
        return "billview";
    }
    //查询供应商返回json字符串
    @RequestMapping("/getProvider")
    @ResponseBody
    public List<Provider> getProviderIdAndName(){
        List<Provider> list=billService.getProviderIdAndName();

        return list;
    }
    //添加订单
    @RequestMapping("addBill")
    public String addBill(Bill bill, HttpSession httpSession) throws MyEception{
        String s="";
        try {
            //获取session域对象，得到用户id
            User user = (User) httpSession.getAttribute(Constants.USER_SESSION);
            if(user!=null){
                bill.setCreatedBy(user.getId());
            }
            int i=billService.addBill(bill);
            if (i>0){
                s="forward:queryAllBill";
            }else {
                s="error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    //删除订单
    @RequestMapping("deleteBillById")
    @ResponseBody
    public Map<String,String> deleteBillById(@RequestParam("billid") Integer pid){
        Map<String,String> map=new HashMap<>();
        try {
            Bill billById = billService.getBillById(pid);
            if (billById.getId()!=null){
                int i=billService.deleteBillById(pid);
                if (i>0){
                    map.put("delResult","true");
                }else {
                    map.put("delResult","false");
                }
            }else {
                map.put("delResult","notexist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    //根据需要修改的订单id查看订单
    @RequestMapping("/queryUpdateBillById")
    public String queryUpdateBillById(Model model, Integer billid) throws MyEception{
        Bill bill=billService.getBillById(billid);
        model.addAttribute("bill",bill);
        return "billmodify";
    }

    //根据订单id修改订单信息
    @RequestMapping("/updateBill")
    public String updateBill(Bill bill,HttpSession httpSession) throws MyEception{
        String s="";
        try {
            //获取用户id，设置修改订单的用户
            User user=(User) httpSession.getAttribute(Constants.USER_SESSION);
            if (user!=null){
                bill.setModifyBy(user.getId());
            }
            int i=billService.updateBill(bill);
            if (i>0){
                s="forward:queryAllBill";
            }else {
                s="error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    //联想查询
    @RequestMapping("/queryAllName")
    @ResponseBody
    public List<Bill> queryAllName(String queryProductName){
        List<Bill>list=billService.queryAllName(queryProductName);
        return list;
    }
}
