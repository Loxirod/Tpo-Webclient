package pjatk.tpo.malkinsk.biblioteka;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pjatk.tpo.malkinsk.biblioteka.service.PozycjeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "nowaPozycjaServlet", value = "/nowa-pozycja")
public class NowaPozycjaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Nowa pozycja w bibliotece</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Nowa pozycja w bibliotece</h1>");
        out.println("<form action=\"/biblioteka_war_exploded/nowa-pozycja\" method=\"post\">");
        out.println("<label for=\"tytul\">Tytuł książki</label> <input name=\"tytul\" id=\"tytul\"><br/>");
        out.println("<label for=\"autor\">Autor</label> <input name=\"autor\" id=\"autor\"><br/>");
        out.println("<input type=\"submit\" value=\"Dodaj\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("autor: "+req.getParameter("autor"));
        System.out.println("tytul: "+req.getParameter("tytul"));
        try {
            (new PozycjeService()).createTablePozycja();
        } catch (SQLException e) {
            System.out.println("Tabela juz istnieje");
        }
        try {
            (new PozycjeService()).dodajNowaPozycja(req.getParameter("autor"), req.getParameter("tytul"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/biblioteka_war_exploded/biblioteka-glowna");
    }
}
