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
package com.manning.junitbook.ch14.ejbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.cactus.ServletTestCase;
import org.apache.commons.beanutils.DynaBean;

import com.manning.junitbook.ch14.ejb.AdministratorBean;
import com.manning.junitbook.ch14.ejb.IAdministratorLocal;

/**
 * A test-case for the AdministratorEJB
 */
public class TestAdministratorEJB extends ServletTestCase {
	/**
	 * Class under test
	 */
	private IAdministratorLocal administrator;

	
	/**
	 * @see TestCase#setUp()
	 */
	public void setUp() throws Exception {
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs",
				"org.jboss.naming rg.jnp.interfaces");

		InitialContext ctx = new InitialContext(properties);

		administrator = (IAdministratorLocal) ctx.lookup("ch14-cactus-ear-cactified/"
				+ AdministratorBean.class.getSimpleName() + "/local");
		
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
		
		s.execute("DROP TABLE USERS IF EXISTS");
		
		s.execute("CREATE TABLE USERS(ID INT, NAME VARCHAR(40))");
		
		PreparedStatement psInsert = conn.prepareStatement("INSERT INTO USERS VALUES (?, ?)");

	    psInsert.setInt(1, 1);
	    psInsert.setString(2, "User 1");
	    psInsert.executeUpdate();

	    psInsert.setInt(1, 2);
	    psInsert.setString(2, "User 2");
	    psInsert.executeUpdate();
	}

	/**
	 * Verify that selecting number of inserted users works properly.
	 * 
	 * @throws Exception on error	 
	 */
	public void testAdministratorBeanExecuteWithAllUsers() throws Exception {
		Collection result = administrator.execute("SELECT * FROM USERS ORDER BY ID ASC");
		
		assertNotNull(result);
		assertEquals(result.size(), 2);
		
		Iterator resultIterator = result.iterator();
		
		DynaBean bean1 = (DynaBean)resultIterator.next();
		assertEquals(bean1.get( "id" ), new Integer(1));
		assertEquals(bean1.get( "name" ), "User 1");

        DynaBean bean2 = (DynaBean)resultIterator.next();
        assertEquals(bean2.get( "id" ), new Integer(2));
        assertEquals(bean2.get( "name" ), "User 2");

		assertFalse(resultIterator.hasNext());
	}
	
	/**
	 * Again test the {@link AdministratorBean#execute(String)} method by passing a
	 * query that would return empty resultset.
	 * 
	 * @throws Excpetion on error
	 */
	public void testAdministratorBeanSelectOneUserShouldResultInNiceOutput() throws Exception {
        Collection result = administrator.execute("SELECT * FROM USERS WHERE ID='3' ORDER BY ID ASC");
        
        assertNotNull(result);
        assertEquals(result.size(), 0);
	}
	
	/**
	 * Test the {@link AdministratorBean#execute(String)} method by passing invalid query
	 * 
	 * @throws Exception
	 */
	public void testAdministratorBeanPassInvalidQueryShouldResultInException() throws Exception {
        try {
            //This query is invalid but it will pass our filter
            administrator.execute("SELECT ...");
	    } catch (SQLException sqlex) {
	        assertEquals(sqlex.getMessage(), "Unexpected token: . in statement [SELECT .]");
	    }
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