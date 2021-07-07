package cn.smbms.eception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmbmsEception implements HandlerExceptionResolver {
    /**
     * 异常逻辑实现方法
     */
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        // 1.判断是否是系统异常
        MyEception ssme = null;
        if(ex instanceof MyEception){
            ssme = (MyEception) ex;
        }else{
            ssme = new MyEception("未知异常!");
        }

        // 2.包装异常提示页面
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ssme.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
