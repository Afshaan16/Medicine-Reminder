package com.mhss.gomed.other;

public class NotesTable {

    public static final String TAG = NotesTable.class.getSimpleName();
    public static final String TABLE = "tblNotes";
    // Labels Table Columns names
    public static final String KEY_NotesId = "NotesId";
    public static final String KEY_Notes = "Notes";
    public static final String KEY_DateTime = "DateTime";

    public static final String CREATE_TABLE_TIME_TABLE = "CREATE TABLE " + TABLE + "("
            + KEY_NotesId + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Notes + " TEXT, " + KEY_DateTime + " TEXT );";

    private int notesId;
    private String notes;
    private String datetime;

    public NotesTable(String notes , String datetime){
        this.notes = notes;
        this.datetime = datetime;
    }

    public int getNotesId() {
        return notesId;
    }

    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
