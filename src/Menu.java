import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    // variáveis de instância
    private List<String> opcoes;
    private List<Utilizador> utilizador;
    private int op;

    /**
     * Construtores
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    /**
     * Método para apresentar o menu e ler uma opção.
     *
     */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
            switch (this.op) {
                case 1:
                    iniciarSessao();
                    break;
                case 2:
                    criarUtilizador();
                    break;
            }
        } while (this.op != 0);
    }

    /** Apresentar o menu */

    private void showMenu() {
        System.out.println("\n *** Menu *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    /** Ler uma opção válida */
    private int lerOpcao() {
        int op;
        Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try {
            op = is.nextInt();
        }
        catch (InputMismatchException e) { // Não foi escrito um int
            op = -1;
        }
        if (op < 0 || op > this.opcoes.size()) {
            System.out.println("Opção Inválida!");
            op = -1;
        }
        return op;
    }

    /**
     * Método para obter a última opção lida
     */
    public int getOpcao() {
        return this.op;
    }

    /**
     * Método para criar um novo utilizador
     */
    private void criarUtilizador() {
        Scanner is = new Scanner(System.in);

        System.out.print("E-mail: ");
        String email = is.nextLine();

        System.out.print("Nome: ");
        String nome = is.nextLine();

        System.out.print("Morada: ");
        String morada = is.nextLine();

        System.out.print("NIF: ");
        int nif = is.nextInt();

        Utilizador utilizador = new Utilizador(nome, idade, nic);
        this.utilizador.add(utilizador);
        System.out.println("Utilizador criado com sucesso.");
    }
}