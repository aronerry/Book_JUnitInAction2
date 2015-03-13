/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.manning.junitbook.ch14.ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.apache.commons.beanutils.RowSetDynaClass;

/**
 * An EJB that we use to extract the necessary information from the database.
 */
@Stateless
public class AdministratorBean implements IAdministratorLocal {
	/**
	 * This mehtod extracts information from the database and constructs a
	 * java.util.Collection from the rows.
	 */
	public Collection execute(String sql) throws Exception {
		Connection connection = getConnection();
		ResultSet resultSet = connection.createStatement().executeQuery(sql);

		RowSetDynaClass rsdc = new RowSetDynaClass(resultSet);
		resultSet.close();
		connection.close();
		return rsdc.getRows();
	}

	/**
	 * A helper method to get the connection to the database.
	 * 
	 * @return the connection to the database
	 * @throws NamingException
	 * @throws SQLException
	 */
    private Connection getConnection() throws NamingException, SQLException {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load Derby JDBC driver.");
            e.printStackTrace();
            return null;
        }

        return DriverManager.getConnection("jdbc:hsqldb:file:testdb", "sa", "");
    }
}
