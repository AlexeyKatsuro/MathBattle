package com.dedalexey.mathbattle.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dedalexey.mathbattle.model.SessionInfo;

import java.util.ArrayList;

import static com.dedalexey.mathbattle.DataBase.DBSchema.SessionsTable.*;


/**
 * Created by Alexey on 01.09.2017.
 */

public class SessionDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "SessionBase.db";
    private static final String TAG = SessionDBHelper.class.getSimpleName();

    public SessionDBHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        upDateDadabase(db,0,VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upDateDadabase(db,oldVersion,newVersion);
    }

    private void upDateDadabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<1){
            db.execSQL("create table " + NAME +"("+
                    " _id integer primary key autoincrement, " +
                    Cols.UUID + ", " +
                    Cols.PLAYER_NAME + ", " +
                    Cols.TOTAL_TIME + " , " +
                    Cols.TRUE_ANSWERS + ", " +
                    Cols.FALSE_ANSWERS + ", " +
                    Cols.DATE + ")"
            );
        }
//        if(oldVerison<2) db.execSQL("ALTER TABLE SHOPS ADD COLUMN COLUMN_NAME TEXT;");
    }

    public void addSession(SessionInfo sessionInfo){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.PLAYER_NAME, sessionInfo.getPlayerName());
        contentValues.put(Cols.TOTAL_TIME, sessionInfo.getTotalTime());
        contentValues.put(Cols.TRUE_ANSWERS, sessionInfo.getTrueAnswers());
        contentValues.put(Cols.FALSE_ANSWERS, sessionInfo.getFalseAnswers());
        database.insert(NAME,null,contentValues);
        database.close();
    }

    public SessionInfo getSession(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] colums = new String[]{Cols.PLAYER_NAME,Cols.TOTAL_TIME,
                Cols.TRUE_ANSWERS,Cols.FALSE_ANSWERS};
        Cursor cursor =
                db.query(NAME, colums,
                "_id=?", new String[] { String.valueOf(id) }, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        SessionInfo sessionInfo = new SessionInfo();
//                sessionInfo.setPlayerName(cursor.getString(0));
//                Long.parseLong(cursor.getString(1)),
//                Integer.getInteger(cursor.getString(2)),
//                Integer.getInteger(cursor.getString(3)));
        return sessionInfo;
    }

    public ArrayList<SessionInfo> getAllSession() {
        ArrayList<SessionInfo> sessionInfoList = new ArrayList<SessionInfo>();
        String query = "SELECT * FROM " + NAME;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor cursor = db.rawQuery(query, null);
            Cursor cursor = getAllCursor();
            if (cursor.moveToFirst()) {
                do {
                    SessionInfo sessionInfo = new SessionInfo();
                    sessionInfo.setPlayerName(cursor.getString(0));
                    sessionInfo.setTotalTime(Long.parseLong(cursor.getString(1)));
                    sessionInfo.setTrueAnswers( Integer.parseInt(cursor.getString(2)));
                    sessionInfo.setFalseAnswers( Integer.parseInt(cursor.getString(3)));
                    sessionInfoList.add(sessionInfo);
                } while (cursor.moveToNext());
            }
            return sessionInfoList;
        } catch (SQLiteException e) {
            Log.e(TAG,"getAllSessions Fail|DB Error",e);
            return null;
        }
    }
    public Cursor getAllCursor() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.query(NAME, null, null, null, null, null, "_id " +" DESC");

        } catch (SQLiteException e) {
            Log.e(TAG,"getAllCursor Fail|DB Error", e);
            return null;
        }

    }
}
