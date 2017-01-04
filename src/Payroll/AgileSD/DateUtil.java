package Payroll.AgileSD;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * �ж���ǰ�Ƿ�������
	 * @param date
	 * @return
	 */
	public static boolean isFriday(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}
	/**
	 * �ж������Ƿ�Ϊ��ĩ
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
			   c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	/**
	 * �ж������Ƿ�Ϊ�������һ��
	 * @param date
	 * @return
	 */
	public static boolean isLastDayOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m1 = c.get(Calendar.MONTH);
		
		c.add(Calendar.DAY_OF_MONTH, 1);
		int m2 = c.get(Calendar.MONTH);
		
		return (m1 != m2);
	}
	/**
	 * ��ȡ����ǰdelta�������
	 * @param date
	 * @param delta
	 * @return
	 */
	public static Date before(Date date, int delta){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -delta);
		
		return c.getTime();
	}
	/**
	 * �ж�����first�Ƿ�������second֮ǰ
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isBefore(Date first, Date second){
		return first.getTime() <= second.getTime();
	}
	/**
	 * �ж�����first�Ƿ�������second֮��
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isAfter(Date first, Date second){
		return first.getTime() >= second.getTime();
	}
}
