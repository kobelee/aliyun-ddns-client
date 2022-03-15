package cn.kobelee.ipresolve.client.ddns;

import cn.kobelee.ipresolve.client.domain.CityInfo;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 11/17/2021
 */
@Component
public class IpHandleService {

    private final Logger logger = LoggerFactory.getLogger(IpHandleService.class);

    private IAcsClient acsClient;
    @Value("${ddns.target-domain-name}")
    private String targetDomain;
    @Value("${ddns.target-record-name}")
    private String targetRecord;
    private static String currentIp;


    @Autowired
    public void setAcsClient(IAcsClient acsClient) {
        this.acsClient = acsClient;
    }

    public UpdateDomainRecordResponse handle(String sohuCityStr) {
        final String remoteAddress = getRemoteAddress(sohuCityStr);
        try {
            logger.info("remoteAddress:{}",remoteAddress);
            if(Objects.equals(remoteAddress,currentIp)){
                logger.info("same with dns, not need to update");
                return null;
            }
            DescribeDomainRecordsResponse.Record currentRecord = this.getCurrentRecord();
            final UpdateDomainRecordResponse response = updateRecord(currentRecord, remoteAddress);
            //本地缓存，减少调用阿里云ddns次数
            currentIp = remoteAddress;
            return response;
        } catch (ClientException e) {
            sendNotification(remoteAddress, e);
            throw new RuntimeException(e);
        }
    }

    private void sendNotification(String ip, ClientException e) {
        //todo
    }

    private UpdateDomainRecordResponse updateRecord(DescribeDomainRecordsResponse.Record currentRecord, String remoteAddress) throws ClientException {
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRecordId(currentRecord.getRecordId());
        request.setValue(remoteAddress);
        request.setRR(currentRecord.getRR());
        request.setType(currentRecord.getType());
        request.setLine(currentRecord.getLine());
        request.setPriority(currentRecord.getPriority());
        request.setTTL(currentRecord.getTTL());

        return acsClient.getAcsResponse(request);
    }

    private String getRemoteAddress(String sohuCityStr) {
        final String cityInfoStr = sohuCityStr
                .replaceFirst("var returnCitySN = ", "")
                .replace(";","");
        logger.info("sohu returns:{}",cityInfoStr);
        final CityInfo cityInfo = JSON.parseObject(cityInfoStr, CityInfo.class);
        return cityInfo.getCip();
    }

    private DescribeDomainRecordsResponse.Record getCurrentRecord() throws ClientException {
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName(targetDomain);
        request.setAcceptFormat(FormatType.JSON);
        DescribeDomainRecordsResponse acsResponse = acsClient.getAcsResponse(request);
        List<DescribeDomainRecordsResponse.Record> domainRecords = acsResponse.getDomainRecords();
        return domainRecords.stream()
                .filter(r -> r.getRR().equals(targetRecord))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("record not found:" + targetRecord));
    }

}
