import java.io.File;
import java.io.IOException;
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
        File file = new File("sys.obj");
        if(file.exists()){
            try{
                this.setModel(this.getModel().load("sys.obj"));
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Erro ao ler ficheiro: " + e);

                throw new RuntimeException(e);
            }
        }

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
        String y;

        System.out.println("Queres guardar as mudanças [y/n]?");
        y = scin.nextLine();

        if(y.equals("y")){
            try{
                this.getModel().save("sys.obj");
            } catch (IOException e){
                System.out.println("Erro a registar ficheiro: " + e);
            }
        }

        System.out.println("Turning off...");

    }

    private void admin(){
        String login;

        System.out.println("Login key: ");
        login = scin.nextLine();

        if(login.equals("admin")){
            NewMenu adminMenu = new NewMenu(new String[]{
                    "Ver usuarios registados", "Eliminar usuário",
                    "Ver todas as transportadoras", "Eliminar transportadora"
            });

            adminMenu.setHandler(1, this::see_users);
            adminMenu.setHandler(2, this::del_user);
            adminMenu.setHandler(3, this::see_transportadora);
            adminMenu.setHandler(4, this::del_transportadora);

            adminMenu.run();
        } else{
            System.out.println("Login Inválido!");
        }
    }

    private void see_transportadora(){
        System.out.println(model.getTransportadora());
    }

    private void del_transportadora(){
        String id;

        System.out.println("Número da transportadora a eliminar: ");
        id = scin.nextLine();

        this.getModel().getTransportadora().remove(id);
    }

    private void see_users(){
        System.out.println(model.getUser());
    }

    private  void del_user(){
        String email;

        System.out.println("E-mail/ID do user a eliminar: ");
        email = scin.nextLine();

        model.DelUser(email);
    }

    private void login(){
        NewMenu loginMenu = new NewMenu(new String[]{
                 "User" , "Transportadora", "Admin"
        });

        loginMenu.setHandler(1, this::login_user);
        loginMenu.setHandler(2, this::login_transportadora);
        loginMenu.setHandler(3, this::admin);

        loginMenu.setPreCondition(1, ()-> this.getModel().getUser().size()>0);
        loginMenu.setPreCondition(2, ()-> this.getModel().getTransportadora().size()>0);

        loginMenu.run();
    }

    private void login_transportadora(){
        String id;

        System.out.println("Número da transportadora: ");
        id = scin.nextLine();

        if(this.getModel().getTransportadora().containsKey(id)){
            Transportadoras logged = model.getTransportadora().get(id);

            NewMenu userMenu = new NewMenu(new String[]{
                    "Dados", "Alterar Dados"
            });

            userMenu.setHandler(1, ()->this.details_transportadora(logged));
            userMenu.setHandler(2, ()->this.change_config_transportadora(logged));

            userMenu.run();
        } else{
            System.out.println("Transportadora ainda não registada.");
        }
    }

    private void details_transportadora(Transportadoras logged){
        if(logged.getPremium()){
            System.out.println("Imposto = " + logged.getImposto() + "\n"
                    + "Dias de atraso = " + logged.getDiasAtraso() + "\n"
                    + "Valores Base = " + logged.getValorBase().getPequeno() + ", "
                    + logged.getValorBase().getMedio() + ", "
                    + logged.getValorBase().getGrande() + ", " + "\n"
                    + "Valores de Expedição = " + logged.getPrecoExp().getPequeno() + ", "
                    + logged.getPrecoExp().getMedio() + ", "
                    + logged.getPrecoExp().getGrande() + ", " + "\n"
                    + "Valores de Expedição Premium = " + logged.getPrecoPremium().getPequeno() + ", "
                    + logged.getPrecoPremium().getMedio() + ", "
                    + logged.getPrecoPremium().getGrande() + "\n"
                    + "Formula: " + logged.getFormula() + "\n"
                    + "Formula Premium: " + logged.getFpremium()
            );
        } else{
            System.out.println("Imposto = " + logged.getImposto() + "\n"
                    + "Dias de atraso = " + logged.getDiasAtraso() + "\n"
                    + "Valores Base = " + logged.getValorBase().getPequeno() + ", "
                    + logged.getValorBase().getMedio() + ", "
                    + logged.getValorBase().getGrande() + ", " + "\n"
                    + "Valores de Expedição = " + logged.getPrecoExp().getPequeno() + ", "
                    + logged.getPrecoExp().getMedio() + ", "
                    + logged.getPrecoExp().getGrande() + "\n"
                    + "Formula: " + logged.getFormula()
            );
        }

    }

    private void change_config_transportadora(Transportadoras logged){
        NewMenu config_trans_Menu = new NewMenu(new String[]{
                "Ativar/Desativar Premium" , "Mudar Valores Base", "Mudar Formula Expedição", "Mudar Formula Expedição Premium"
        });

        config_trans_Menu.setHandler(1, ()-> this.premium_transportadora(logged));
        config_trans_Menu.setHandler(2, ()-> this.base_transportadora(logged));
        config_trans_Menu.setHandler(3, ()-> this.formula_transportadora(logged));
        config_trans_Menu.setHandler(4, ()-> this.formula_premium_transportadora(logged));

        config_trans_Menu.setPreCondition(4, logged::getPremium);

        config_trans_Menu.run();
    }

    private void formula_premium_transportadora(Transportadoras logged){
        System.out.println("Formula nova para os preços de expedição Premium:\nkeys:\nvalor - valor base dos 3 tamanhos\nimposto - imposto");
        String formula = scin.nextLine();
        logged.setFpremium(formula);
        logged.formulaPremium(formula);

        System.out.println(logged.getPrecoPremium());
    }

    private void formula_transportadora(Transportadoras logged){
        System.out.println("Formula nova para os preços de expedição:\nkeys:\nvalor - valor base dos 3 tamanhos\nimposto - imposto");
        String formula = scin.nextLine();
        logged.setFormula(formula);
        logged.formula(formula);

        System.out.println(logged.getPrecoExp());
    }

    private void base_transportadora(Transportadoras logged){
        String base;

        System.out.println("Preço para encomendas pequenas (1 artigo): ");
        base = scin.nextLine();
        logged.getValorBase().setPequeno(Double.parseDouble(base));
        System.out.println("Preço para encomendas médias (2 a 5 artigos): ");
        base = scin.nextLine();
        logged.getValorBase().setMedio(Double.parseDouble(base));
        System.out.println("Preço para encomendas grandes (mais que 5 artigos): ");
        base = scin.nextLine();
        logged.getValorBase().setGrande(Double.parseDouble(base));

        logged.formula(logged.getFormula());

        if(logged.getPremium()){
            logged.formulaPremium(logged.getFpremium());
            System.out.println("Valores base: " + logged.getValorBase() + "\n" + "Valores expedição: " +
                    logged.getPrecoExp() +"\n"+ "Valores expedição Premium: " + logged.getPrecoPremium());
        }else{
            System.out.println("Valores base: " + logged.getValorBase() + "\n" + "Valores expedição: " +
                    logged.getPrecoExp());
        }

    }

    private void premium_transportadora(Transportadoras logged){
        if(logged.getPremium()){
            logged.desativaPremium();
        } else{
            logged.ativaPremium();
        }
    }

    private void login_user(){
        String email;

        System.out.println("E-mail/ID: ");
        email = scin.nextLine();

        if(this.getModel().existsEmail(email) || this.getModel().getUser().containsKey(email)){
            Utilizador logged = model.getUser(email);

            NewMenu userMenu = new NewMenu(new String[]{
                    "Dados Pessoais", "Alterar Configurações", "Central de Cliente", "Centro de Vendedor"
            });

            userMenu.setHandler(1, () -> this.user_details(logged));
            userMenu.setHandler(2, () -> this.user_change_config(logged));
            userMenu.setHandler(3, () -> this.user_central_cliente(logged));
            userMenu.setHandler(4, () -> this.user_central_vendedor(logged));

            userMenu.run();
        } else{
            System.out.println("E-mail ainda não registado.");
        }
    }

    private void user_central_cliente(Utilizador logged){
        NewMenu userMenu = new NewMenu(new String[]{
                "Encomendar", "Ver produtos comprados"
        });

        userMenu.setHandler(1, () -> this.user_encomendar(logged));
        userMenu.setHandler(2, () -> this.user_bought(logged));

        userMenu.run();
    }

    private void user_central_vendedor(Utilizador logged){
        NewMenu userMenu = new NewMenu(new String[]{
                "Criar novo artigo", "Publicar artigo",
                "Remover artigo", "Ver receita",
                "Ver produtos vendidos", "Ver produtos á venda"
        });

        userMenu.setHandler(1, () -> this.user_new_artigo(logged));
        userMenu.setHandler(2, () -> this.user_publish_artigo(logged));
        userMenu.setHandler(3, () -> this.user_rm_artigo(logged));
        userMenu.setHandler(4, () -> this.user_receita(logged));
        userMenu.setHandler(5, () ->this.user_sold(logged));
        userMenu.setHandler(6, () -> this.user_selling(logged));

        userMenu.run();
    }

    private void user_details(Utilizador logged){
            System.out.println("Email = " + logged.getEmail() + "\n"
                    + "Nome = " + logged.getNome() + "\n"
                    + "Morada = " + logged.getMorada() + "\n"
                    + "Nif = " + logged.getNif() + "\n"
                    + "User ID = " + logged.getID() + "\n"
                    + "Receota: " + logged.getRevenue() + "\n"
                    + "Produtos a venda = " + logged.getProdutosAVenda()+ "\n"
                    + "Vendas efetuadas =" + logged.getVendasEfetuadas() + "\n"
                    + "Artigos = " + logged.getArtigos() + "\n"
                    + "Encomendas Realizadas = " + logged.getEncomendas()
            );
    }

    private void user_change_config(Utilizador logged){
        NewMenu config_user_Menu = new NewMenu(new String[]{
                "Mudar email" , "Mudar nome", "Mudar morada", "Mudar nif"
        });

        config_user_Menu.setHandler(1, ()-> this.email_user(logged));
        config_user_Menu.setHandler(2, ()-> this.nome_user(logged));
        config_user_Menu.setHandler(3, ()-> this.morada_user(logged));
        config_user_Menu.setHandler(4, ()-> this.nif_user(logged));

        config_user_Menu.run();
    }

    private void email_user(Utilizador logged){
        System.out.println("Introduza o novo email:");
        String email = scin.nextLine(), cancel = "";
        while(!(email.contains("@")) || this.getModel().existsEmail(email)){
            if(!(email.contains("@"))){
                System.out.println("E-mail Inválido! (não contém @)");
            }if(this.getModel().existsEmail(email)){
                System.out.println("Este e-mail já esta registado!");
            }
            System.out.println("Cancelar a mudança [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("E-mail: ");
            email = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        logged.setEmail(email);
        System.out.println("Novo email: " + logged.getEmail());
    }
    private void nome_user(Utilizador logged){
        System.out.println("Introduza novo nome:");
        String nome = scin.nextLine();
        logged.setNome(nome);
        System.out.println("Novo nome: " + logged.getNome());
    }
    private void morada_user(Utilizador logged){
        System.out.println("Introduza nova morada:");
        String m = scin.nextLine();
        logged.setMorada(m);
        System.out.println("Nova morada: " + logged.getMorada());
    }
    private void nif_user(Utilizador logged){
        System.out.println("Introduza novo nif:");
        String nif = scin.nextLine(), cancel = "";
        while(nif.length() != 9){
            System.out.println("Nif Inválido!");
            System.out.println("Cancelar a mudança [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Nif (123456789): ");
            nif = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        logged.setNif(nif);
        System.out.println("Novo nif: " + logged.getNif());
    }

    private void user_new_artigo(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_publish_artigo(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_rm_artigo(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_encomendar(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_receita(Utilizador logged){
        System.out.println("Receita até ao momento: " + logged.getRevenue());
    }

    private void user_bought(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_sold(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void user_selling(Utilizador logged){
        System.out.println("opção por implementar");
    }

    private void registar(){
        NewMenu registarMenu = new NewMenu(new String[]{
                "User", "Transportadora"
        });

        registarMenu.setHandler(1, this::registar_user);
        registarMenu.setHandler(2, this::registar_transportadora);

        registarMenu.run();

    }

    private void registar_user(){
        String email= "", nome, morada, nif="", cancel = "";
        System.out.println("Introduza o novo email:");
        email = scin.nextLine();
        while(!(email.contains("@")) || this.getModel().existsEmail(email)){
            if(!(email.contains("@"))){
                System.out.println("E-mail Inválido! (não contém @)");
            }if(this.getModel().existsEmail(email)){
                System.out.println("Este e-mail já esta registado!");
            }
            System.out.println("Cancelar o registo [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("E-mail: ");
            email = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        System.out.println("Nome: ");
        nome = scin.nextLine();
        System.out.println("Morada: ");
        morada = scin.nextLine();

        System.out.println("Introduza o nif:");
        nif = scin.nextLine();
        while(nif.length() != 9){
            System.out.println("Nif Inválido!");
            System.out.println("Cancelar o registo [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Nif (123456789): ");
            nif = scin.nextLine();
        }
        if(cancel.contains("y")) return;

        Utilizador user = new Utilizador(email, nome, morada, nif);
        model.RegistarUser(user);

        System.out.println("Id de login: " + user.getID());
    }

    private void registar_transportadora(){
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
        transportadora.setFormula(formula);
        transportadora.formula(formula);

        System.out.println("Ativar Premium [y/n]? ");
        premium = scin.nextLine();
        if(premium.equals("y")) {
            transportadora.ativaPremium();
            System.out.println("Formula de Cálculo Premium\nkeys:\nvalor - valor base dos 3 tamanhos\nimposto - imposto");
            formula = scin.nextLine();
            transportadora.setFpremium(formula);
            transportadora.formulaPremium(formula);
        }

        System.out.println("Dias de atraso de envio em relação à compra: ");
        diasatraso = scin.nextLine();

        transportadora.setDiasAtraso(Integer.parseInt(diasatraso));

        System.out.println("Numero de login: " + this.getModel().getTransportadora().size());

        model.addTransportadora(transportadora);
    }

    private sys getModel() {
        return model;
    }

    private void setModel(sys model) {
        this.model = model;
    }

    private Scanner getScin() {
        return scin;
    }

    private void setScin(Scanner scin) {
        this.scin = scin;
    }
}
