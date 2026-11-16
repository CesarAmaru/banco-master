/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.service;

import br.com.dao.ConexaoFactory;
import br.com.dao.ContaDAO;
import br.com.dao.TransacaoDAO;
import br.com.entidade.Conta;
import br.com.entidade.TipoTransacao;
import br.com.entidade.Transacao;
import br.com.exception.PersistenciaException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Fluxo de depósito, saque, transferência e extrato, com controle de transação SQL. (O comentário antigo desta classe estava trocado com o da {@code ContaService}.)
 *
 * <b>Camada:</b> Service (regras de fluxo)
 * <br><b>Responsável:</b> Integrante 4
 * <p>
 * <b>O que esta classe deve fazer:</b>
 * <ul>
 *   <li>Atributos: {@code ContaDAO contaDAO} e {@code TransacaoDAO transacaoDAO}; construtor que os recebe.</li>
 *   <li>{@code depositar(String numeroConta, BigDecimal valor)}: valida valor, abre conexão, {@code setAutoCommit(false)}, busca a conta, {@code conta.depositar(valor)}, {@code atualizarSaldo}, registra DEPOSITO, {@code commit}. Em erro: {@code rollback} e relança.</li>
 *   <li>{@code sacar(String numeroConta, BigDecimal valor)}: mesmo fluxo com {@code conta.sacar(valor)} e registro SAQUE.</li>
 *   <li>{@code transferir(String origem, String destino, BigDecimal valor)}: valida valor e origem != destino; saca da origem, deposita no destino, atualiza as DUAS contas, registra TRANSFERENCIA, {@code commit}. Qualquer erro: {@code rollback} (ou tudo acontece, ou nada).</li>
 *   <li>{@code listarTransacoes(String numeroConta)} e {@code listarPorPeriodo(...)}: repassam ao {@code TransacaoDAO} (usados pela {@code TelaExtrato}).</li>
 *   <li>Privados: {@code validarValor(BigDecimal)} (lança exceção se nulo ou {@code <= 0}) e {@code buscarContaOuFalhar(Connection, String)}.</li>
 * </ul>
 * <b>Atenção:</b>
 * <ul>
 *   <li>Todos os DAOs de uma mesma operação usam a MESMA {@code Connection}; senão o rollback não funciona.</li>
 *   <li>A validação de saldo fica na entidade ({@code Conta.sacar}); o service só chama.</li>
 *   <li>Para testar o rollback: force um erro no meio da transferência e confirme no banco que nenhum saldo mudou.</li>
 * </ul>
 *
 * 
 */
public class TransacaoService {
    // TODO: implementar conforme descrito acima.
    private final ContaDAO contaDAO;
    private final TransacaoDAO transacaoDAO;
    
    public TransacaoService(){
        this(new ContaDAO(), new TransacaoDAO());
    }
    
    public TransacaoService(ContaDAO contaDAO, TransacaoDAO transacaoDAO){
        this.contaDAO = contaDAO;
        this.transacaoDAO = transacaoDAO;
    }
    
    public void depositar(String numeroConta, BigDecimal valor){
        validarValor(valor);
        
        emTransacao(con -> {
            Conta conta = buscarContaOuFalhar(con, numeroConta, "Conta");
            
            conta.depositar(valor);
            contaDAO.atualizarSaldo(con, conta);
            transacaoDAO.registrar(con, new Transacao(
                TipoTransacao.DEPOSITO, valor, null, conta.getId()));   
        });
    }
    
    private interface OperacaoNoBanco{
        void executar(Connection con);
    }
    
    private void emTransacao(OperacaoNoBanco operacao){
        try(Connection con = ConexaoFactory.getConnection()){
            con.setAutoCommit(false);
            try{
                operacao.executar(con);
                con.commit();
            }catch(SQLException | RuntimeException e){
                desfazer(con, e);
                throw e;
            }
        }catch(SQLException e){
            throw new PersistenciaException("Erro ao processar a operação: " + e.getMessage(), e);   
        }
    }
    
    private void desfazer(Connection con, Exception erroOriginal){
        try{
            con.rollback();
        }catch(SQLException ex){
            erroOriginal.addSuppressed(ex);
        }
    }
    
    private void validarValor(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor deve ser maior que zero!");
        }
    }
    
    private void validarNumero(String numeroConta){
        if(numeroConta == null || numeroConta.isBlank()){
            throw new IllegalArgumentException("Informe o número da conta!");
        }
    }
    
    private Conta buscarContaOuFalhar(Connection con, String numero, String papel){
        validarNumero(numero);
        Conta conta = contaDAO.buscarPorNumero(con, numero.trim());
        if(conta == null){
            throw new IllegalArgumentException(papel + " não encontrada: " + numero.trim());
        }
        return conta;
    }
    
    private void exigirAtiva(Conta conta, String papel){
        if(!conta.estaAtiva()){
            throw new IllegalStateException("A conta " + papel + " não está ativa!");
        }
    }
    
    public void sacar(String numeroConta, BigDecimal valor){
        validarValor(valor);
        emTransacao(con ->{
            Conta conta = buscarContaOuFalhar(con, numeroConta, "Conta");
            conta.sacar(valor);
            contaDAO.atualizarSaldo(con, conta);
            transacaoDAO.registrar(con, new Transacao(
                    TipoTransacao.SAQUE, valor, conta.getId(), null));
        });
    }
    
    public void transferir(String numeroOrigem, String numeroDestino, BigDecimal valor){
        validarValor(valor);
        
        if(numeroOrigem == null || numeroOrigem.isBlank()|| numeroDestino == null || numeroDestino.isBlank()){
            throw new IllegalArgumentException("Informe a conta de origem e a conta de destino!");
        }
        if(numeroOrigem.trim().equals(numeroDestino.trim())){
            throw new IllegalArgumentException("A conta de origem e a de destino devem ser diferentes!");
        }
        
        emTransacao(con -> {
            Conta origem = buscarContaOuFalhar(con, numeroOrigem, "Conta de origem");
            Conta destino = buscarContaOuFalhar(con, numeroDestino, "Conta de destino");
            
            exigirAtiva(origem, "de origem");
            exigirAtiva(destino, "de destino");
            
            origem.sacar(valor);
            destino.depositar(valor);
            
            contaDAO.atualizarSaldo(con, origem);
            contaDAO.atualizarSaldo(con, destino);
            transacaoDAO.registrar(con, new Transacao(
                    TipoTransacao.TRANSFERENCIA, valor, origem.getId(), destino.getId()));
        });
        
    }
    
    public List<Transacao> listarTransacoes(String numeroConta){
        validarNumero(numeroConta);
        return transacaoDAO.listarPorConta(numeroConta.trim());
    }
    
    public List<Transacao> listarTransacoes(String numeroConta, LocalDate inicio, LocalDate fim){
        validarNumero(numeroConta);
        if(inicio != null && fim != null && inicio.isAfter(fim)){
            throw new IllegalArgumentException("A data inicial não pode ser depois da data final.");
        }
        return transacaoDAO.listarPorPeriodo(numeroConta.trim(), inicio, fim);
    }
   
}

