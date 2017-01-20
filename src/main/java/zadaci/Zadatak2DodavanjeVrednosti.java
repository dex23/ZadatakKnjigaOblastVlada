package zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;

/**
 * Created by user on 20.1.2017..
 */
public class Zadatak2DodavanjeVrednosti {
    public static void main(String[] args){
        ConnectionSource connectionSource = null;
        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
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
