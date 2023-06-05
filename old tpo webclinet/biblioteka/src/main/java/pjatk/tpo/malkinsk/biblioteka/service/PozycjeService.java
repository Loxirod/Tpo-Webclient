package pjatk.tpo.malkinsk.biblioteka.service;

import org.apache.derby.jdbc.EmbeddedDriver;
import pjatk.tpo.malkinsk.biblioteka.service.dao.Pozycja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PozycjeService {
    /**
     * Przechowuje połączenie do bazy danych
     */
    private Connection connection;

    /**
     * Tworzenie obiektu klasy i stworzenie połaczenia do bazy danych
     * @throws SQLException
     */
    public PozycjeService() throws SQLException {
        DriverManager.registerDriver(new EmbeddedDriver());
        connection = DriverManager.getConnection("jdbc:derby:StudentDb;create=true");
    }

    /**
     * Dodajemy rekord do tabeli POZYCJA
     * @param autor
     * @param tytul
     * @throws SQLException
     */
    public void dodajNowaPozycja(String autor, String tytul) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into POZYCJA values ("+getLastIdPozycja()+",'"+autor+"','"+tytul+"')");
        connection.commit();
    }

    /**
     * Wylicza nową wartość dla ID
     * @return
     * @throws SQLException
     */
    public int getLastIdPozycja() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM POZYCJA");
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            if (id==null) {
                return 1;
            }
            else {
                return id+1;
            }
        }
        return 1;
    }

    /**
     * Tworzenie tabeli w bazie danych
     * @throws SQLException
     */
    public void createTablePozycja() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE POZYCJA (id int primary key , autor varchar(200), tytul varchar(200) )");
    }

    /**
     * Pobieranie wszystkich rekordów z bazy danych
     * @return
     * @throws SQLException
     */
    public List<Pozycja> getPozycje() throws SQLException {
        List<Pozycja> ret = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM POZYCJA");
        while (resultSet.next()) {
            ret.add(new Pozycja(resultSet));
        }
        return ret;
    }

    /**
     * Pobieranie rekordów z bazy danych z możliwością filtrowania
     * @param tytul
     * @param autor
     * @return
     * @throws SQLException
     */
    public List<Pozycja> getPozycjeFiltered(String tytul, String autor) throws SQLException {
        List<Pozycja> ret = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM POZYCJA";

        List<String> where = new ArrayList<>();

        if (tytul!=null && !tytul.isEmpty()) {
            where.add("tytul like '%"+tytul.trim()+"%'");
        }
        if (autor!=null && !autor.isEmpty()) {
            where.add("autor like '%"+autor.trim()+"%'");
        }
        System.out.println(where);
        boolean addAnd=false;
        for(String s: where) {
            if (addAnd) {
                sql = sql.concat("\nAND ").concat(s);
            }
            else {
                sql = sql.concat("\nWHERE ").concat(s);
                addAnd = true;
            }
        }

        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            ret.add(new Pozycja(resultSet));
        }
        return ret;
    }

}
