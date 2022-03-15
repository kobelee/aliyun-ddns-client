package cn.kobelee.ipresolve.client.domain;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 3/11/2022
 */
public class CityInfo implements Serializable {
    /**
     * ip
     */
   private String cip;
    /**
     * city id
     *
     */
   private String cid;
    /**
     * city name
     */
   private String cname;

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
