package control.utente;

import model.utente.UtenteBean;
import model.utente.UtenteDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Login")
public class Login extends HttpServlet {
    public static final int ADMIN = 0;
    public static final int REGISTRATO = 1;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            String redirectedPage;
            int tipoUtente = checkUser(username, password);
            UtenteDAO utenteDAO = new UtenteDAO();
            UtenteBean utenteBean = utenteDAO.doRetrieveByKey(username);

            req.getSession().setAttribute("utente", utenteBean);

            switch (tipoUtente) {
                case ADMIN:
                    req.getSession().setAttribute("tipoUtente", ADMIN);
                    redirectedPage = "index.jsp";
                    break;
                case REGISTRATO:
                    req.getSession().setAttribute("tipoUtente", REGISTRATO);

                    redirectedPage = "index.jsp";
                    break;
                default:
                    redirectedPage = "pages/login.jsp";
            }

            resp.sendRedirect(redirectedPage);
        } catch (SQLException e) {
            req.getRequestDispatcher("/pages/errorpage.jsp").forward(req, resp);
        }
    }

    private int checkUser(String username, String password) throws SQLException {
        UtenteDAO utenteDAO = new UtenteDAO();
        UtenteBean utenteBean = utenteDAO.doRetrieveByKey(username);

        if (utenteBean == null || !(utenteBean.getPwd().equals(password)))
            return -1;
        else {
            if (utenteBean.getTipo().equals("admin"))
                return ADMIN;
            else
                return REGISTRATO;
        }
    }
}
