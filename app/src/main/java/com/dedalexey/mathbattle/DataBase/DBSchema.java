package com.dedalexey.mathbattle.DataBase;

/**
 * Created by Alexey on 08.02.2018.
 */

public class DBSchema {

    public static final class SessionsTable {
        public static final String NAME = "sessions";

        public static final class Cols {

            public static final String UUID = "uuid";
            public static final String PLAYER_NAME = "player_name";
            public static final String TOTAL_TIME = "total_time";
            public static final String TRUE_ANSWERS = "true_answers";
            public static final String FALSE_ANSWERS = "false_answers";
            public static final String DATE = "date";
        }
    }
}
