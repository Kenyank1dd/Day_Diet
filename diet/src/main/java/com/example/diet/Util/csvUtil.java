package com.example.diet.Util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class csvUtil {
    public static void read(String path) throws IOException {

        // 第一参数：读取文件的路径 第二个参数：分隔符（不懂仔细查看引用百度百科的那段话） 第三个参数：字符集
        CsvReader csvReader = new CsvReader(path, ',', StandardCharsets.UTF_8);

        // 如果你的文件没有表头，这行不用执行
        // 这行不要是为了从表头的下一行读，也就是过滤表头
        csvReader.readHeaders();

        // 读取每行的内容
        while (csvReader.readRecord()) {
            // 获取内容的两种方式
            // 1. 通过下标获取
            System.out.print(csvReader.get(0));

            // 2. 通过表头的文字获取
            System.out.println(" " + csvReader.get("年龄"));
        }
    }

    public static void writer() throws IOException {

        // 第一参数：新生成文件的路径 第二个参数：分隔符（不懂仔细查看引用百度百科的那段话） 第三个参数：字符集
        CsvWriter csvWriter = new CsvWriter("F:/demo.csv", ',', StandardCharsets.UTF_8);

        // 表头和内容
        String[]  headers = {"姓名", "年龄", "性别"};
        String[] content = {"张三", "18", "男"};

        // 写表头和内容，因为csv文件中区分没有那么明确，所以都使用同一函数，写成功就行
        csvWriter.writeRecord(headers);
        csvWriter.writeRecord(content);

        // 关闭csvWriter
        csvWriter.close();

    }
}
