import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ControllerUtilizador {
    private Map<String, Integer> inventario;
    private ArrayList<Encomenda> encomendasRecebidas;
    private ArrayList<Encomenda> encomendasEnviadas;
    private double valorTotalVendido;
    private double valorTotalComprado;

    public ControllerUtilizador() {
        inventario = new HashMap<String, Integer>();
        encomendasRecebidas = new ArrayList<Encomenda>();
        encomendasEnviadas = new ArrayList<Encomenda>();
        valorTotalVendido = 0;
        valorTotalComprado = 0;
    }

    public void adicionarArtigo(String nomeArtigo, int quantidade) {
        if (inventario.containsKey(nomeArtigo)) {
            int quantidadeAtual = inventario.get(nomeArtigo);
            inventario.put(nomeArtigo, quantidadeAtual + quantidade);
        } else {
            inventario.put(nomeArtigo, quantidade);
        }
    }

    public void removerArtigo(String nomeArtigo, int quantidade) {
        if (inventario.containsKey(nomeArtigo)) {
            int quantidadeAtual = inventario.get(nomeArtigo);
            if (quantidadeAtual >= quantidade) {
                inventario.put(nomeArtigo, quantidadeAtual - quantidade);
            } else {
                System.out.println("Quantidade insuficiente no inventário.");
            }
        } else {
            System.out.println("Artigo não encontrado no inventário.");
        }
    }

    public void registarEncomendaRecebida(Encomenda encomenda) {
        encomendasRecebidas.add(encomenda);
    }

    public void registarEncomendaEnviada(Encomenda encomenda) {
        encomendasEnviadas.add(encomenda);
    }

    public void publicarArtigo(String id, double preco) {
        Artigo artigo = new Artigo(id, preco);
        // adicionar o artigo a um sistema de publicação de artigos (não implementado aqui)
    }

    public void comprarArtigo(String id, int quantidade, double preco) {
        adicionarArtigo(id, quantidade);
        valorTotalComprado += quantidade * preco;
    }

    public void venderArtigo(String id, int quantidade, double preco) {
        removerArtigo(id, quantidade);
        valorTotalVendido += quantidade * preco;
    }

    public double getValorTotalVendido() {
        return valorTotalVendido;
    }

    public double getValorTotalComprado() {
        return valorTotalComprado;
    }

    public static void main(String[] args) {
        ControllerUtilizador sistema = new ControllerUtilizador();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de vendas.");

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Adicionar artigo");
            System.out.println("2 - Remover artigo");
            System.out.println("3 - Registar encomenda recebida");
            System.out.println("4 - Registar encomenda enviada");
            System.out.println("5 - Publicar artigo");
            System.out.println("6 - Comprar artigo");
            System.out.println("7 - Vender artigo");
