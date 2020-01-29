package com.newsreader.article.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    public static Date convertStringToDate(String stringifiedDate) {

        if (stringifiedDate == null) {
            return null;
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stringifiedDate);
        } catch (ParseException e) {
            LOG.error("exception {} parsing the date: {}", e.getMessage(), stringifiedDate);
        }
        return date;
    }
}
