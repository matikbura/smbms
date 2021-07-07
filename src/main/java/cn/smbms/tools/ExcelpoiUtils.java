package cn.smbms.tools;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class ExcelpoiUtils {
    //上传
    public static <T>List<T> excelToList(MultipartFile excelFile, Class<T> clazz, Map<String,String> excelHeads, String dateString) throws Exception {

        List<T> list = new ArrayList<>();
        //判断是xls  xmls
        //获取后缀
        System.out.println(excelFile.getOriginalFilename());
        String extString = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf("."));
        InputStream inputStream = excelFile.getInputStream();
        Workbook sheets = null;
        if(".xls".equals(extString)){
            sheets = new HSSFWorkbook(inputStream);
        }else if(".xlsx".equals(extString)){
            sheets = new XSSFWorkbook(inputStream);
        }
        assert sheets != null;
        Row head = null;
        for (Sheet sheet : sheets) {
            for (Row row : sheet) {
                if (row.getRowNum()==0){
                    head = row;
                    continue;
                }
                int number = row.getPhysicalNumberOfCells();
                T t = clazz.getConstructor().newInstance();
                for (int i = 0; i <number; i++) {
                    String stringCellValue = head.getCell(i).getStringCellValue();
                    System.out.println(stringCellValue);
                    String beanFieldName = excelHeads.get(stringCellValue);//获得对应的字段名
                    System.out.println(beanFieldName);
                    Field field = t.getClass().getDeclaredField(beanFieldName);//获取字段
                    field.setAccessible(true);
                    Cell cell = row.getCell(i);//获取单元格
                    if (cell==null){
                        number++;
                        continue;
                    }
                    Class<?> type = field.getType();//
                    if (type.equals(String.class)){
                        field.set(t, getCellValue(cell));
                    }else if (type.equals(Integer.class)){
                        field.set(t, Integer.valueOf(getCellValue(cell)));
                    }else if (type.equals(Long.class.getName())){
                        field.set(t,Long.valueOf(getCellValue(cell)));
                    }else if (type.equals(Float.class)){
                        field.set(t, Float.valueOf(getCellValue(cell)));
                    }else if (type.equals(Double.class)){
                        field.set(t, Double.valueOf(getCellValue(cell)));
                    }else if (type.equals(Byte.class)){
                        field.set(t, Byte.valueOf(getCellValue(cell)));
                    }else if (type.equals(Boolean.class)){
                        field.set(t, Boolean.valueOf(getCellValue(cell)));
                    }else if (type.equals(BigDecimal.class)){
                        field.set(t, BigDecimal.valueOf(Double.parseDouble(getCellValue(cell))));
                    }else if (type.equals(Date.class)){
                        SimpleDateFormat sdf = new SimpleDateFormat(dateString);
                        Date date=null;
                        try {
                            date=sdf.parse(getCellValue(cell));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        field.set(t, date);
                    }else {
                        field.set(t, getCellValue(cell));
                    }
                }
                list.add(t);
            }
        }
        return list;
    }

    private static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        result = dff.format(theDate);
                    }else{
                        DecimalFormat df = new DecimalFormat("0");
                        result = df.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }






    //下载
    //返回excel工作空间
    public static<T> HSSFWorkbook getHSSFWorkbook(String sheetName,LinkedHashMap<String,String> headMap,List<T> list, HSSFWorkbook wb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);; // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;
        //创建标题
        int i =0;
        for (String s : headMap.keySet()) {
            cell = row.createCell(i);
            cell.setCellValue(s);
            cell.setCellStyle(style);
            i++;
        }
        //放入数据
        int z=1;
        if (list!=null){
            T t1 = list.get(0);
            Method[] m = t1.getClass().getDeclaredMethods();
            for (T t : list) {
                row=sheet.createRow(z);
                i=0;
                for (String key : headMap.keySet()) {
                    String name = headMap.get(key);
                    Object result=null;
                    for (int j = 0; j < m.length; j++) {
                        System.out.println(m[j].getName());
                        if (("get" + name).toLowerCase().equals(m[j].getName().toLowerCase())) {
                            result=m[j].invoke(t);
                        }
                    }
                    if (result instanceof String){
                        System.out.println((String)result);
                        row.createCell(i).setCellValue((String)result);
                    }
                    if (result instanceof Integer) {
                        row.createCell(i).setCellValue((Integer) result);
                    }
                    if (result instanceof BigDecimal){
                        row.createCell(i).setCellValue(String.valueOf((BigDecimal) result));
                    }
                    if (result instanceof Date){
                        row.createCell(i).setCellValue((Date) result);
                    }
                    i++;
                }
                z++;
            }
        }
        return wb;
    }

    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                //设置表文件名的字符编码，不然中文文件名会乱码
                fileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}





