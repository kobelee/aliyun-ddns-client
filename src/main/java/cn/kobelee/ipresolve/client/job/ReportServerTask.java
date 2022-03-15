package cn.kobelee.ipresolve.client.job;

import cn.kobelee.ipresolve.client.ddns.IpHandleService;
import cn.kobelee.ipresolve.client.feign.SohuCityFeign;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 11/18/2021
 */
@Component
public class ReportServerTask {
    private final IpHandleService ipHandleService;
    private final SohuCityFeign sohuCityFeign;
    @Scheduled(cron = "0 */1 * * * ?")
    public void execute(){
        final String cityInfoStr = sohuCityFeign.currentCityInfo();
        ipHandleService.handle(cityInfoStr);
    }

    public ReportServerTask(IpHandleService ipHandleService, SohuCityFeign sohuCityFeign){
        this.ipHandleService = ipHandleService;
        this.sohuCityFeign = sohuCityFeign;
    }
}
