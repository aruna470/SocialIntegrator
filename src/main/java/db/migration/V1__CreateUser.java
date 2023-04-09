package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;

public class V1__CreateUser extends BaseJavaMigration { //NOSONAR

    public void migrate(Context context) throws Exception {
        try (PreparedStatement statement = 
                 context
                     .getConnection()
                     .prepareStatement(this.getSql())) {
            statement.execute();
        }
    }

    private String getSql() {

        return "CREATE TABLE User (" 
            + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "firstName VARCHAR(30) NOT NULL,"
            + "lastName VARCHAR(100) NOT NULL,"
            + "email VARCHAR(100) NOT NULL UNIQUE,"
            + "password TEXT NOT NULL,"
            + "createdAt DATETIME NOT NULL,"
            + "status INT(1) NOT NULL,"
            + "verificationCode INT(4) NULL,"
            + "emailVerified INT(1) NOT NULL)"
            + "ENGINE=InnoDB DEFAULT CHARSET=UTF8";
    }
}