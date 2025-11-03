package control.maglietta;

import model.maglietta.MagliettaBean;
import model.maglietta.MagliettaDAO;
import model.misura.MisuraBean;
import model.misura.MisuraDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;

@WebServlet("/SaveMaglietta")
@MultipartConfig
public class SaveMaglietta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String PATH = req.getServletContext().getInitParameter("WHITEE_ROOT") + File.separator + "images" +
                File.separator + "grafiche" + File.separator;
        String nome  = req.getParameter("nome");
        String colore = req.getParameter("colore");
        String tipo = req.getParameter("tipo");
        int IVA = (int) Float.parseFloat(req.getParameter("IVA"));
        float prezzo = Float.parseFloat(req.getParameter("prezzo"));
        String descrizione = req.getParameter("descrizione");
        Part grafica = req.getPart("grafica");

        MagliettaDAO magliettaDAO = new MagliettaDAO();
        String nomeFile = "";
        int extensionIndex = grafica.getSubmittedFileName().lastIndexOf(".");

        try {
            nomeFile = magliettaDAO.getMaxID() + tipo +
                    grafica.getSubmittedFileName().substring(extensionIndex);
        } catch (SQLException e) {
            req.getRequestDispatcher("/pages/errorpage.jsp").forward(req, resp);
        }

        String relativePath = "images" + File.separator + "grafiche" + File.separator + nomeFile;

        try (OutputStream outputStream = new FileOutputStream(PATH + nomeFile); InputStream inputStream = grafica.getInputStream()) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            req.getRequestDispatcher("/pages/errorpage.jsp").forward(req, resp);
        }

        MagliettaBean maglietta = new MagliettaBean();
        maglietta.setNome(nome);
        maglietta.setColore(colore);
        maglietta.setTipo(tipo);
        maglietta.setPrezzo(prezzo);
        maglietta.setIVA(IVA);
        maglietta.setDescrizione(descrizione);
        maglietta.setGrafica(relativePath);

        String taglia = req.getParameter("taglia");
        int quantita = Integer.parseInt(req.getParameter("quantita"));

        MisuraDAO misuraDAO = new MisuraDAO();

        try {
            magliettaDAO.doSave(maglietta);

            MisuraBean misuraBean = new MisuraBean(magliettaDAO.getMaxID() - 1, quantita, taglia);

            misuraDAO.doSave(misuraBean);
        } catch (SQLException e) {
            req.getRequestDispatcher("/pages/errorpage.jsp").forward(req, resp);
        }

        resp.sendRedirect("./Catalogo");
    }
}
