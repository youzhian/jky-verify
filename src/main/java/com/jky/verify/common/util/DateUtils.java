package com.jky.verify.common.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 *
 * @author Mark sunlightcs@gmail.com
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }
    /**
     * 根据日期获取当前日期所在月份的最大天数
     * @param date 日期
     * @return 日期所在月份最大天数
     */
    public static int getMaxDayOnMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DATE);
        return maxDay;
    }
    /**
     * 根据当前日期获取当前日期所在月的最后一天
     * @param date
     * @param type 类型，0为最小一天，1为最大一天
     * @return
     */
    public static Date getMaxOrMinDateOnMonth(Date date,int type){
        if(date != null){

            int day = -1;

            Calendar c = Calendar.getInstance();
            c.setTime(date);

            if(type == 0){
                day = c.getActualMinimum(Calendar.DATE);
            }else if(type == 1){
                day = c.getActualMaximum(Calendar.DATE);
            }else{
                //不符合要求
                return null;
            }

            c.set(Calendar.DAY_OF_MONTH,day);

            return c.getTime();
        }
        return null;
    }

    /**
     * 根据日期判断是否为周末（周五、周六）
     * @param date 欲判断的日期
     * @return
     */
    public static boolean isWeekEnd(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //得到今天是周几，一周从周日开始
        int wd = c.get(Calendar.DAY_OF_WEEK);
        //大于5,即为周末
        if(wd > 5){
            return true;
        }
        return false;
    }

    /**
     * 获取当前日期，格式yyyy-MM-dd
     * @return 返回当天日期，格式yyyy-MM-dd
     */
    public static Date getToDay(){

        return stringToDate(format(new Date()),DATE_PATTERN);
    }

    /**
     * 获取最小的时间 lcs
     * @param field：Calendar.DAY_OF_MONTH 获取一天中最小的时间
     */
    public static Date getMinDate(Date date, int field)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.get(Calendar.DAY_OF_MONTH);
        switch (field){
            case Calendar.YEAR :
                cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
            case Calendar.MONTH :
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            case Calendar.DAY_OF_MONTH :
                cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
            case Calendar.HOUR_OF_DAY :
                cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
            case Calendar.MINUTE :
                cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
            case Calendar.SECOND :
                cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        }
        return cal.getTime();
    }
    /**
     * 获取最大的时间 lcs
     * @param field：Calendar.DAY_OF_MONTH 获取一天中最大的时间
     */
    public static Date getMaxDate(Date date,int field)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.get(Calendar.DAY_OF_MONTH);
        switch (field){
            case Calendar.YEAR :
                cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
            case Calendar.MONTH :
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
            case Calendar.DAY_OF_MONTH :
                cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
            case Calendar.HOUR_OF_DAY :
                cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
            case Calendar.MINUTE :
                cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
            case Calendar.SECOND :
                cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
        }
        return cal.getTime();
    }

    /**
     * 检查字符串是否为日期格式 yyyy-MM-dd
     * @param dateStr 要检查的字符串
     * @return true表示符合
     */
    public static boolean checkDate(String dateStr){
        if(StringUtils.isNotBlank(dateStr)){
            try {
                Date date = DateUtils.stringToDate(dateStr, DateUtils.DATE_PATTERN);
                Date maxDate = DateUtils.getMaxOrMinDateOnMonth(date,1);
                Date minDate = DateUtils.getMaxOrMinDateOnMonth(date,0);
                if(minDate.compareTo(date)<1 && maxDate.compareTo(date)>-1){
                    return true;
                }
            }catch (Exception e){
                return false;
            }
        }

        return false;
    }

    /**
     * 检查是否是月份格式，yyyy-MM
     * @param monthStr 要检查的字符串
     * @return true表示符合
     */
    public static boolean checkMonth(String monthStr){
        //判断是否符合月份格式
        if(StringUtils.isNotBlank(monthStr) && monthStr.contains("-")){
            String [] s = monthStr.split("-");
            if(s.length == 2 && s[0].length() == 4 && s[1].length() == 2
                    && StringUtils.isNumeric(s[0]) && StringUtils.isNumeric(s[1])){
                int m = Integer.valueOf(s[1]);
                int year = Integer.valueOf(s[0]);
                if(year > 0 && 0 < m && m < 13){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 比较source是否大于target
     * @param source 要进行比较的日期
     * @param target 要比较的日期 ，若target为null，而source不为空，则返回true
     * @return
     */
    public static boolean isGt(Date source, Date target){
        if(source != null){
            if(target != null){
                return source.compareTo(target) > 0;
            }
            return true;
        }
        return false;
    }

    /**
     * 比较source的年月是否大于target的年月
     * @param source 要进行比较的日期
     * @param target 要比较的日期
     * @return false 表示不大于，true表示大于
     */
    public static boolean isGtByMonth(Date source, Date target){
        if(source != null && target != null){
            String s1 = DateUtils.format(source,"yyyy-MM");
            String s2 = DateUtils.format(target,"yyyy-MM");
            return s1.compareTo(s2) > 0;
        }
        return false;
    }
    public static void main(String[] args) {
        Date d1 = DateUtils.stringToDate("2021-07-11","yyyy-MM-dd");
        Date d2 = DateUtils.stringToDate("2021-05-21","yyyy-MM-dd");
        System.out.println(DateUtils.isGtByMonth(d1,d2));
        System.out.println(DateUtils.format(new Date()));
    }
}
