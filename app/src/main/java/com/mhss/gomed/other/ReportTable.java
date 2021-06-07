package com.mhss.gomed.other;

public class ReportTable {

    public static final String TAG = ReportTable.class.getSimpleName();
    public static final String TABLE = "tblReport";
    // Labels Table Columns names
    public static final String KEY_ReportId = "ReportId";
    public static final String KEY_Med_Name = "MedName";
    public static final String KEY_DateTime = "DateTime";
    public static final String KEY_Status = "status";

    public static final String CREATE_TABLE_REPORT_TABLE = "CREATE TABLE " + TABLE + "("
            + KEY_ReportId + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Med_Name + " TEXT, "
            + KEY_DateTime + " TEXT, "
            + KEY_Status + " TEXT );";

    private int ReportId;
    private String MedName;
    private String datetime;
    private String status;

    public int getReportId() {
        return ReportId;
    }

    public void setReportId(int reportId) {
        ReportId = reportId;
    }

    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
