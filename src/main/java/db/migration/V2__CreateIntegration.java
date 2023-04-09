package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;

public class V2__CreateIntegration extends BaseJavaMigration { //NOSONAR

    public void migrate(Context context) throws Exception {
        try (PreparedStatement statement = 
                 context
                     .getConnection()
                     .prepareStatement(this.getSql())) {
            statement.execute();
        }
    }

    private String getSql() {

        return "CREATE TABLE Integration (" 
            + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "userId INT NOT NULL,"
            + "code VARCHAR(45) NOT NULL,"
            + "createdAt DATETIME NOT NULL,"
            + "updatedAt DATETIME NULL,"
            + "name VARCHAR(45) NOT NULL,"
            + "FOREIGN KEY (userId) REFERENCES User(id))"
            + "ENGINE=InnoDB DEFAULT CHARSET=UTF8";
    }
}