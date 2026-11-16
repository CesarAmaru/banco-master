/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.entidade.Transacao;
import br.com.exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import br.com.entidade.TipoTransacao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao banco para a tabela {@code transacao} (movimentações).
 *
 * <b>Camada:</b> DAO (persistência)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>{@code void registrar(Connection con, Transacao t)}: {@code INSERT} usando a conexão recebida; grava {@code NULL} em origem/destino quando forem nulos.</li>
 *   <li>{@code List<Transacao> listarPorConta(String numeroConta)}: movimentações em que a conta é origem ou destino, em ordem de data.</li>
 *   <li>{@code List<Transacao> listarPorPeriodo(String numeroConta, LocalDate inicio, LocalDate fim)}: para o filtro do extrato.</li>
 *   <li>Método privado {@code montarTransacao(ResultSet rs)}: converte uma linha do banco em objeto (evita repetir código).</li>
 *   <li>Para a tela de extrato (Integrante 5): uma consulta com {@code JOIN} entre {@code transacao}, {@code conta} e {@code cliente} para trazer também o nome do titular.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>{@code registrar} NÃO abre conexão própria: usa a recebida, para participar do {@code commit}/{@code rollback} do service.</li>
 *   <li>Não existe método de {@code UPDATE} nem {@code DELETE}: transação registrada não se edita nem se apaga.</li>
 *   <li>Coluna {@code tipo} guarda o texto do enum; {@code conta_origem_id} e {@code conta_destino_id} aceitam {@code NULL}.</li>
 * </ul>
 *
 * 
 */
public class TransacaoDAO {
    // TODO: implementar conforme descrito acima.
    public void registrar(Connection con, Transacao t){
        String sql = "INSERT INTO transacao "
                + "(tipo, valor, data_hora, conta_origem_id, conta_destino_id"
                + "VALUES (?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, t.getTipo().name());
            stmt.setBigDecimal(2, t.getValor());
            stmt.setTimestamp(3, Timestamp.valueOf(t.getDataHora()));
            
            if(t.getContaOrigemId() == null){
                stmt.setNull(4, Types.INTEGER);
            }else{
                stmt.setInt(4, t.getContaOrigemId());
            }
            
            if(t.getContaDestinoId() == null){
                stmt.setNull(5, Types.INTEGER);
            }else{
                stmt.setInt(5, t.getContaDestinoId());
            }
            
            stmt.executeUpdate();
            
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    t.setId(rs.getInt(1));
                }
            }
        }catch(SQLException e){
            throw  new PersistenciaException(
                    "Erro ao registrar a movimentação: " + e.getMessage(), e);
        }
    }
    
    public List<Transacao> listarPorConta(String numeroConta){
        return consultar(numeroConta, null, null);
    }
    
    public List<Transacao> listarPorPeriodo(String numeroConta, LocalDate inicio, LocalDate fim){
        LocalDateTime de = (inicio == null) ? null : inicio.atStartOfDay();
        LocalDateTime ate = (fim == null) ? null : fim.plusDays(1).atStartOfDay();
        
        return consultar(numeroConta, de, ate);
    }
    
    private List<Transacao> consultar(String numeroConta, LocalDateTime de, LocalDateTime ate){
        StringBuilder sql = new StringBuilder(
        "SELECT t.id, t.tipo, t.valor, t.data_hora "
        + "t.conta_origem_id, t.conta_destino_id, "
        + "co.numero AS numero_origem, clo.nome AS nome_origem, "
        + "cd.numero AS numero_destino, cld.nome AS nome_destino "
        +"FROM transacao t "
        +"LEFT JOIN conta co ON co.id = t.conta_origem_id "
        +"LEFT JOIN cliente clo ON clo.id = co.cliente_id "
        +"LEFT JOIN conta cd ON cd.id = t.conta_destino_id "
        +"LEFT JOIN cliente cld ON cld.id = cd.cliente_id "
        +"WHERE (co.numero = ? OR cd.numero = ?) ");
    
    if(de != null){
        sql.append("AND t.data_hora >= ? ");
    }
    
    if(ate != null){
        sql.append("AND t.data_hora < ? ");
    }
    sql.append("ORDER BY t.data_hora, t.id");
    
    List<Transacao> lista = new ArrayList<>();
    
    try(Connection con = ConexaoFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql.toString())){
        
        int i = 1;
        stmt.setString(i++, numeroConta);
        stmt.setString(i++, numeroConta);
        if(de != null){
            stmt.setTimestamp(i++, Timestamp.valueOf(de));
        }
        if(ate != null){
            stmt.setTimestamp(i++, Timestamp.valueOf(ate));
        }
        try(ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                lista.add(montarTransacao(rs));
            }
        }
    }
    
    catch(SQLException e){
        throw new PersistenciaException("Erro ao listar as movimentações realizadas pela conta.", e);
    }
    return lista;
    
    }
    
    private Transacao montarTransacao(ResultSet rs) throws SQLException{
        Transacao t = new Transacao();
        
        t.setId(rs.getInt("id"));
        t.setTipo(TipoTransacao.valueOf(rs.getString("tipo")));
        t.setValor(rs.getBigDecimal("valor"));
        t.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
        
        int origemId = rs.getInt("conta_origem_id");
        t.setContaOrigemId(rs.wasNull() ? null : origemId);
        
        int destinoId = rs.getInt("conta_destino_id");
        t.setContaDestinoId(rs.wasNull() ? null : destinoId);
        
        t.setNumeroContaOrigem(rs.getString("numero_origem"));
        t.setNomeClienteOrigem(rs.getString("nome_origem"));
        t.setNumeroContaDestino(rs.getString("numero_destino"));
        t.setNomeClienteDestino(rs.getString("nome_destino"));
        
        return t;

    }
}

