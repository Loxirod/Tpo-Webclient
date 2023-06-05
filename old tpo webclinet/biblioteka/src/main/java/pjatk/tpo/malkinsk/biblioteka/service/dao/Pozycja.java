package pjatk.tpo.malkinsk.biblioteka.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pozycja {
    private int id;
    private String tytul;
    private String autor;

    public Pozycja(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        tytul = resultSet.getString("tytul");
        autor = resultSet.getString("autor");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Pozycja{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
