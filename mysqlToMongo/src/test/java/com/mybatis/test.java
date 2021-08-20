package com.mybatis;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

public class test {

    public static void main(String[] args) throws ParseException {

//        String content = "     您的留言我们已经收到！我们已将\n您反映的问题   转相关部门     进行处理，处理情况我们会及时与您进行沟通。感谢的宝贵留言！";
//        Matcher matcher = Pattern.compile("您(?!(，|。|！))+处理").matcher(reply);
//        matcher.find();
//        System.out.println(matcher.group());
        String label1 = "城建";
        String lacel2 = "投诉";

        String complaintLabel[] = new String[]{label1, lacel2};

        Document document = new Document("complaintLabel", complaintLabel);

        System.out.println(document);



    }
}
