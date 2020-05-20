package com.mycompany.organizationdemo.domain.constants;

public class OrmConstants {

    private OrmConstants()
    {
        /* no "static class" in java.  :( */
    }

    /* H2, probably Oracle and MySql */
    public final static String OffsetDateTimeColumnDefinition = "TIMESTAMP WITH TIME ZONE";

    /* Sql Server */
    //public final static String OffsetDateTimeColumnDefinition = "DATETIMEOFFSET(7)";
}
