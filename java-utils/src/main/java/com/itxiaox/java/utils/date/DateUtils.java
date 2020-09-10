package com.itxiaox.java.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 常用时间日期工具类
 * test ok
 *<p>
 * </br>
 * 默认的时间格式为:
 * </br>
 * format：yyyy-MM-dd HH:mm:ss
 * </br>
 * ex: 2015-7-6 11:02:09
 * <p>
 <p>java格式化字母表</p>

 <table border="1" summary="date format symbols"><tbody><tr><th id="h101">Symbol</th>
 <th id="h102">Meaning</th>
 <th id="h103">Presentation</th>
 <th id="h104">Example</th>
 </tr><tr><td>G</td>
 <td>era designator</td>
 <td>Text</td>
 <td>AD</td>
 </tr><tr><td>y</td>
 <td>year</td>
 <td>Number</td>
 <td>2009</td>
 </tr><tr><td>M</td>
 <td>month in year</td>
 <td>Text &amp; Number</td>
 <td>July &amp; 07</td>
 </tr><tr><td>d</td>
 <td>day in month</td>
 <td>Number</td>
 <td>10</td>
 </tr><tr><td>h</td>
 <td>hour in am/pm (1-12)</td>
 <td>Number</td>
 <td>12</td>
 </tr><tr><td>H</td>
 <td>hour in day (0-23)</td>
 <td>Number</td>
 <td>0</td>
 </tr><tr><td>m</td>
 <td>minute in hour</td>
 <td>Number</td>
 <td>30</td>
 </tr><tr><td>s</td>
 <td>second in minute</td>
 <td>Number</td>
 <td>55</td>
 </tr><tr><td>S</td>
 <td>millisecond</td>
 <td>Number</td>
 <td>978</td>
 </tr><tr><td>E</td>
 <td>day in week</td>
 <td>Text</td>
 <td>Tuesday</td>
 </tr><tr><td>D</td>
 <td>day in year</td>
 <td>Number</td>
 <td>189</td>
 </tr><tr><td>F</td>
 <td>day of week in month</td>
 <td>Number</td>
 <td>2 (2nd Wed in July)</td>
 </tr><tr><td>w</td>
 <td>week in year</td>
 <td>Number</td>
 <td>27</td>
 </tr><tr><td>W</td>
 <td>week in month</td>
 <td>Number</td>
 <td>2</td>
 </tr><tr><td>a</td>
 <td>am/pm marker</td>
 <td>Text</td>
 <td>PM</td>
 </tr><tr><td>k</td>
 <td>hour in day (1-24)</td>
 <td>Number</td>
 <td>24</td>
 </tr><tr><td>K</td>
 <td>hour in am/pm (0-11)</td>
 <td>Number</td>
 <td>0</td>
 </tr><tr><td>z</td>
 <td>time zone</td>
 <td>Text</td>
 <td>Pacific Standard Time</td>
 </tr><tr><td>'</td>
 <td>escape for text</td>
 <td>Delimiter</td>
 <td>(none)</td>
 </tr><tr><td>'</td>
 <td>single quote</td>
 <td>Literal</td>
 <td>'</td>
 </tr></tbody></table>
 * </p>
 *</p>
 * @author xiaoxiao
 * @ClassName: DateUtils
 * @Description: TODO
 * @date modify by 2015-7-6 上午11:02:09
 */
public class DateUtils {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * 获取当前的时间，包含日期和时间
     * 格式为: yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前时间
     * ex:2015-07-11 18:17:48
     */
    public static String getNowDateTime() {
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 根据传入的format格式获取当前时间
     * <p>
     * ex:
     * SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());</br>
     * format:yyyy年MM月dd日 HH:mm:ss ——> 2015年07月11日 18:20:42 </br>
     * format:yyyy-MM-dd HH:mm:ss.SSS (带毫秒数) ——> 2015-07-11 18:23:52.242
     * </p>
     *
     * @param format 时间格式</br>
     * @return
     */
    public static String getNowDateTime(SimpleDateFormat format) {
        if (format == null) {
            throw new NullPointerException("format is null");
        }
        return format.format(new Date());
    }

    /**
     * 获取当前日期
     * @return 返回当前时间
     * ex:2020-7-11
     */
    public static String getDate() {
        return DateFormat.getDateInstance().format(new Date());
    }

    /**
     * 获取当前日期 不包含时间，格式为 yyyy-MM-dd
     * @return 返回日期
     * ex:2020-07-11
     */
    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获取当前时间(仅时间)
     * 格式HH:mm:ss
     * @return ex:19:24:33
     */
    public static String getTime() {
        return DateFormat.getTimeInstance().format(new Date());
    }

    /**
     * 获取当前是周几
     * 默认： String[] weekDays  = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
     * @param dt
     * @return 当前周几
     * </br>
     * ex: 周一
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays  = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 获取当前日期是周几
     * @param dt 日期
     * @param weekDays 星期数组 </br>
     *                ex: String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
     * @return 当前日期是周几
     */
    public static String getWeekOfDate(Date dt,String[] weekDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前时间
     *
     * @param format 显示时间类型格式:
     *               </br>
     *               1: 2016-5-4 11:42:32
     *               </br>
     *               2: 2012-10-6
     *               </br>
     *               3: 12:36:35
     *               </br>
     *               4: 12-10-6 下午12:36
     *               </br>
     *               5: 20121006
     *               </br>
     *               6：2012-10-06
     *               </br>
     *               7: 2016-05-04 11:50:37
     * @return 当前时间
     */
    public static String getNowTime(int format) {
        String year = "";
        String month = "";
        String day = "";
        Date now = new Date();
        DateFormat d = null;
        String str = null;
        switch (format) {
            case 1:
                d = DateFormat.getDateTimeInstance();// 日期时间
                str = d.format(now);
                break;
            case 2:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(now);
                break;
            case 3:
                d = DateFormat.getTimeInstance();// 时间
                str = d.format(now);
                break;
            case 4:
                d = DateFormat.getInstance(); // 使用SHORT风格显示日期和时间
                str = d.format(now);
                break;
            case 5:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(now);
                year = str.split("-")[0];
                month = "0" + str.split("-")[1];
                day = "0" + str.split("-")[2];

                str = year + month.substring(month.length() - 2)
                        + day.substring(day.length() - 2);
                break;
            case 6:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(now);
                year = str.split("-")[0];
                month = "0" + str.split("-")[1];
                day = "0" + str.split("-")[2];

                str = year + "-" + month.substring(month.length() - 2) + "-"
                        + day.substring(day.length() - 2);
                break;
            case 7:
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.getDefault());
                str = format2.format(now);
            default:
                break;
        }
        return str;
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param format 1:2012-10-6 12:36:35
     *                <p>
     *                2:2012-10-6
     *                <p>
     *                3:12:36:35
     *                <p>
     *                4:12-10-6 下午12:36
     *                <p>
     *                5:20121006
     *                <p>
     *                6:2012-10-06
     * @return String
     */
    public static String getTime(Date date, int format) {
        String year = "";
        String month = "";
        String day = "";
        DateFormat d = null;
        String str = null;
        switch (format) {
            case 1:
                d = DateFormat.getDateTimeInstance();// 日期时间
                str = d.format(date);
                break;
            case 2:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(date);
                break;
            case 3:
                d = DateFormat.getTimeInstance();// 时间
                str = d.format(date);
                break;
            case 4:
                d = DateFormat.getInstance(); // 使用SHORT风格显示日期和时间
                str = d.format(date);
                break;
            case 5:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(date);
                year = str.split("-")[0];
                month = "0" + str.split("-")[1];
                day = "0" + str.split("-")[2];

                str = year + month.substring(month.length() - 2)
                        + day.substring(day.length() - 2);
                break;
            case 6:
                d = DateFormat.getDateInstance(); // 日期
                str = d.format(date);
                year = str.split("-")[0];
                month = "0" + str.split("-")[1];
                day = "0" + str.split("-")[2];

                str = year + "-" + month.substring(month.length() - 2) + "-"
                        + day.substring(day.length() - 2);
                break;
            case 7:
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.getDefault());
                str = format2.format(date);
            default:
                break;
        }
        return str;
    }

    /**
     * 解析日期为大写
     * 月份大写
     * @param str
     * @return 返回日期数组</br>
     * ex:[2020,七月,11,20:07:29]
     */
    public static String[] dateToUpCase(String str) {

        String[] strs = null;
        String[] split = null;
        String[] strings = new String[4];
        try {
            split = str.split(" ");
            strs = split[0].split("-");
            if (strs.length == 3) {
                int m = Integer.parseInt(strs[1]);
                if (m == 1) {
                    strs[1] = "一月";
                } else if (m == 2) {
                    strs[1] = "二月";
                } else if (m == 3) {
                    strs[1] = "三月";
                } else if (m == 4) {
                    strs[1] = "四月";
                } else if (m == 5) {
                    strs[1] = "五月";
                } else if (m == 6) {
                    strs[1] = "六月";
                } else if (m == 7) {
                    strs[1] = "七月";
                } else if (m == 8) {
                    strs[1] = "八月";
                } else if (m == 9) {
                    strs[1] = "九月";
                } else if (m == 10) {
                    strs[1] = "十月";
                } else if (m == 11) {
                    strs[1] = "十一";
                } else if (m == 12) {
                    strs[1] = "十二";
                }
            }
            strings[0] = strs[0];
            strings[1] = strs[1];
            strings[2] = strs[2];
            strings[3] = split[1];
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * 计算上传时间的
     * 时间：
     * ex:created = System.currentTimeMillis()
     * @param created
     * @return 返回距离created时间之前</br>
     * ex: 9秒前、1分钟前
     */
    public static String getUploadTime(long created) {
        StringBuffer when = new StringBuffer();
        int difference_seconds;
        int difference_minutes;
        int difference_hours;
        int difference_days;
        int difference_months;
        long curTime = System.currentTimeMillis();
        difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
        if (difference_months > 0) {
            when.append(difference_months + "月");
        }

        difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
        if (difference_days > 0) {
            when.append(difference_days + "天");
        }

        difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
        if (difference_hours > 0) {
            when.append(difference_hours + "小时");
        }

        difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
        if (difference_minutes > 0) {
            when.append(difference_minutes + "分钟");
        }

        difference_seconds = (int) ((curTime % 60) - (created % 60));
        if (difference_seconds > 0) {
            when.append(difference_seconds + "秒");
        }

        return when.append("前").toString();
    }

    /**
     * 是否为日期
     *
     * @param str
     * @param format 0:YYYY-MM-DD
     * @param format 1:YYYY/MM/DD
     * @return
     */
    private static boolean isDate(String str, int format) {
        boolean b = true;
        String[] strs = null;
        try {
            if (format == 0) {
                strs = str.split("-");
            }
            if (format == 1) {
                strs = str.split("/");
            }
            if (strs.length == 3) {
                int y = Integer.parseInt(strs[0]);
                int m = Integer.parseInt(strs[1]);
                int d = Integer.parseInt(strs[2]);
                if (y < 1600 || y > 9999) {
                    return false;
                }
                if (m < 1 || m > 12) {
                    return false;
                }
                if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10
                        || m == 12) {
                    if (d < 1 || d > 31) {
                        return false;
                    }
                }
                if (m == 4 || m == 6 || m == 9 || m == 11) {
                    if (d < 1 || d > 30) {
                        return false;
                    }
                }
                if (m == 2) {
                    if ((y % 4 == 0 && y % 100 > 0) || y % 400 == 0) {
                        if (d < 1 || d > 29) {
                            return false;
                        }
                    } else {
                        if (d < 1 || d > 28) {
                            return false;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            b = false;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 计算相隔年份
     * 计算相隔年份，向下取整，不足年则为0
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param format   0:YYYY-MM-DD
     * @param format   1:YYYY/MM/DD
     * @return
     */
    public static int getYearDifference(String startDate, String endDate,
                                        int format) {
        int year = -1;
        String[] strs1 = null;
        String[] strs2 = null;
        try {
            if (isDate(startDate, format) && isDate(endDate, format)) {
                if (format == 0) {
                    strs1 = startDate.split("-");
                    strs2 = endDate.split("-");
                }
                if (format == 1) {
                    strs1 = startDate.split("/");
                    strs2 = endDate.split("/");
                }
                int y1 = Integer.parseInt(strs1[0]);
                int m1 = Integer.parseInt(strs1[1]);
                int d1 = Integer.parseInt(strs1[2]);

                int y2 = Integer.parseInt(strs2[0]);
                int m2 = Integer.parseInt(strs2[1]);
                int d2 = Integer.parseInt(strs2[2]);

                year = y2 - y1;
                if (m2 < m1) {
                    year = year - 1;
                } else if (m2 == m1) {
                    if (d2 < d1) {
                        year = year - 1;
                    }
                }
            }
        } catch (NumberFormatException e) {
            year = -1;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return year;
    }

    /**
     * 计算两个月之间的相差的月份
     *
     * @param date1 开始月份
     * @param date2 结束月份
     * @return months 相隔月份
     */
    @SuppressWarnings({"deprecation", "unused"})
    public static Integer getMonthBetweenDate(String date1, String date2) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date d1 = null;
        try {
            d1 = sd.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sd.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int months = 0;// 相差月份
        int y1 = d1.getYear();
        int y2 = d2.getYear();
        int dm1 = d2.getMonth();// 起始日期月份
        int dm2 = d2.getMonth();// 结束日期月份
        int dd1 = d1.getDate(); // 起始日期天
        int dd2 = d2.getDate(); // 结束日期天
        if (d1.getTime() < d2.getTime()) {
            months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
            if (dd2 < dd1) {
                months = months - 1;
            }
        }
        return months;
    }

    /**
     * 将时间戳转换成普通日期型的函数
     * </br>
     * @param timestamp 单位ms
     * @return
     */
    public static String timeStampToDate(Long timestamp) {
//        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date(timestamp));
    }

    /**
     * 格式化日期
     * @param format 日期格式
     * @param date 要格式的日期
     * @return 返回格式化后的时间字符串
     */
    public static String formatDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 日期大小比较:
     * </br>
     * 1 : date1 > date2
     * </br>
     * 0 : date1 = date2
     * </br>
     * -1: date1 < date2
     * @param date1
     * @param date2
     * @return 返回比较结果：
     * </br>
     * 1 : date1 > date2
     * </br>
     * 0 : date1 = date2
     * </br>
     * -1: date1 < date2
     */
    public static int compareDate(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间大小比较</br>
     * 1 :time1>time2</br>
     * 0 :time1=time2</br>
     * -1:time1<time2</br>
     * @param time1
     * @param time2
     * @param format 时间格式
     * @return
     * </br>
     * 1 :time1>time2</br>
     * 0 :time1=time2</br>
     * -1:time1<time2
     */
    public static int compareTime(String time1, String time2,String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(time1);
            Date dt2 = df.parse(time2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 将年龄转为出生日期
     * @param age  年龄
     * @param unit 单位</br>0:岁</br>1:月</br>2:周</br>3:天
     * @return
     */
    public static Date ageToBirthday(int age, int unit) {
        if (age == 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (unit == 0) {//岁
            calendar.add(Calendar.YEAR, -age);
        } else if (unit == 1) {
            calendar.add(Calendar.MONTH, -age);
        } else if (unit == 2) {
            calendar.add(Calendar.WEEK_OF_MONTH, -age);
        } else if (unit == 3) {
            calendar.add(Calendar.DATE, -age);
        }
        return calendar.getTime();
    }
}
