package com.mhss.gomed.other;

import java.io.Serializable;
import java.util.List;

public class ReminderDTO implements Serializable {

    private int reminderId;
    private String reminderName;
    private String reminderType;
    private String reminderUnit;
    private String oneTimeDose;
    private String totalDose;
    private String doseCount;
    private String remainingDose;
    private String startDate;
    private String endDate;
    private String isReq;
    private String percent;
    private List<ReminderTimeTable> list;

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public String getReminderUnit() {
        return reminderUnit;
    }

    public void setReminderUnit(String reminderUnit) {
        this.reminderUnit = reminderUnit;
    }

    public String getOneTimeDose() {
        return oneTimeDose;
    }

    public void setOneTimeDose(String oneTimeDose) {
        this.oneTimeDose = oneTimeDose;
    }

    public String getTotalDose() {
        return totalDose;
    }

    public void setTotalDose(String totalDose) {
        this.totalDose = totalDose;
    }

    public String getRemainingDose() {
        return remainingDose;
    }

    public void setRemainingDose(String remainingDose) {
        this.remainingDose = remainingDose;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIsReq() {
        return isReq;
    }

    public void setIsReq(String isReq) {
        this.isReq = isReq;
    }

    public String getDoseCount() {
        return doseCount;
    }

    public void setDoseCount(String doseCount) {
        this.doseCount = doseCount;
    }

    public List<ReminderTimeTable> getList() {
        return list;
    }

    public void setList(List<ReminderTimeTable> list) {
        this.list = list;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
