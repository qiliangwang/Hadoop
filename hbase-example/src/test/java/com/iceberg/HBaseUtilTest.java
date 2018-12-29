package com.iceberg;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.Arrays;


public class HBaseUtilTest {

    @Test
    public void createTable() {
        boolean result = HBaseUtil.createTable("FileTable", new String[]{"fileInfo", "saveInfo"});
        System.out.println(result);
    }

    @Test
    public void deleteTable() {
        HBaseUtil.deleteTable("TestTable");
    }

    @Test
    public void putRow() {
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "name", "file1.txt");
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "size", "1024");
        HBaseUtil.putRow("FileTable", "rowkey1", "saveInfo", "creator", "superman");

        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "name", "file2.txt");
        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "size", "2048");
        HBaseUtil.putRow("FileTable", "rowkey2", "saveInfo", "creator", "batman");

        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "name", "file3.txt");
        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "type", "txt");
        HBaseUtil.putRow("FileTable", "rowkey3", "fileInfo", "size", "3072");
        HBaseUtil.putRow("FileTable", "rowkey3", "saveInfo", "creator", "wonder woman");
    }

    @Test
    public void putRows() {
    }

    @Test
    public void getRow() {
        Result result = HBaseUtil.getRow("FileTable", "rowkey1");
        if (result != null) {
            System.out.println("rowkey=" + Bytes.toString(result.getRow()));
            System.out.println("rowkey=" + Bytes.toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
        }
    }

    @Test
    public void getRow1() {
    }

    @Test
    public void getScanner() {
        ResultScanner scanner = HBaseUtil.getScanner("FileTable");
        printScannerResult(scanner);
    }

    private void printScannerResult(ResultScanner scanner) {
        if (scanner != null) {
            scanner.forEach(result -> {
                System.out.println("rowkey=" + Bytes.toString(result.getRow()));
                System.out.println("rowkey=" + Bytes.toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
            });
            scanner.close();
        }else {
            System.out.println("None data found");
        }
    }
    
    @Test
    public void getScanner1() {
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("rowkey1")));

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(filter));

        ResultScanner scanner = HBaseUtil.getScanner("FileTable", "rowkey1", "rowkey2", filterList);

        printScannerResult(scanner);

    }

    @Test
    public void getScanner2() {
    }

    @Test
    public void deleteRow() {
        HBaseUtil.deleteRow("FileTable", "rowkey1");
    }

    @Test
    public void deleteColumnFamily() {
    }

    @Test
    public void deleteQualifier() {
    }
}