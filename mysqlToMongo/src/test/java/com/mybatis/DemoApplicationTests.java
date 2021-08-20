package com.mybatis;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import com.mybatis.model.wj_complaints_01;
import com.mybatis.service.Wj_complaints_01ServiceImpl;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private Wj_complaints_01ServiceImpl wj_complaints_01Service;

    //计算：uniqid
    public String hashcode(String oldPath) throws Exception {
        long h = 0;
        String encode = URLEncoder.encode(oldPath, "UTF-8");
        for (int i = 0; i < encode.length(); i++) {
            char c = encode.charAt(i);
            int a = c;
            h = 31 * h + a;
        }
        long hash_code = ((h + 0x8000000000000000L) & 0xFFFFFFFFFFFFFFFFL) - 0x8000000000000000L;
        return String.valueOf(hash_code);
    }

    @Test
    void contextLoads() {
        //连接mongodb
        MongoClient mongoClient = new MongoClientImpl(MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://root:Jiacheng_1995@mongodb.dcpro.jcinfo.com:32197/admin?")).build(), null);
        MongoDatabase database = mongoClient.getDatabase("weijian");
        MongoCollection<Document> collection = database.getCollection("odl_wj_Complaints_yz");

        //获取MySQL数据
        List<wj_complaints_01> list = wj_complaints_01Service.showAll();
        int size = list.size();
        AtomicInteger success_count = new AtomicInteger();

        list.forEach(wj_complaints_01 -> {
            try {
                //punishDate, punishDate_timestamp
                String punishDate = null;
                long punishDate_timestamp = 0;
                if (wj_complaints_01.getPunishdate() != null) {
                    Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(wj_complaints_01.getPunishdate());
                    matcher.find();
                    punishDate = matcher.group();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(punishDate);
                    punishDate_timestamp = date.getTime();
                }
                //content_noTag
                String content_noTag = wj_complaints_01.getContent();
                if (content_noTag.length() != 0 && content_noTag != null) {
                    content_noTag = content_noTag.replace("\n", "");
                    content_noTag = content_noTag.replace(" ", "");
                }

                Document Doc;
                if (wj_complaints_01.getNumber() == null) {
                    //reply
                    String reply = wj_complaints_01.getReply();
                    if (reply.length() != 0 && reply != null) {
                        Matcher matcher = Pattern.compile("\\S(.*)").matcher(reply);
                        matcher.find();
                        reply = matcher.group();
                    }
                    //replyTime
                    String replyTime = wj_complaints_01.getReplytime();
                    if (replyTime.length() != 0 && replyTime != null) {
                        Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(replyTime);
                        matcher.find();
                        replyTime = matcher.group();
                    }
                    //complaintLabel
                    String label1 = wj_complaints_01.getLabel1();
                    String lacel2 = wj_complaints_01.getLabel2();
                    HashSet<String> set = new HashSet<>();
                    set.add(label1);
                    set.add(lacel2);


                    Doc = new Document("status", wj_complaints_01.getStatus())
                            .append("sourceUrl", wj_complaints_01.getSourceurl())
                            .append("title", wj_complaints_01.getTitle())
                            .append("content", wj_complaints_01.getContent())
                            .append("content_noTag", content_noTag)
                            .append("punishDate", punishDate)
                            .append("reply", reply)
                            .append("complaintLabel",set)
                            .append("respondent", wj_complaints_01.getRespondent())
                            .append("replyTime", replyTime)
                            .append("siteName", "领导留言板")
                            .append("cityCode", 320000)
                            .append("caseAddrDistrict", "320000")
                            .append("gatherTime", System.currentTimeMillis())
                            .append("punishDate_timestamp", punishDate_timestamp);
                } else {
                    Doc = new Document("status", wj_complaints_01.getStatus())
                            .append("sourceUrl", wj_complaints_01.getSourceurl())
                            .append("title", wj_complaints_01.getTitle())
                            .append("content", wj_complaints_01.getContent())
                            .append("content_noTag", content_noTag)
                            .append("punishDate", wj_complaints_01.getPunishdate())
                            .append("reply", wj_complaints_01.getReply())
                            .append("number", wj_complaints_01.getNumber())
                            .append("letterType", wj_complaints_01.getLettertype())
                            .append("siteName", "12345")
                            .append("cityCode", 320200)
                            .append("caseAddrDistrict", "320200")
                            .append("gatherTime", System.currentTimeMillis())
                            .append("punishDate_timestamp", punishDate_timestamp);
                }

                //uniqid
                String path = wj_complaints_01.getTitle() + wj_complaints_01.getNumber();
                String uniqid = hashcode(path);

                //createDate
                long createDate = System.currentTimeMillis();

                Document newDoc = new Document("uniqid", uniqid)
                        .append("createDate", createDate)
                        .append("data", Doc);

                collection.insertOne(newDoc);
                success_count.getAndIncrement();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("本次从MySQL获取的增量数据为----- " + size + "条");
        System.out.println("成功导入Mongo----- " + success_count + "条");

    }
}
