package com.dh.consultorioOdontologico.dao.configuracaoJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class ConfiguracaoJDBC {

    private String driver;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
