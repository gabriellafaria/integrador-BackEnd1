package com.dh.consultorioOdontologico.dao.configuracaoJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracaoJDBC {

    private String driver;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;


    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public Connection getConnectionH2() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/CONSULTORIO_ODONTOLOGICO;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'create.sql'", "sa","");
    }
}
