package club.chillman.demo.service.combine;


import club.chillman.demo.entity.dto.MainPageInfoDTO;
import club.chillman.demo.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
