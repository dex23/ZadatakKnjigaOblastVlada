package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 20.1.2017..
 */
public class Zadatak2DodavanjeVrednosti {

    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args){
        ConnectionSource connectionSource = null;
        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");
            knjigaDao= DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao=DaoManager.createDao(connectionSource, Oblast.class);

            //Kreiranje objekta klase Knjiga
            Knjiga k1=new Knjiga("Java programiranje",650,new Date());
            knjigaDao.create(k1);

            Knjiga k2=new Knjiga("Android programiranje",650,new Date());
            knjigaDao.create(k2);

            //Kreiranje objekta klase Oblast
            Oblast o1=new Oblast("Uvod",2);
            o1.setKnjiga(k1);
            oblastDao.create(o1);

            Oblast o2=new Oblast("Naredbe",10);
            o2.setKnjiga(k1);
            oblastDao.create(o2);

            Oblast o3=new Oblast("Aritmeticki operatori",20);
            o3.setKnjiga(k1);
            oblastDao.create(o3);

            Oblast o4=new Oblast("Android operativni sistem",2);
            o4.setKnjiga(k2);
            oblastDao.create(o4);

            Oblast o5=new Oblast("Activity klasa",30);
            o5.setKnjiga(k2);
            oblastDao.create(o5);

            //Prikaz svih vrednosti tabela Knjiga
            List<Knjiga> knjige=knjigaDao.queryForAll();
            for(Knjiga k:knjige)
                System.out.println("k = " + k);

            //Prikaz svih vrednosti tabela Oblast
            List<Oblast> oblasti=oblastDao.queryForAll();
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
