package pjatk.tpo.malkinsk.biblioteka;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pjatk.tpo.malkinsk.biblioteka.service.PozycjeService;
import pjatk.tpo.malkinsk.biblioteka.service.dao.Pozycja;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "bibliotekaServlet", value = "/biblioteka-glowna")
public class BibliotekaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(String.valueOf(req.getParameter("test")));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        //out.println("<h1> to jest get</h1>");
        out.println("</body></html>");

        out.println("<a href=\"/biblioteka_war_exploded/nowa-pozycja\">Nowa pozycja</a><br/>\n");

        out.println("<div style = \"text-align:center;\"><h1>Co pragniesz znaleźć?</h1>"+"</div>");
        out.println("<form action=\"/biblioteka_war_exploded/biblioteka-glowna\" method=\"get\">");
        String autor = req.getParameter("autor")==null?"":req.getParameter("autor");
        String tytul = req.getParameter("tytul")==null?"":req.getParameter("tytul");
        out.println("<div style = \"text-align:center;\"><label>Autor</label><input name=\"autor\" value=\""+autor+"\"><br/>"+"</div>");
        out.println("<div style = \"text-align:center;\"><label>Tytuł</label><input name=\"tytul\"value=\""+tytul+"\"><br/>"+"</div>");

        out.println("<div style = \"text-align:center;\"><input type=\"submit\" value=\"Szukaj\">"+"</div>");
        out.println("</form>");

        out.println("<h1>Wyniki</h1>");
        out.println("<table>");
        out.println("<tr><th>Id</th><th>Autor</th><th>Tytuł</th></tr>");
        out.println("<style>\n" +
                "body {\n" +
                "  background-color: lightblue;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "  color: white;\n" +

                "}\n" +
                "\n" +
                "p {\n" +
                "  font-family: verdana;\n" +
                "  font-size: 20px;\n" +
                "}\n" +
                "</style>");
        try {
            for(Pozycja pozycja : (new PozycjeService()).getPozycjeFiltered(req.getParameter("tytul")==null?"":req.getParameter("tytul"), req.getParameter("autor")==null?"":req.getParameter("autor"))) {
                out.println("<tr>");
                out.println("<td>"+pozycja.getId()+"</td>");
                out.println("<td>"+pozycja.getAutor()+"</td>");
                out.println("<td>"+pozycja.getTytul()+"</td>");
                out.println("</td>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</table>");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>To jest post</h1>");
        out.println("</body></html>");
    }
}
