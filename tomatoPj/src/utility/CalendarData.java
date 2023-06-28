package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CalendarData {
	private LocalDate currentDate;
	
	
	/**
	 * 해당 년도의 월의 달력 출력 메소드
	 * 
	 * @param int 년도, int 월
	 */
	public void printCal(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year); 
		cal.set(Calendar.MONTH, month);
		cal.set(year,month-1,1);
		
		int end = cal.getActualMaximum(Calendar.DATE); //해당 월 마지막 날짜
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); //해당 날짜의 요일 (1:일요일 … 7:토요일)
		
		for(int i=1; i<=end; i++) {
			if(i==1) {
				for(int j=1; j<dayOfWeek; j++) {
					System.out.print("    ");
				}
			}
			if(i<10) { //한자릿수일 경우 공백을 추가해서 줄맞추기
				System.out.print(" ");
			}
			System.out.print(" "+i+" ");
			if(dayOfWeek%7==0) { //한줄에 7일씩 출력
				System.out.println();
			}
			dayOfWeek++;
		}
	}
	
	
	
	
	/**
	 * 현재 날짜 구해서 포맷 적용된 String타입으로 결과 반환 (2023년 06월 28일 수요일)
	 * 
	 * @return String타입의 현재 날짜
	 */
	public String getCurrentDate() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		String month = now.getMonth().toString();
		int monthValue = now.getMonthValue();
		int dayOfMonth = now.getDayOfMonth();
		int dayOfYear = now.getDayOfYear();
		String dayOfWeek = now.getDayOfWeek().toString();
		int dayOfWeekValue = now.getDayOfWeek().getValue();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일");
		String formatedNow = now.format(formatter);

		return formatedNow;
	}

	/**
	 * 현재 날짜를 기준으로 년, 월(문자열, 숫자), 일, 요일, 일(Year 기준) 추출
	 * 
	 * @param String 원하는날짜값 (메소드 내 변수명 참고)
	 * @return String타입의 원하는 날짜값
	 */
	public String getCurrentSelDate(String str) {
		String select = str.toLowerCase();
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		String month = now.getMonth().toString(); 
		int monthValue = now.getMonthValue(); // 달을 숫자로 표현
		int dayOfMonth = now.getDayOfMonth();
		int dayOfYear = now.getDayOfYear();
		String dayOfWeek = now.getDayOfWeek().toString();
		int dayOfWeekValue = now.getDayOfWeek().getValue(); // 요일을 숫자로 표현 (ex. 월-1)
		
		if(select.equals("year")) {
			return String.valueOf(year);
		} else if(select.equals("month")) {
			return month;
		} else if(select.equals("monthvalue")) {
			return String.valueOf(monthValue);
		} else if(select.equals("dayofmonth")) {
			return String.valueOf(dayOfMonth);
		} else if(select.equals("dayofyear")) {
			return String.valueOf(dayOfYear);
		} else if(select.equals("dayofweek")) {
			return dayOfWeek;
		} else {
			return String.valueOf(dayOfWeekValue);
		}
	}
	
	public String selectCurrentDate(LocalDate sel, String str) {
		String select = str.toLowerCase();
		LocalDate now = sel;
		int year = now.getYear();
		String month = now.getMonth().toString(); 
		int monthValue = now.getMonthValue(); // 달을 숫자로 표현
		int dayOfMonth = now.getDayOfMonth();
		int dayOfYear = now.getDayOfYear();
		String dayOfWeek = now.getDayOfWeek().toString();
		int dayOfWeekValue = now.getDayOfWeek().getValue(); // 요일을 숫자로 표현 (ex. 월-1)
		
		if(select.equals("year")) {
			return String.valueOf(year);
		} else if(select.equals("month")) {
			return month;
		} else if(select.equals("monthvalue")) {
			return String.valueOf(monthValue);
		} else if(select.equals("dayofmonth")) {
			return String.valueOf(dayOfMonth);
		} else if(select.equals("dayofyear")) {
			return String.valueOf(dayOfYear);
		} else if(select.equals("dayofweek")) {
			return dayOfWeek;
		} else {
			return String.valueOf(dayOfWeekValue);
		}
	}
}