package model.utente;

import model.DAOInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import java.sql.Date;

public class UtenteDAO implements DAOInterface<UtenteBean, String> {
    private static final String TABLE_NAME = "Utente";
    private static final DataSource ds;

    // Connessione database
    static {
        try {
            Context init = new InitialContext();
            Context env = (Context) init.lookup("java:comp/env");

            ds = (DataSource) env.lookup("jdbc/whiTee");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public UtenteBean doRetrieveByKey(String code) throws SQLException {
        UtenteBean utenteBean = new UtenteBean();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        return getUtenteBean(code, utenteBean, query);
    }

    @Override
    public Collection<UtenteBean> doRetriveAll(String order) throws SQLException {
        Collection<UtenteBean> utenti = new ArrayList<>();

        String query = "SELECT * FROM" + TABLE_NAME;

        try (Connection connection=ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UtenteBean utenteBean = new UtenteBean();
                setUtente(resultSet, utenteBean);
                utenti.add(utenteBean);
            }
        }

        return utenti;
    }

    public UtenteBean doRetrieveByEmail(String email) throws SQLException {
        UtenteBean utenteBean = new UtenteBean();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        return getUtenteBean(email, utenteBean, query);
    }


    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (username, pwd, nome, cognome, email, dataNascita, nomeCarta, cognomeCarta, numCarta, dataScadenza, CVV, cap, via, citta, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setUtenteStatement(utente, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    @Override
    public synchronized void doUpdate(UtenteBean utente) throws SQLException {
        String query =  "UPDATE " + TABLE_NAME +
                        " SET pwd = ?, nome = ?, cognome = ?,"+
                        " email = ?, dataNascita = ?, numCarta = ?," +
                        " dataScadenza = ?, CVV = ?, nomeCarta = ?, cognomeCarta = ?, cap = ?, via = ?," +
                        " citta = ?, tipo = ?" +
                        " WHERE username = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
           preparedStatement.setString(1, utente.getPwd());
           preparedStatement.setString(2, utente.getNome());
           preparedStatement.setString(3, utente.getCognome());
           preparedStatement.setString(4, utente.getEmail());
           preparedStatement.setDate(5, Date.valueOf(utente.getDataNascita()));
           preparedStatement.setString(6, utente.getNumCarta());
           if(utente.getDataScadenza() == null){
               preparedStatement.setDate(7, null);
           }
           else{
               preparedStatement.setDate(7, Date.valueOf(utente.getDataScadenza()));
           }

           preparedStatement.setString(8, utente.getCVV());
           preparedStatement.setString(9, utente.getNomeCarta());
           preparedStatement.setString(10, utente.getCognomeCarta());
           preparedStatement.setString(11, utente.getCap());
           preparedStatement.setString(12, utente.getVia());
           preparedStatement.setString(13, utente.getCitta());
           preparedStatement.setString(14, utente.getTipo());

           preparedStatement.setString(15, utente.getUsername());

           preparedStatement.executeUpdate();

        }
    }

    @Override
    public boolean doDelete(String code) throws SQLException {
        int result;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE username = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);

            result = preparedStatement.executeUpdate();
        }

        return result != 0;
    }

    private UtenteBean getUtenteBean(String code, UtenteBean utenteBean, String query) throws SQLException {
        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst())
                return null;

            resultSet.next();

            setUtente(resultSet, utenteBean);
        }

        return utenteBean;
    }

    private void setUtente(ResultSet resultSet, UtenteBean utenteBean) throws SQLException {
        utenteBean.setUsername(resultSet.getString("username"));
        utenteBean.setPwd(resultSet.getString("pwd"));
        utenteBean.setNome(resultSet.getString("nome"));
        utenteBean.setCognome(resultSet.getString("cognome"));
        utenteBean.setEmail(resultSet.getString("email"));
        utenteBean.setDataNascita((resultSet.getDate("dataNascita").toLocalDate()));
        utenteBean.setNomeCarta((resultSet.getString("nomeCarta")));
        utenteBean.setCognomeCarta((resultSet.getString("cognomeCarta")));
        utenteBean.setNumCarta(resultSet.getString("numCarta"));
        if (resultSet.getDate("dataScadenza") == null)
            utenteBean.setDataScadenza(null);
        else
            utenteBean.setDataScadenza((resultSet.getDate("dataScadenza").toLocalDate()));
        utenteBean.setCVV((resultSet.getString("CVV")));
        utenteBean.setCap(resultSet.getString("cap"));
        utenteBean.setVia(resultSet.getString("via"));
        utenteBean.setCitta(resultSet.getString("citta"));
        utenteBean.setTipo(resultSet.getString("tipo"));
    }

    private void setUtenteStatement(UtenteBean utenteBean, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, utenteBean.getUsername());
        preparedStatement.setString(2, utenteBean.getPwd());
        preparedStatement.setString(3, utenteBean.getNome());
        preparedStatement.setString(4, utenteBean.getCognome());
        preparedStatement.setString(5, utenteBean.getEmail());
        preparedStatement.setDate(6, Date.valueOf(utenteBean.getDataNascita()));
        preparedStatement.setString(7, utenteBean.getNomeCarta());
        preparedStatement.setString(8, utenteBean.getCognomeCarta());
        preparedStatement.setString(9, utenteBean.getNumCarta());
        if(utenteBean.getDataScadenza() == null)
            preparedStatement.setDate(10, null);
        else
            preparedStatement.setDate(10, Date.valueOf(utenteBean.getDataScadenza()));
        preparedStatement.setString(11, utenteBean.getCVV());
        preparedStatement.setString(12, utenteBean.getCap());
        preparedStatement.setString(13, utenteBean.getVia());
        preparedStatement.setString(14, utenteBean.getCitta());
        preparedStatement.setString(15, utenteBean.getTipo());
    }
}