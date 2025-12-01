/*
 * aspire-tech.com Inc.
 * Copyright (c) 2000-2024 All Rights Reserved.
 */
package org.lionsoul.ip2region.ip;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;

import static org.lionsoul.ip2region.xdb.Version.IPv4;

/**
 * @author zhaozhixiao
 * @version v
 * @date 2024/2/21 18:08
 */
@Slf4j
@Component
public class IpUtil {
    private static String path = "E:\\Codespace\\datasophon\\datasophon\\datasophon-api\\src\\main\\resources\\ip2region_v4.xdb";
    /**
     * 将整个xdb文件加载到内存中(11M左右),此种创建方式支持多线程,因此只需要加载一次
     */

//    @Value("${xdbPath}")
//    public void setPath(String path) {
//        this.path = path;
//    }


    public static String getLocationByIpv4(String ipv4) throws Exception {
        Searcher searcher = Searcher.newWithFileOnly(IPv4, path);
        String region = searcher.search(ipv4);
        return region;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("直接获取"+getLocationByIpv4("39.135.243.39"));
        String ip = "39.135.243.40";
        IpLocation loc = getLocationFromIp(ip);
        System.out.println("通过对象获取"+loc);

    }

    private static IpLocation getLocationFromIp(String ip) throws Exception {
        String region = getLocationByIpv4(ip);
        return getIpLocation(region);
    }

    private static IpLocation getIpLocation(String region) {
        String[] parts = region.split("\\|");
        IpLocation loc = new IpLocation();

        loc.setCountry(parts.length > 0 ? obtain(parts[0]) : "未知");
        loc.setProvince(parts.length > 1 ? obtain(parts[1]) : "未知");
        loc.setCity(parts.length > 2 ? obtain(parts[2]) : "未知");
        loc.setIsp(parts.length > 4 ? obtain(parts[4]) :
                (parts.length > 3 ? obtain(parts[3]) : "未知")); // 有些版本 ISP 在第4或第5位
        return loc;
    }

    private static String obtain(String s) {
        return "0".equals(s) ? "未知" : s;
    }
}
