package com.minsx.schedule;

/**
 * ScheduleException
 * Created by Joker on 2017/7/14.
 */
public class ScheduleException extends Exception {

    private static final long serialVersionUID = -6706060877158153054L;

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }



}
