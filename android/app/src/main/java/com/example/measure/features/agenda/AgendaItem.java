package com.example.measure.models.data;

import android.util.Log;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * An item to be displayed in the agenda.
 */
public class AgendaItem {
    private String name;
    private String timeWorked;

    /**
     * Initialize member variables.
     *
     * @param name       name of the agenda item
     * @param timeWorked amount of time worked on the agenda item
     */
    public AgendaItem(String name, Duration timeWorked) {
        this.name = name;

        Period timeWorkedPeriod = timeWorked.toPeriod();
        Log.d("AGENDAITEM", timeWorked.getStandardSeconds() + "");
        PeriodFormatter padHMS = new PeriodFormatterBuilder()
                .minimumPrintedDigits(2)
                .printZeroAlways()
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        this.timeWorked = padHMS.print(timeWorkedPeriod);
    }

    public String getName() {
        return name;
    }

    public String getTimeWorked() {
        return timeWorked;
    }
}
