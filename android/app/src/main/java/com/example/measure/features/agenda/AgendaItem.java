package com.example.measure.features.agenda;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * An item to be displayed in the agenda.
 */
public class AgendaItem {
    private String name;
    private Duration timeWorked;

    /**
     * Initialize member variables.
     *
     * @param name       name of the agenda item
     * @param timeWorked amount of time worked on the agenda item
     */
    public AgendaItem(String name, Duration timeWorked) {
        this.name = name;
        this.timeWorked = timeWorked;
    }

    public String getName() {
        return name;
    }

    /**
     * Return the string representation of the time worked.
     *
     * @return HH:MM:SS format of amount of time worked or the empty string
     *         if no time has been worked
     */
    public String getTimeWorked() {
        if (timeWorked == null || timeWorked.getStandardSeconds() == 0) {
            return "";
        }

        Period timeWorkedPeriod = timeWorked.toPeriod();
        PeriodFormatter padHMS = new PeriodFormatterBuilder()
                .minimumPrintedDigits(2)
                .printZeroAlways()
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        return padHMS.print(timeWorkedPeriod);
    }
}
