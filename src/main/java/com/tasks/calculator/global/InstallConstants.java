package com.tasks.calculator.global;

public class InstallConstants {
    InstallConstants(){
    }
    public static final String DEFAULT_TIMEZONE = "Europe/Riga";
    public static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    //java higher "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_NAME = "tasks";
    public static final String USER = "springUser";
    //@Value("${db.password}")
    public static final String PASS = "Libra28091963!";

    public static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DATABASE_NAME +"?user=" + USER + "&password=" + PASS;
}
