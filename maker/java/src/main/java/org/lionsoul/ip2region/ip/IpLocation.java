package org.lionsoul.ip2region.ip;

import lombok.Data;

@Data
public class IpLocation {
    private String country = "未知";
    private String province = "未知";// 省
    private String city = "未知";
    private String isp = "未知";// 运营商

    // 构造函数、getter/setter 省略
    public static IpLocation unknown() {
        return new IpLocation();
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s", country, province, city, isp);
    }
}
