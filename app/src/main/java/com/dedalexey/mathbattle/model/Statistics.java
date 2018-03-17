package com.dedalexey.mathbattle.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dedalexey.mathbattle.DataBase.SessionCursorWrapper;
import com.dedalexey.mathbattle.DataBase.SessionDBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.dedalexey.mathbattle.DataBase.DBSchema.*;

/**
 * Created by alexey on 3/17/18.
 */

public class Statistics {

    private static Statistics sStatistics;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Statistics get(Context context){
        if(sStatistics == null){
            sStatistics = new Statistics(context);

        }
        return sStatistics;
    }

    private Statistics(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new SessionDBHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(SessionInfo sessionInfo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SessionsTable.Cols.UUID,sessionInfo.getId().toString());
        contentValues.put(SessionsTable.Cols.PLAYER_NAME, sessionInfo.getPlayerName());
        contentValues.put(SessionsTable.Cols.TOTAL_TIME, sessionInfo.getTotalTime());
        contentValues.put(SessionsTable.Cols.TRUE_ANSWERS, sessionInfo.getTrueAnswers());
        contentValues.put(SessionsTable.Cols.FALSE_ANSWERS, sessionInfo.getFalseAnswers());
        contentValues.put(SessionsTable.Cols.DATE,sessionInfo.getDate().getTime());

        return contentValues;
    }

    public void addSessionInfo(SessionInfo sessionInfo){
        ContentValues contentValues = getContentValues(sessionInfo);
        mDatabase.insert(SessionsTable.NAME,null,contentValues);
    }

    public void updateSession(SessionInfo sessionInfo){
        ContentValues contentValues = getContentValues(sessionInfo);
        String uuidString = sessionInfo.getId().toString();
        mDatabase.update(SessionsTable.NAME,contentValues,
                SessionsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private SessionCursorWrapper queryCrimes (String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SessionsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SessionCursorWrapper(cursor);
    }

    public List<SessionInfo> getSessionInfoList(){
        List<SessionInfo> sessionInfoList = new ArrayList<>();

        SessionCursorWrapper cursor = queryCrimes(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                sessionInfoList.add(cursor.getSessionInfo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return sessionInfoList;
    }

}
