package com.itxiaox.java.utils.date;

import com.itxiaox.java.utils.data.ConvertUtils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsTest {

    @Test
    public void getNowDateTime() {
        String dateTime = DateUtils.getNowDateTime();
        System.out.println("当前时间：" + dateTime);
    }

    @Test
    public void testGetNowDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateTime = DateUtils.getNowDateTime(format);
        //2020年07月11日 18:20:42
        System.out.println("当前时间：" + dateTime);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateTime2 = DateUtils.getNowDateTime(format2);
        //2020-07-11 18:20:42.232
        System.out.println("当前时间：" + dateTime2);
    }

    @Test
    public void getDate() {
        String date = DateUtils.getDate();
        System.out.println("getDate:" + date);
    }

    @Test
    public void getTime() {
        String time = DateUtils.getTime();
        System.out.println("getTime:" + time);
    }

    @Test
    public void getWeekOfDate() {
        String text1 = DateUtils.getWeekOfDate(new Date());
        System.out.println("getWeekOfDate1:" + text1);
        String[] weekOfDate = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        String text2 = DateUtils.getWeekOfDate(new Date(), weekOfDate);
        System.out.println("getWeekOfDate2:" + text2);
    }

    @Test
    public void getNowTime() {
        String t1 = DateUtils.getNowTime(1);
        System.out.println("t：" + t1);
        String t2 = DateUtils.getNowTime(2);
        System.out.println("t：" + t2);
        String t3 = DateUtils.getNowTime(3);
        System.out.println("t：" + t3);
        String t4 = DateUtils.getNowTime(4);
        System.out.println("t：" + t4);
        String t5 = DateUtils.getNowTime(5);
        System.out.println("t：" + t5);
        String t6 = DateUtils.getNowTime(6);
        System.out.println("t：" + t6);
        String t7 = DateUtils.getNowTime(7);
        System.out.println("t：" + t7);
    }

    @Test
    public void testGetTime() {
        Date date = new Date();
        String t1 = DateUtils.getTime(date, 1);
        System.out.println("t：" + t1);
        String t2 = DateUtils.getTime(date, 2);
        System.out.println("t：" + t2);
        String t3 = DateUtils.getTime(date, 3);
        System.out.println("t：" + t3);
        String t4 = DateUtils.getTime(date, 4);
        System.out.println("t：" + t4);
        String t5 = DateUtils.getTime(date, 5);
        System.out.println("t：" + t5);
        String t6 = DateUtils.getTime(date, 6);
        System.out.println("t：" + t6);
        String t7 = DateUtils.getTime(date, 7);
        System.out.println("t：" + t7);
    }

    @Test
    public void getNowDate() {
        String date2 = DateUtils.getNowDate();
        System.out.println("getNowDate:" + date2);
    }

    @Test
    public void dateToUpCase() {
        String[] texts = DateUtils.dateToUpCase(DateUtils.getNowDateTime());
        System.out.println("dateToUpdate:" + ConvertUtils.arraysToString(texts));
    }

    @Test
    public void getUploadTime() {
        long time = System.currentTimeMillis();
        String uploadTime = DateUtils.getUploadTime(time);
        System.out.println("uploadTime:" + uploadTime);
    }

    @Test
    public void getYearDifference() {
        int longTime = DateUtils.getYearDifference("2017-12-09",
                DateUtils.getDate(), 0);
        System.out.println("longTime:" + longTime);
    }

    @Test
    public void getMonthBetweenDate() {
        int longMonth = DateUtils.getMonthBetweenDate("2017-12-09", DateUtils.getDate());
        System.out.println("longMonth:" + longMonth);
    }

    @Test
    public void timeStampToDate() {
        long timeStamp = System.currentTimeMillis();
        String dateTime = DateUtils.timeStampToDate(timeStamp);
        System.out.println("dateTime:" + dateTime);
    }

    @Test
    public void formatDate() {
        String time = DateUtils.formatDate("yyyy-MM-dd HH:mm", new Date());
        System.out.println("time:" + time);
    }

    @Test
    public void compareDate() {
        int time = DateUtils.compareDate("2018-1-23", DateUtils.getDate());
        System.out.println("time:" + time);
    }

    @Test
    public void compareTime() {
        int time = DateUtils.compareTime("21:5", DateUtils.formatDate("HH:mm", new Date()), "HH:mm");
        System.out.println("time:" + time);
    }

    @Test
    public void ageToDate() {
        Date date = DateUtils.ageToBirthday(30, 0);
        System.out.println("date1:" + DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", date));
        Date date2 = DateUtils.ageToBirthday(30, 1);
        System.out.println("date2:" + DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", date2));
        Date date3 = DateUtils.ageToBirthday(30, 2);
        System.out.println("date3:" + DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", date3));
        Date date4 = DateUtils.ageToBirthday(30, 3);
        System.out.println("date4:" + DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", date4));
    }
}