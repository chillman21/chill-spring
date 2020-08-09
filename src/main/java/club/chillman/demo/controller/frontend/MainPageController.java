package club.chillman.demo.controller.frontend;



import club.chillman.demo.entity.dto.MainPageInfoDTO;
import club.chillman.demo.entity.dto.Result;
import club.chillman.demo.service.combine.HeadLineShopCategoryCombineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
    public void throwException(){
        throw new RuntimeException("抛出异常测试");
    }
}
