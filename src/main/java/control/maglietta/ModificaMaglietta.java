package control.maglietta;

import model.maglietta.MagliettaBean;
import model.maglietta.MagliettaDAO;
import model.misura.MisuraDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ModificaMaglietta")
public class ModificaMaglietta extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ID = Integer.parseInt(req.getParameter("id"));

        MagliettaDAO magliettaDAO = new MagliettaDAO();
        MisuraDAO misuraDAO = new MisuraDAO();

        try {
            MagliettaBean magliettaBean = magliettaDAO.doRetrieveByKey(ID);
            req.setAttribute("maglietta", magliettaBean);
            req.setAttribute("misure", misuraDAO.doRetrieveAll(ID));
        } catch (SQLException e) {
            req.getRequestDispatcher("/pages/errorpage.jsp").forward(req, resp);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("pages/modifica.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }
}
