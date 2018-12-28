package com.iceberg;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class HBaseUtil {
    public static boolean createTable(String tableName, String[] cfs) {
        try {
            HBaseAdmin admin = (HBaseAdmin)HBaseConn.getHBaseCoon().getAdmin();
            if (admin.tableExists(tableName)) {
                return false;
            }
            final HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
//            for (String cf: cfs) {
//                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
//                hColumnDescriptor.setMaxVersions(1);
//                hTableDescriptor.addFamily(hColumnDescriptor);
//            }
            Arrays.stream(cfs).forEach(cf -> {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
                hColumnDescriptor.setMaxVersions(1);
                hTableDescriptor.addFamily(hColumnDescriptor);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteTable(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin)HBaseConn.getHBaseCoon().getAdmin()){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean putRow(String tableName, String rowKey, String cfName, String qualifier, String data) {
        try (Table table = HBaseConn.getTable(tableName)){
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier), Bytes.toBytes(data));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean putRows(String tableName, List<Put> puts) {
        try (Table table = HBaseConn.getTable(tableName)){
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Result getRow(String tableName, String rowKey) {
        try (Table table = HBaseConn.getTable(tableName)){
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result getRow(String tableName, String rowKey, FilterList filterList) {
        try (Table table = HBaseConn.getTable(tableName)){
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultScanner getScanner(String tableName) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setCaching(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultScanner getScanner(String tableName, String startRowKey, String stopRowKey) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRowKey));
            scan.setStopRow(Bytes.toBytes(stopRowKey));
            scan.setCaching(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultScanner getScanner(String tableName, String startRowKey, String stopRowKey, FilterList filterList) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRowKey));
            scan.setStopRow(Bytes.toBytes(stopRowKey));
            scan.setFilter(filterList);
            scan.setCaching(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteRow(String tableName, String rowKey) {
        try (Table table = HBaseConn.getTable(tableName)) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
