package com.dedalexey.mathbattle.DataBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.dedalexey.mathbattle.model.SessionInfo;

import java.util.Date;
import java.util.UUID;

import static com.dedalexey.mathbattle.DataBase.DBSchema.*;

/**
 * Created by alexey on 3/17/18.
 */

public class SessionCursorWrapper extends CursorWrapper {

    public SessionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public SessionInfo getSessionInfo(){
        String uuidStrimg = getString(getColumnIndex(SessionsTable.Cols.UUID));
        String playerName = getString(getColumnIndex(SessionsTable.Cols.PLAYER_NAME));
        int trueAnswers = getInt(getColumnIndex(SessionsTable.Cols.TRUE_ANSWERS));
        int falseAnswers = getInt(getColumnIndex(SessionsTable.Cols.FALSE_ANSWERS));
        long totalTime = getLong(getColumnIndex(SessionsTable.Cols.TOTAL_TIME));
        long date = getLong(getColumnIndex(SessionsTable.Cols.DATE));

        SessionInfo sessionInfo = new SessionInfo(UUID.fromString(uuidStrimg));
        sessionInfo.setPlayerName(playerName);
        sessionInfo.setTotalTime(totalTime);
        sessionInfo.setDate(new Date(date));
        sessionInfo.setTrueAnswers(trueAnswers);
        sessionInfo.setFalseAnswers(falseAnswers);
        return sessionInfo;
    }
}
