package moedeiro;

import java.util.Map;

import moedeiro.exception.ExcessoMoedasException;
import moedeiro.exception.MoedasInsuficientesException;
import moedeiro.exception.MoedeiroException;
import moedeiro.exception.QuantidadeInvalidaException;
import moedeiro.validator.ValidarMoedasValidator;

public class TuboMoeda {

    // Atributos
    private static final int CAPACIDADE_MAXIMA = 200;
    private final double valorMoeda;
    private int quantidade;
    private final Cofre cofreMoedas;

    // Gets e Sets
    public int getQuantidade() {
        return quantidade;
    }

    public double getValorMoeda() {
        return valorMoeda;
    }

    // Construtor
    public TuboMoeda(double valorMoeda, Cofre cofreMoedas) {
        this.valorMoeda = valorMoeda;
        this.quantidade = 0;
        this.cofreMoedas = cofreMoedas;
    }

    // Comportamentos
    public int adicionarMoeda(int adicionar) throws QuantidadeInvalidaException, ExcessoMoedasException {
        if (ValidarMoedasValidator.validarQuantidade(adicionar)) {
            if (quantidade + adicionar <= CAPACIDADE_MAXIMA) {
                quantidade += adicionar;
            } else {
                int excesso = quantidade + adicionar - CAPACIDADE_MAXIMA;
                quantidade = CAPACIDADE_MAXIMA;
                cofreMoedas.adicionarMoeda(valorMoeda, excesso);
                throw new ExcessoMoedasException("Quantidade excede a capacidade máxima do tubo.", excesso);
            }
        } else {
            throw new QuantidadeInvalidaException("A quantidade de moedas não pode ser negativa.");
        }
        return quantidade;
    }

    public void removerMoeda(int quantidade) throws MoedasInsuficientesException{
        if (quantidade > this.quantidade) {
            throw new MoedasInsuficientesException("Não há moedas suficientes no tubo.");
        }
        this.quantidade -= quantidade;
    }

    public double calcularValorTotal() {
        final double valorTotal = valorMoeda * quantidade;
        return valorTotal;
    }



    // Verificar se o cofre ainda tem o mesmo tipo de moeda e retornar mensagem
    public int getMoedasRestantesSeCofreVazio() {
        Integer numMoedas = cofreMoedas.getMoedasPorTipo().getOrDefault(valorMoeda, 0);
        return numMoedas == null ? 0 : numMoedas;
//
//        Map<Double, Integer> moedasNoCofre = cofreMoedas.contarMoedasPorTipo();
//
//        if (moedasNoCofre.containsKey(valorMoeda)) {
//            return "";
//        } else {
//            return "Moedas restantes no tubo: " + quantidade + ". O cofre não tem moedas de " + valorMoeda;
//        }
    }

    // Outras

}
