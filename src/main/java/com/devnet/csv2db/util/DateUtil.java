package com.devnet.csv2db.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

	public static LocalDate parseDate(String dateStr) {
		DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("M/d/yyyy");
		
		return LocalDate.parse(dateStr,dtfInput);
	}
}
