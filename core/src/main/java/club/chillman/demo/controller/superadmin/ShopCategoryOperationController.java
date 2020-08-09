package club.chillman.demo.controller.superadmin;


import club.chillman.core.annotation.Controller;
import club.chillman.demo.entity.bo.ShopCategory;
import club.chillman.demo.entity.dto.Result;
import club.chillman.demo.service.solo.ShopCategoryService;
import club.chillman.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ShopCategoryOperationController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    public Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return shopCategoryService.addShopCategory(new ShopCategory());
    }
    public Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return shopCategoryService.removeShopCategory(1);
    }
    public Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return shopCategoryService.modifyShopCategory(new ShopCategory());
    }
    public Result<ShopCategory> queryShopCategoryById(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return shopCategoryService.queryShopCategoryById(1);
    }
    public Result<List<ShopCategory>> queryShopCategory(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return shopCategoryService.queryShopCategory(null, 1, 100);
    }
}
