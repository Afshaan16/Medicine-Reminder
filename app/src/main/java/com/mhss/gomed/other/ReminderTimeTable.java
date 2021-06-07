package com.mhss.gomed.other;

public class ReminderTimeTable {

    public static final String TAG = ReminderTimeTable.class.getSimpleName();
    public static final String TABLE = "tblReminderTime";
    // Labels Table Columns names
    public static final String KEY_ReminderTimeId = "ReminderTimeId";
    public static final String KEY_ReminderId = "ReminderId";
    public static final String KEY_ReminderTime = "ReminderTime";

    public static final String CREATE_TABLE_REMINDER_TIME = "CREATE TABLE " + TABLE + "("
            + KEY_ReminderTimeId + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ReminderId + " TEXT, "
            + KEY_ReminderTime + " TEXT );";

    private int reminderTimeId;
    private int reminderId;
    private String reminderTime;

    public int getReminderTimeId() {
        return reminderTimeId;
    }

    public void setReminderTimeId(int reminderTimeId) {
        this.reminderTimeId = reminderTimeId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}
