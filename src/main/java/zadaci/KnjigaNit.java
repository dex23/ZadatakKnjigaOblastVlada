package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 20.1.2017..
 */
public class KnjigaNit extends Thread {
    private String imeClana;
    private Knjiga knjiga;

    static Dao<Knjiga,Integer> knjigaDao;

    public KnjigaNit(String imeClana, Knjiga knjiga) {
        this.imeClana = imeClana;
        this.knjiga = knjiga;
    }

    @Override
    public void run() {
        //Koristicemo lokalnu promenljivu da bismo izbegli sinhronizaciju u while (videti dole)
        boolean uzeoKnjigu = false;
        System.out.println("Clan " + imeClana + " trazi knjigu " + knjiga.getNaslov());

        do {
            synchronized (knjiga) {
                if (knjiga.isPrisutna()) {

                    uzeoKnjigu = true;
                    knjiga.setPrisutna(false);
                    System.out.println("Clan " + imeClana + " uzima knjigu " + knjiga.getNaslov());

                    Random random = new Random();

                    try {

                        sleep(random.nextInt(5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    knjiga.setPrisutna(true);
                    System.out.println("Clan " + imeClana + " vraca knjigu " + knjiga.getNaslov());
                }
            }
        } while (!uzeoKnjigu) ;
    }

    public static void main(String[] args){
        ConnectionSource connectionSource = null;
        try {

            connectionSource=new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            knjigaDao= DaoManager.createDao(connectionSource, Knjiga.class);


            List<Knjiga> sveKnjige = knjigaDao.queryForAll();

            KnjigaNit kn1 = new KnjigaNit("Pera", sveKnjige.get(0));
            KnjigaNit kn2 = new KnjigaNit("Mika", sveKnjige.get(0));
            KnjigaNit kn3 = new KnjigaNit("Ana", sveKnjige.get(0));

            KnjigaNit kn4 = new KnjigaNit("Zika", sveKnjige.get(1));
            KnjigaNit kn5 = new KnjigaNit("Branka", sveKnjige.get(1));
            KnjigaNit kn6 = new KnjigaNit("Sanja", sveKnjige.get(1));

            kn1.start();
            kn2.start();
            kn3.start();
            kn4.start();
            kn5.start();
            kn6.start();

            try {
                kn1.join();
                kn2.join();
                kn3.join();
                kn4.join();
                kn5.join();
                kn6.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Biblioteka se zatvara.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //Zatvaranje konekcije sa bazom
                connectionSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
