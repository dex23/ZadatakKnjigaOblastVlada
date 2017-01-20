package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 20.1.2017..
 */
public class Zadatak4BrisanjeVrednosti{

    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;
        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
            knjigaDao= DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao=DaoManager.createDao(connectionSource, Oblast.class);

            //Prikaz vrednosti tabele Oblast
            List<Oblast> oblasti=oblastDao.queryForAll();
            for(Oblast o:oblasti)
                System.out.println("o = " + o);

            //Pronalazenje oblasti koje za vrednost kolone naziv imaju
            // vrednost "Aritmeticki operatori"
            oblasti=oblastDao.queryForEq(Oblast.POLJE_NAZIV,"Aritmeticki operatori");
            Oblast zaBrisanje=oblasti.get(0);//Preuzimamo prvi pronadjeni
            //Brisemo vrednost iz baze
            oblastDao.delete(zaBrisanje);

            /*Prikaz vrednosti tabele Oblast
               da potvrdimo da je vrednost obrisana
             */
            oblasti=oblastDao.queryForAll();
            for(Oblast o:oblasti)
                System.out.println("o = " + o);
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
