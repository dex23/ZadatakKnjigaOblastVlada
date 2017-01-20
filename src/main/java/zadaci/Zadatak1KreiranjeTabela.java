package zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;

/**
 * Created by user on 20.1.2017..
 */
public class Zadatak1KreiranjeTabela {
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
            TableUtils.dropTable(connectionSource, Oblast.class,true);
            TableUtils.dropTable(connectionSource, Knjiga.class,true);

            TableUtils.createTable(connectionSource, Knjiga.class);
            TableUtils.createTable(connectionSource, Oblast.class);


        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
