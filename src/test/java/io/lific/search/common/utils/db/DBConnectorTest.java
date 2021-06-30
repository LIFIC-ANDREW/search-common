package io.lific.search.common.utils.db;


import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class DBConnectorTest {


	private static final String TABLE_NAME = "test_table"; // or just "test"

	@Test
	public void test() throws SQLException {
		try (EmbeddedPostgres pg = EmbeddedPostgres.start();
		     DBConnector connector = new DBConnector(pg.getPostgresDatabase().getConnection())) {

			assertFalse(
				connector.create(String.format("CREATE TABLE IF NOT EXISTS %s ("
					+ "name varchar (64) not null,"
					+ "domain varchar (128) not null,"
					+ "reg_date timestamp not null default CURRENT_TIMESTAMP,"
					+ "primary key (name, domain)"
					+ ")", TABLE_NAME))
			);
			assertTrue(
				connector.exist(TABLE_NAME)
			);
			assertEquals(1
				, connector.insert(String.format("INSERT INTO %s (name, domain) VALUES ('test1', 'www.test.com')", TABLE_NAME))
			);
			assertEquals(1
				, connector.count(String.format("SELECT count(*) from %s", TABLE_NAME))
			);
			ResultSet result = connector.select(String.format("SELECT name, domain from %s", TABLE_NAME));
			assertTrue(result.next());
			assertEquals("test1"
				, result.getString("name")
			);
			assertEquals("www.test.com"
				, result.getString("domain")
			);

		} catch (Exception e) {
			System.out.print(e);
			fail();
		}
	}
}