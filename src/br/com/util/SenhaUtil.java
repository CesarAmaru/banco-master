/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.util;

/**
 * Funções auxiliares para proteger senhas.
 *
 * Camada: Util (apoio)
 * 
 * O que esta classe deve fazer:
 * 
 *   Classe só com métodos {@code static}.
 *   {@code static String gerarHash(String senha)}: gera um salt aleatório ({@code SecureRandom}) e aplica SHA-256 ({@code MessageDigest}); devolve {@code salt + hash} em texto (Base64).
 *   {@code static boolean verificar(String senha, String hashGuardado)}: refaz o hash com o mesmo salt e compara.
 * 
 * Atenção:
 * 
 *   NUNCA guarde a senha em texto puro no banco. Mínimo de 6 caracteres (valide no controle).
 *   Quem chama: {@code ClienteControle} (cadastro) e {@code LoginControle} (login).
 * 
 */
public class SenhaUtil {
    // TODO: implementar conforme descrito acima.
}
