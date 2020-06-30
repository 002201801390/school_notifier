package br.edu.usf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Set;

public class Schedule {

    private Set<DayOfWeek> daysOfWeek;
    private Time timeIni;
    private Time timeEnd;

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    @JsonIgnore
    public DayOfWeek[] getDaysOfWeekAsArray() {
        return daysOfWeek.toArray(new DayOfWeek[0]);
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Time getTimeIni() {
        return timeIni;
    }

    public void setTimeIni(Time timeIni) {
        this.timeIni = timeIni;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
}
