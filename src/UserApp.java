import jdk.jshell.execution.Util;

import java.util.Scanner;

public class UserApp {
    private sys model;
    private Scanner scin;

    public static void main(String[] args) {
        UserApp app = new UserApp();
        app.run();
    }
    private UserApp(){
        model = new sys();
        scin = new Scanner(System.in);
    }

    private void run(){
        NewMenu mainMenu = new NewMenu(new String[]{
                "Log-in", "Registar"
        });

        mainMenu.setHandler(1, this::login);
        mainMenu.setHandler(2, this::registar);

        mainMenu.setPreCondition(1, ()-> model.getUser().size()  > 0 || model.getTransportadora().size() > 0);

        mainMenu.run();

        closeApp();
    }

    private void closeApp(){
        System.out.println("bye bye, registar num ficheiro");
    }

    private void admin(){
        String login;

        System.out.println("Login key: ");
        login = scin.nextLine();

        if(login.equals("admin")){
            NewMenu adminMenu = new NewMenu(new String[]{
                    "Ver usuarios registados", "Eliminar usuário"
            });

            adminMenu.setHandler(1, this::seeusers);
            adminMenu.setHandler(2, this::deluser);

            adminMenu.run();
        } else{
            System.out.println("Login Inválido!");
        }
    }

    private void seeusers(){
        System.out.println(model.getUser());
    }

    private  void deluser(){
        String email;

        System.out.println("E-mail do user a eliminar: ");
        email = scin.nextLine();

        model.DelUser(email);
    }

    private void login(){
        NewMenu loginMenu = new NewMenu(new String[]{
                 "User" , "Transportadora", "Admin"
        });

        loginMenu.setHandler(1, this::loginuser);
        //loginMenu.setHandler(2, this::logintransportadora);
        loginMenu.setHandler(3, this::admin);

        loginMenu.run();
    }

    private void loginuser(){
        String email;

        System.out.println("E-mail: ");
        email = scin.nextLine();

        if(model.UserExists(email)){
            Utilizador logged = model.getUser(email);

            NewMenu userMenu = new NewMenu(new String[]{
                    "Dados Pessoais", "Alterar Configurações", "Criar Novo Artigo", "Publicar Artigo",
                    "Remover Artigo", "Fazer Encomenda", "Receita",
                    "Ver Produtos Comprados", "Ver produtos Vendidos", "Ver produtos Á Venda"
            });

            /*userMenu.setHandler(1, this::details);
            userMenu.setHandler(2, this::changeconfig);
            userMenu.setHandler(3, this::newartigo);
            userMenu.setHandler(4, this::addartigo);
            userMenu.setHandler(5, this::publishartigo);
            userMenu.setHandler(6, this::rmartigo);
            userMenu.setHandler(7, this::encomendar);
            userMenu.setHandler(8, this::receita);
            userMenu.setHandler(9, this::bought);
            userMenu.setHandler(10, this::sold);
            userMenu.setHandler(11, this::selling);*/
        } else{
            System.out.println("E-mail ainda não registado.");
        }
    }

    private void registar(){
        NewMenu registarMenu = new NewMenu(new String[]{
                "User", "Transportadora"
        });

        registarMenu.setHandler(1, this::registaruser);
        registarMenu.setHandler(2, this::registartransportadora);

        registarMenu.run();

    }

    public void registaruser(){
        String email= "", nome, morada, nif="";

        while(!(email.contains("@"))){
            System.out.println("E-mail: ");
            email = scin.nextLine();
            if(!(email.contains("@"))){
                System.out.println("E-mail Inválido! (não contém @)");
            }
        }
        System.out.println("Nome: ");
        nome = scin.nextLine();
        System.out.println("Morada: ");
        morada = scin.nextLine();

        while(nif.length() != 9){
            System.out.println("Nif (123456789): ");
            nif = scin.nextLine();
            if(nif.length() != 9){
                System.out.println("Nif Inválido!");
            }
        }
        model.RegistarUser(new Utilizador(email, nome, morada, nif));
    }

    public void registartransportadora(){
        String imposto, p, m, g, premium, formula, diasatraso;
        Transportadoras transportadora;

        System.out.println("Preço para encomendas pequenas (1 artigo): ");
        p = scin.nextLine();
        System.out.println("Preço para encomendas médias (2 a 5 artigos): ");
        m = scin.nextLine();
        System.out.println("Preço para encomendas grandes (mais que 5 artigos): ");
        g = scin.nextLine();
        System.out.println("Imposto: ");
        imposto = scin.nextLine();

        transportadora = new Transportadoras(Double.parseDouble(p),Double.parseDouble(m) ,Double.parseDouble(g),
                Double.parseDouble(imposto));

        System.out.println("Formula de Cálculo\nkeys:\nvalor - valor base dos 3 tamanhos\nimposto - imposto");
        formula = scin.nextLine();
        transportadora.formula(formula);

        System.out.println("Ativar Premium [y/n]? ");
        premium = scin.nextLine();
        if(premium.equals("y")) {
            transportadora.ativaPremium();
            System.out.println("Formula de Cálculo Premium\nkeys:\nvalor - valor base dos 3 tamanhos\nimposto - imposto");
            formula = scin.nextLine();
            transportadora.formulaPremium(formula);
        }

        System.out.println("Dias de atraso de envio em relação à compra: ");
        diasatraso = scin.nextLine();

        transportadora.setDiasAtraso(Integer.parseInt(diasatraso));

        model.addTransportadora(transportadora);
    }
}
