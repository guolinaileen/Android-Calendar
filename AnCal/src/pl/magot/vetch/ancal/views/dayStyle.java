
package pl.magot.vetch.ancal.views;


import java.util.*;


public class dayStyle
{
	//methods
	private static String[] getWeekDayNames()
	{
		String[] vec = new String[10];
		vec[Calendar.SUNDAY] = "Sun";
		vec[Calendar.MONDAY] = "Mon";
		vec[Calendar.TUESDAY] = "Tue";
		vec[Calendar.WEDNESDAY] = "Wed";
		vec[Calendar.THURSDAY] = "Thu";
		vec[Calendar.FRIDAY] = "Fri";
		vec[Calendar.SATURDAY] = "Sat";		
		return vec;		
	}
	
	public static String getWeekDayName(int iDay)
	{
		return vecStrWeekDayNames[iDay];
	}
	
	//fields
	private final static String[] vecStrWeekDayNames = getWeekDayNames();

	//fields
	public final static int iColorFrame = 0xff4169E1;//week header weekday
	public final static int iColorFrameHoliday = 0xff6495ED;//header sat&sun
	public final static int iColorFrameToday = 0xff6495ED;

	public final static int iColorText = 0xff006400;//
	public final static int iColorTextHoliday = 0xfff0f0f0;
	public final static int iColorTextToday = 0xff002200;
	
	public final static int iColorTextHeader = 0xffcccccc;
	public final static int iColorTextHeaderHoliday = 0xffd0d0d0;
	public final static int iColorTextHeaderToday = 0xff002200;

	public final static int iColorTextHeaderLight = 0xffdddddd;
	public final static int iColorTextHeaderLightHoliday = 0xffe0e0e0;
	public final static int iColorTextHeaderLightToday = 0xff003300;
	
	public final static int iColorBkg = 0xffE0FFFF;//block background weekday
	public final static int iColorBkgHoliday = 0xffccddee;//block
	public final static int iColorBkgToday = 0xff87CEEB;//block
	
	public final static int iColorBkgFocusLight = 0xffffddbb;
	public final static int iColorBkgFocusDark = 0xffaa5500;
		
	public final static int iColorTextHour = 0xff8B0000;//FF1177BB;//weekview no
	public final static int iColorTextWeek = 0xFF8B0000;//month view week no
	
	public final static int iColorTimeItem = 0xff555555;
	public final static int iColorTimeItemFocus = 0xFF664422;

	public final static int iColorTimeItemBkg = 0xffeeeeee;
	public final static int iColorTimeItemBkgFocus = 0xFFaa5500;
		
	public final static int iColorTextFocused = 0xff221100;
	
	//methods
	public static int getColorTimeItem(boolean bFocused)
	{	
		return bFocused?iColorTimeItemFocus:iColorTimeItem;
	}

	public static int getColorTimeItemBkg(boolean bFocused)
	{	
		return bFocused?iColorTimeItemBkgFocus:iColorTimeItemBkg;
	}
	
	public static int getColorFrame(boolean bHoliday, boolean bToday)
	{
		if (bToday)
			return iColorFrameToday;
		if (bHoliday)
			return iColorFrameHoliday;
		return iColorFrame;
	}
	
	public static int getColorTextHeader(boolean bHoliday, boolean bToday)
	{
		if (bToday)
			return iColorTextHeaderToday;
		if (bHoliday)
			return iColorTextHeaderHoliday;
		return iColorTextHeader;
	}

	public static int getColorText(boolean bHoliday, boolean bToday)
	{
		if (bToday)
			return iColorTextToday;
		if (bHoliday)
			return iColorTextHoliday;
		return iColorText;
	}
	
	public static int getColorTextHeaderLight(boolean bHoliday, boolean bToday)
	{
		if (bToday)
			return iColorTextHeaderLightToday;
		if (bHoliday)
			return iColorTextHeaderLightHoliday;
		return iColorTextHeaderLight;
	}
	
	public static int getColorBkg(boolean bHoliday, boolean bToday)
	{
		if (bToday)
			return iColorBkgToday;
		if (bHoliday)
			return iColorBkgHoliday;
		return iColorBkg;
	}
		
}
