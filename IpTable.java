/*  
 * This file provides the implementation of the class IpTable. 
 * IpTable: a form of hash BitMap, using one bit to present an IP.
 *
 *  FileName:    IpTable.java 
 *  description: use BitMap to implement Iptable.
 *  Author:      wzb (zhongbo.wzb@alibaba-inc.com)
 *  Date:        2014-04-19 16:13
 *  Version:     0.1
 *
 * */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.net.URL;
import java.net.URI;

public class IpTable {
  private BitMap table;
  private long ipNum;
  

  private IpTable() {
  }

  public static IpTable getIpTable(String ipTableFile) {
    IpTable ipTable = new IpTable();
    ipTable.table = new BitMap(1L << 32);
    ipTable.ipNum = 0;

    try { 
      InputStream is = ipTable.getClass().getClassLoader().getResourceAsStream(ipTableFile);
      InputStreamReader reader = new InputStreamReader(is); 
      BufferedReader br = new BufferedReader(reader); 

      String line = "";

      while ((line = br.readLine()) != null) {
        long ip = ipToLong(line);
        ipTable.addIp(ip);
      }

      br.close();
      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ipTable;

  }

  public static long ipToLong(String ip) {
    String[] ip_bytes = ip.split("\\.");
    return  ((Integer.parseInt(ip_bytes[0]) << 24) |
             (Integer.parseInt(ip_bytes[1]) << 16) |
             (Integer.parseInt(ip_bytes[2]) <<  8) |
             (Integer.parseInt(ip_bytes[3]) <<  0)) & 0x0ffffffffL; 
  }
  

  public void addIp(long ip) {
    if (!table.isSet(ip)) {
      ipNum++;
    }

    table.set(ip);
  }

  public void removeIp(long ip) {
    if (table.isSet(ip)) {
      ipNum--;
    }

    table.clear(ip);
  }

  public boolean hasIp(long ip) {
    return table.isSet(ip);
  }

  public long getValidIpNum() {
    return ipNum;
  }

  
  public static void main(String[] args) {
    IpTable ipTable = IpTable.getIpTable("ip.txt");
    System.out.println("ipTable has valid ip num: "  + ipTable.getValidIpNum());

    String ip = "21.196.12.10";
    if (ipTable.hasIp(IpTable.ipToLong(ip))) {
      System.out.println("ip table has ip: " + ip);
    }
  }
  

}
