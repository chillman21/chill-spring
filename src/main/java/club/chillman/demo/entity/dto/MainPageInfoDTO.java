package club.chillman.demo.entity.dto;


import club.chillman.demo.entity.bo.HeadLine;
import club.chillman.demo.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
