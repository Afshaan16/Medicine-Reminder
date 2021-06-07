package com.mhss.gomed.other;

public class ReminderTable {

    public static final String TAG = ReminderTable.class.getSimpleName();
    public static final String TABLE = "tblReminder";
    // Labels Table Columns names
    public static final String KEY_ReminderId = "ReminderId";
    public static final String KEY_ReminderName = "ReminderName";
    public static final String KEY_ReminderType = "ReminderType";
    public static final String KEY_ReminderUnit = "ReminderUnit";
    public static final String KEY_OneTimeDose = "OneTimeDose";
    public static final String KEY_TotalDose = "TotalDose";
    public static final String KEY_DoseCount = "DoseCount";
    public static final String KEY_RemainingDose = "RemainingDose";
    public static final String KEY_ReminderStartDate = "ReminderStartDate";
    public static final String KEY_ReminderEndDate = "ReminderEndDate";
    public static final String KEY_ReminderReq = "ReminderReq";

    public static final String CREATE_TABLE_REMINDER = "CREATE TABLE " + TABLE + "("
            + KEY_ReminderId + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ReminderName + " TEXT, "
            + KEY_ReminderType + " TEXT, "
            + KEY_ReminderUnit + " TEXT, "
            + KEY_OneTimeDose + " TEXT, "
            + KEY_TotalDose + " TEXT, "
            + KEY_DoseCount + " TEXT, "
            + KEY_RemainingDose + " TEXT, "
            + KEY_ReminderStartDate + " TEXT, "
            + KEY_ReminderEndDate + " TEXT, "
            + KEY_ReminderReq + " TEXT );";

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
}
