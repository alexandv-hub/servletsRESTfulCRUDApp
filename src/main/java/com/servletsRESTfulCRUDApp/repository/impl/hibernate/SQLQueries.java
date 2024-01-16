package com.servletsRESTfulCRUDApp.repository.impl.hibernate;

public final class SQLQueries {

    private SQLQueries() {
    }

    public static final String SQL_SHOW_DATABASES = "SHOW DATABASES;";
    public static final String SQL_CREATE_DATABASE = "CREATE DATABASE ";

    static final String HQL_FROM_EVENT_LEFT_JOIN_FETCH_FILE_FETCH_USER =
                                                                """
                                                                from Event e
                                                                LEFT JOIN FETCH e.file
                                                                LEFT JOIN FETCH e.user
                                                                ORDER BY e.id ASC
                                                                """;

    static final String HQL_FROM_EVENT_BY_ID_LEFT_JOIN_FETCH_FILE_FETCH_USER =
                                                                """
                                                                from Event e
                                                                LEFT JOIN FETCH e.file
                                                                LEFT JOIN FETCH e.user
                                                                WHERE e.id = :id
                                                                """;

    static final String HQL_FROM_USER_LEFT_JOIN_FETCH_EVENTS = """
                                                                from User u
                                                                LEFT JOIN FETCH u.events e
                                                                LEFT JOIN FETCH e.file
                                                                LEFT JOIN FETCH e.user
                                                                ORDER BY e.id ASC
                                                                """;

    static final String HQL_FROM_USER_BY_ID_LEFT_JOIN_FETCH_EVENTS = """
                                                                from User u
                                                                LEFT JOIN FETCH u.events e
                                                                LEFT JOIN FETCH e.file
                                                                LEFT JOIN FETCH e.user
                                                                WHERE u.id = :id
                                                                """;
}
