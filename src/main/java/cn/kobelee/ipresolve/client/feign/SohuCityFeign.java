package cn.kobelee.ipresolve.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 3/11/2022
 */
@FeignClient(name = "sohuCityFeign",url = "http://pv.sohu.com")
public interface SohuCityFeign {

    /**
     * 获取本机城市信息，包含公网ip，城市id，城市name
     * @return
     */
    @GetMapping("/cityjson")
    String currentCityInfo();
}
