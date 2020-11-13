package com.mycompany.organizationdemo.domaindatalayer.jpa.constants;

public final class OrmConstants {

    /* H2, probably Oracle and MySql */
    public static final String OFFSET_DATE_TIME_COLUMN_DEFINITION = "TIMESTAMP WITH TIME ZONE";

    /* Sql Server */
    //public static final String OFFSET_DATE_TIME_COLUMN_DEFINITION = "DATETIMEOFFSET(7)";

    private OrmConstants() {
        /* no "static class" in java.  :( */
    }
}
