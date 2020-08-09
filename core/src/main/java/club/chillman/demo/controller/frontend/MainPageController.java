package club.chillman.demo.controller.frontend;


import club.chillman.core.annotation.Controller;
import club.chillman.demo.entity.dto.MainPageInfoDTO;
import club.chillman.demo.entity.dto.Result;
import club.chillman.demo.service.combine.HeadLineShopCategoryCombineService;
import club.chillman.inject.annotation.Autowired;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@Getter
public class MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl2")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
    public void throwException(){
        throw new RuntimeException("抛出异常测试");
    }
    public void print(){
        log.debug("辛苦了");
    }
}
