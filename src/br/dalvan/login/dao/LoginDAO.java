/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dalvan.login.dao;

import br.dalvan.login.entity.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalvan
 */
public class LoginDAO {

    private final String SQL_INSERIR = "INSERT INTO login (usuario,senha,palavra_chave) VALUES (?,md5(?),md5(?))";
    private final String SQL_GET_BY_ID = "SELECT * FROM login WHERE idLogin = ?";
    private final String SQL_GET_ALL = "SELECT * FROM login  ";
    private final String SQL_UPDATE = "UPDATE login SET usuario=?, senha=md5(?), palavra_chave=md5(?) WHERE idLogin = ?";

    public Login insert(Login login) {

        PreparedStatement ps;
        ResultSet genereResultSetkeys;
        Connection con = Conexao.conectar();

        try {
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERIR, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, login.getUsuario());
            ps.setString(2, login.getSenha());
            ps.setString(3, login.getPalavraChave());

            ps.executeUpdate();

            genereResultSetkeys = ps.getGeneratedKeys();

            if (genereResultSetkeys.next()) {
                login.setId(genereResultSetkeys.getInt(1));
            } else {
                throw new SQLException("Criação de Tipo falhou, nao consegui obter o id.");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                Conexao.desconectar(con);
            } catch (Exception e) {

                e.printStackTrace();;
            }
        }
        return login;
    }

    public void update(Login login) {

        Connection con = null;
        PreparedStatement ps;

        try {
            con = Conexao.conectar();
            ps = (PreparedStatement) con.prepareStatement(SQL_UPDATE);
            ps.setString(1, login.getUsuario());
            ps.setString(2, login.getSenha());
            ps.setString(3, login.getPalavraChave());
            ps.setInt(4, login.getId());

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar(con);
        }

    }

    public boolean delete(int id) {
        Connection con = Conexao.conectar();

        try {
            Statement stm = con.createStatement();

            String sql = " DELETE FROM login WHERE idLogin = " + id + "";

            stm.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                Conexao.desconectar(con);

            } catch (Exception e) {

                e.printStackTrace();;
            }
        }
        return false;
    }

    public List<Login> getAll() {
        Connection con = null;
        PreparedStatement st;
        ResultSet rs;
        Login login;

        List<Login> lista = new ArrayList();

        try {
            con = Conexao.conectar();
            st = (PreparedStatement) con.prepareStatement(SQL_GET_ALL);
            rs = st.executeQuery();

            while (rs.next()) {
                login = new Login();
                login.setId(rs.getInt("idLogin"));
                login.setUsuario(rs.getString("usuario"));
                login.setSenha(rs.getString("senha"));
                login.setPalavraChave(rs.getString("palavra_chave"));

                lista.add(login);
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar(con);
        }

        return lista;

    }

    public Login getById(int id) {

        Connection con = null;
        PreparedStatement st;
        ResultSet rs;
        Login login = null;
        try {
            con = Conexao.conectar();
            st = (PreparedStatement) con.prepareStatement(SQL_GET_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                login = new Login();
                login.setId(rs.getInt("idLogin"));
                login.setUsuario(rs.getString("usuario"));
                login.setSenha(rs.getString("senha"));
                login.setPalavraChave(rs.getString("palavra_chave"));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar(con);
        }
        return login;
    }
}
