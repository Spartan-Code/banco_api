/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpmislata.presentacion.database.impl;

import com.fpmislata.banco.persistence.jdbc.DataSourceFactory;
import com.fpmislata.presentacion.database.MigrationDB;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author German
 */
public class MigrationDBImpl implements MigrationDB {
    @Autowired
    DataSourceFactory dataSourceFactory;
    @Override
    public void toMigration() {
        try {           
            DataSource dataSource = dataSourceFactory.getDataSource();
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.setLocations("com.fpmislata.banco.persistence.scripts");
            flyway.setEncoding("utf-8");
            flyway.migrate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

}
