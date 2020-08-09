package club.chillman.demo.controller.superadmin;


import club.chillman.demo.entity.dto.Result;
import club.chillman.demo.entity.bo.HeadLine;
import club.chillman.demo.service.solo.HeadLineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HeadLineOperationController {
    private HeadLineService headLineService;
    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.addHeadLine(new HeadLine());
    }
    public void removeHeadLine(){
        System.out.println("删除HeadLine");
    }
    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.modifyHeadLine(new HeadLine());
    }
    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.queryHeadLineById(1);
    }
    public Result<List<HeadLine>>queryHeadLine(){
        return headLineService.queryHeadLine(null, 1, 100);
    }


}
