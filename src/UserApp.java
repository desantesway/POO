import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class UserApp {
    private sys model;
    private Scanner scin;

    public void viagem_tempo(){
        System.out.println("Era bom");
    }

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
                "Log-in", "Registar", "Viagem no Tempo"
        });

        mainMenu.setHandler(1, this::login);
        mainMenu.setHandler(2, this::registar);
        mainMenu.setHandler(3, this::viagem_tempo);

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
                    "Mudar % da vintage", "Ver % da vintage", "Ver usuarios registados", "Eliminar usuário",
                    "Ver todas as transportadoras", "Eliminar transportadora", "Ver todas as encomendas", "Ver todos artigos",
                    "Ver cardapio"
            });

            adminMenu.setHandler(1, this::change_cut_vintage);
            adminMenu.setHandler(2, this::see_vintage);
            adminMenu.setHandler(3, this::see_users);
            adminMenu.setHandler(4, this::del_user);
            adminMenu.setHandler(5, this::see_transportadora);
            adminMenu.setHandler(6, this::del_transportadora);
            adminMenu.setHandler(7, this::see_encomendas);
            adminMenu.setHandler(8, this::see_artigos);
            adminMenu.setHandler(9, this::encomenda_cardapio);

            adminMenu.run();
        } else{
            System.out.println("Login Inválido!");
        }
    }

    public void see_artigos(){
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            System.out.println("Email: " + entry.getValue().getEmail() + " criou os artigos: " + entry.getValue().getArtigos());
        }
    }

    public void see_encomendas(){
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            System.out.println(entry.getValue().getEncomendas());
        }
    }

    public void see_vintage(){
        System.out.println(this.getModel().getVintagecut() * 100 + " %");
    }

    public void change_cut_vintage(){
        String cut;
        System.out.println("Nova % da plataforma para cada venda: ");
        cut = scin.nextLine();
        this.getModel().setVintagecut((double) Integer.parseInt(cut) / 100);
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
                "Criar novo artigo", "Publicar/Privar artigo",
                "Remover artigo", "Ver receita",
                "Ver produtos vendidos", "Ver produtos á venda", "Alterar configurações de um artigo"
        });

        userMenu.setHandler(1, () -> this.user_new_artigo(logged));
        userMenu.setHandler(2, () -> this.user_publish_artigo(logged));
        userMenu.setHandler(3, () -> this.user_rm_artigo(logged));
        userMenu.setHandler(4, () -> this.user_receita(logged));
        userMenu.setHandler(5, () ->this.user_sold(logged));
        userMenu.setHandler(6, () -> this.user_selling(logged));
        userMenu.setHandler(7, () -> this.user_artigo_config(logged));

        userMenu.run();
    }

    private void user_artigo_config(Utilizador logged){
        System.out.println("Introduza o id do artigo a editar: ");
    }

    private void user_details(Utilizador logged){
            System.out.println("Email: " + logged.getEmail() + "\n"
                    + "Nome: " + logged.getNome() + "\n"
                    + "Morada: " + logged.getMorada() + "\n"
                    + "Nif: " + logged.getNif() + "\n"
                    + "User ID: " + logged.getID() + "\n"
                    + "Receita: " + logged.getRevenue() + "\n"
                    + "Produtos a venda: " + logged.getProdutosAVenda()+ "\n"
                    + "Vendas efetuadas: " + logged.getVendasEfetuadas() + "\n"
                    + "Artigos á venda: " + logged.getArtigos() + "\n"
                    + "Encomendas Realizadas: " + logged.getEncomendas()
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
                System.out.println("Este e-mail já está registado!");
            }
            System.out.println("Cancelar a mudança [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("E-mail: ");
            email = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        logged.setEmail(email);
        System.out.println("Novo e-mail: " + logged.getEmail());
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
        NewMenu config_user_Menu = new NewMenu(new String[]{
                "Mala" , "Tshirt", "Sapatilha"
        });
        config_user_Menu.setHandler(1, ()-> this.user_mala(logged));
        config_user_Menu.setHandler(2, ()-> this.user_tshirt(logged));
        config_user_Menu.setHandler(3, ()-> this.user_sapatilha(logged));

        config_user_Menu.run();
    }

    private void user_mala(Utilizador logged){
        Malas mala=new Malas();
        System.out.println("Introduza o tamanho da Mala (Pequeno, Medio, Grande): ");
        System.out.println("Se as medidas (0,0 -> 10,0 x 0,0 -> 15,0 x 0,0 -> 10,0) é Pequeno");
        System.out.println("Se as medidas (10,0 -> 15,0 x 10,0-> 15,0 x 10,0 -> 15,0) é Medio");
        System.out.println("Se as medidas (15,0 -> 20,0 x 15,0 -> 25,0 x 15,0 -> 20,0) é Grande");
        String tamanho=scin.nextLine(), cancel ="";
        while(!(tamanho.equals("Pequeno") || tamanho.equals("Medio") || tamanho.equals("Grande"))){
            System.out.println("Tamanho Inválido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Tamanho da mala (Pequeno, Medio, Grande):");
            tamanho = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        mala.setTamanho(tamanho);

        System.out.println("Introduza o material:");
        String material=scin.nextLine();
        mala.setMaterial(material);

        Artigo artigo = common_artigo();

        String bool;
        System.out.println("Deseja ativar premium [y/n]:");
        bool=scin.nextLine();
        if(bool.contains("y")){
            mala.ativaPremium();
            mala.setDataPremium(this.getModel().now());

            System.out.println("Valorização do premium ao ano (0-100): ");
            int val = Integer.parseInt(scin.nextLine());

            while(val < 0 || val > 100){
                System.out.println("A valorização só pode ser entre 0% e 100%");
                System.out.println("Cancelar a adição [y/n]?");
                cancel = scin.nextLine();
                if(cancel.contains("y")) break;
                System.out.println("Valorização: ");
                val = Integer.parseInt(scin.nextLine());
            }
            if(cancel.contains("y")) return;
            mala.setValorizacao(val);
        }

        mala.setNumeroDonos(artigo.getNumeroDonos());
        mala.setEstado(artigo.getEstado());
        mala.setBrand(artigo.getBrand());
        mala.setPrecobase(artigo.getPrecobase());
        mala.setDescricao(artigo.getDescricao());

        System.out.println("Tamanho da mala: " + mala.getTamanho());
        System.out.println("Material da mala: " + mala.getMaterial());
        if(mala.isPremium()) System.out.println("Valorização ao ano: " + mala.getValorizacao() + "%");

        System.out.println("Numero de donos: " + mala.getNumeroDonos());
        System.out.println("Estado: " + mala.getEstado());
        System.out.println("Marca: " + mala.getBrand());
        System.out.println("Precobase: " + mala.getPrecobase());
        System.out.println("Descricao do artigo: "+ mala.getDescricao());

        logged.addArtigo(mala);
    }

    private void user_tshirt(Utilizador logged){ //padrao
        TShirt tshirt = new TShirt();
        System.out.println("Introduza o tamanho da tshirt (S, M, L, XL, XXL):");
        String tamanho=scin.nextLine(), cancel ="";
        while(!(tamanho.equals("S") || tamanho.equals("M") || tamanho.equals("L") || tamanho.equals("XL") || tamanho.equals("XXL"))){
            System.out.println("Tamanho Inválido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Tamanho (S, M, L, XL, XXL):");
            tamanho = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        tshirt.setTamanho(tamanho);


        System.out.println("Introduza o padrao da TShirt (liso, riscas, palmeiras: ");
        String padrao=scin.nextLine();
        while(!(padrao.equals("palmeiras") || padrao.equals("riscas") || padrao.equals("liso"))){
            System.out.println("Padrão Inválido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Padrao: (liso, riscas, palmeiras: ");
            padrao = scin.nextLine();
        }
        tshirt.setPadrao(padrao);

        Artigo artigo = common_artigo();

        tshirt.setNumeroDonos(artigo.getNumeroDonos());
        tshirt.setEstado(artigo.getEstado());
        tshirt.setBrand(artigo.getBrand());
        tshirt.setPrecobase(artigo.getPrecobase());
        tshirt.setDescricao(artigo.getDescricao());

        System.out.println("Tamanho da tshirt: " + tshirt.getTamanho());
        System.out.println("Padrao da tshirt: " + tshirt.getPadrao());

        System.out.println("Numero de donos: " + tshirt.getNumeroDonos());
        System.out.println("Estado: " + tshirt.getEstado());
        System.out.println("Marca: " + tshirt.getBrand());
        System.out.println("Precobase: " + tshirt.getPrecobase());
        System.out.println("Descricao do artigo: "+ tshirt.getDescricao());

        logged.addArtigo(tshirt);
    }

    private void user_sapatilha(Utilizador logged){ //atacadores,cor

        Sapatilhas sapatilha=new Sapatilhas();
        System.out.println("Introduza o tamanho das sapatilhas (15...50):");
        String tam=scin.nextLine(), cancel= "", nd;
        int tamanho=Integer.parseInt(tam);
        while(tamanho < 15 || tamanho > 50){
            System.out.println("Tamanho Inválido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o tamanho (15...50):");
            nd = scin.nextLine();
            tamanho=Integer.parseInt(nd);
        }
        if(cancel.contains("y")) return;

        sapatilha.setTamanho(tamanho);

        System.out.println("Atacador ou fio?");
        String ata=scin.nextLine();
        while(!(ata.equals("Atacador") || ata.equals("fio") || ata.equals("atacador") || ata.equals("Fio"))){
            System.out.println("Isso não é fio ou atacador");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Atacador ou fio?");
            ata = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        sapatilha.setAtacadores(ata.equals("Atacador"));

        System.out.println("Introduza a cor:");
        String cor=scin.nextLine();
        sapatilha.setCor(cor);

        Artigo artigo = common_artigo();

        String bool;
        System.out.println("Deseja ativar premium [y/n]:");
        bool=scin.nextLine();
        if(bool.contains("y")){
            sapatilha.ativaPremium();
            sapatilha.setDataPremium(this.getModel().now());
        }

        sapatilha.setNumeroDonos(artigo.getNumeroDonos());
        sapatilha.setEstado(artigo.getEstado());
        sapatilha.setBrand(artigo.getBrand());
        sapatilha.setPrecobase(artigo.getPrecobase());
        sapatilha.setDescricao(artigo.getDescricao());

        System.out.println("Cor da sapatilha: " + sapatilha.getCor());
        System.out.println("Tamanho da sapatilha: " + sapatilha.getTamanho());
        System.out.println("Atacadores da sapatilha: " + sapatilha.getAtacadores());

        System.out.println("Numero de donos: " + sapatilha.getNumeroDonos());
        System.out.println("Estado: " + sapatilha.getEstado());
        System.out.println("Marca: " + sapatilha.getBrand());
        System.out.println("Precobase: " + sapatilha.getPrecobase());
        System.out.println("Descricao do artigo: "+ sapatilha.getDescricao());

        logged.addArtigo(sapatilha);
    }

    public Artigo common_artigo(){
        Artigo artigo=new Artigo();
        String cancel="";
        int numerodonos=0;
        System.out.println("Introduza o numero de donos: ");
        String nd = scin.nextLine();
        numerodonos=Integer.parseInt(nd);
        while(numerodonos < 0){
            System.out.println("Numero de donos invalidos");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o numero de donos:");
            nd = scin.nextLine();
            numerodonos=Integer.parseInt(nd);
        }
        if(cancel.contains("y")) return new Artigo();
        artigo.setNumeroDonos(numerodonos);

        String estado="";
        System.out.println("Introduza o estado (Pouco usado, Usado, Muito usado):");
        estado = scin.nextLine();
        while(!( estado.equals("Pouco usado") ||  estado.equals("Usado") ||  estado.equals("Muito usado"))){
            System.out.println("Tipo de estado invalido!");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o estado (Pouco usado, Usado, Muito usado):");
            estado = scin.nextLine();
        }
        if(cancel.contains("y")) return new Artigo();
        artigo.setEstado(estado);

        System.out.println("Introduza a marca do artigo: ");
        String brand=scin.nextLine();
        artigo.setBrand(brand);

        double precobase=0,desconto=0;
        System.out.println("Introduza o preco base (sem calculos): ");
        String price = scin.nextLine();
        precobase=Integer.parseInt(price);

        while(precobase < 0){
            System.out.println("Precobase: ");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza um novo precobase: ");
            price = scin.nextLine();
            precobase=Integer.parseInt(price);
        }
        if(cancel.contains("y")) return new Artigo();
        artigo.setPrecobase(precobase);

        System.out.println("Descricao do artigo: ");
        String descricao = scin.nextLine();
        artigo.setDescricao(descricao);


        System.out.println("Id/nome da colecao do artigo:");
        String id = scin.nextLine();
        while(!(this.getModel().getColecao().containsKey(id))){
            System.out.println("Não existe coleção com id/nome: " + id);
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza um Id/nome da colecao: ");
            id = scin.nextLine();
        }

        artigo.setColecao(this.getModel().getColecao().get(id));

        System.out.println("Id da transportadora:");
        String idt = scin.nextLine();
        while(!(this.getModel().getColecao().containsKey(idt))){
            System.out.println("Não existe transportadora com id: " + idt);
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o id da transportadora: ");
            idt = scin.nextLine();
        }

        artigo.setTransportadoras(this.getModel().getTransportadora().get(idt));

        return artigo;
    }

    private void user_publish_artigo(Utilizador logged){
        System.out.println("Insira o ID do artigo a publicar: ");
        String id = scin.nextLine();
        if(logged.getArtigos().containsKey(id)){
            if(logged.getArtigos().get(id).isPublicado()){
                logged.getArtigos().get(id).privar();
                System.out.println("Privado com sucesso!");
            } else {
                logged.getArtigos().get(id).publicar();
                System.out.println("Publicado com sucesso!");
            }
        } else{
            System.out.println("Artigo" + id + "não existe");
        }
    }

    private void user_rm_artigo(Utilizador logged){

        System.out.println("Insira o ID do artigo a remover: ");
        String id = scin.nextLine();
        if(logged.getArtigos().containsKey(id)){
            System.out.println("Tem a certeza que quer remover o artigo [y/n]?");
            String clean = scin.nextLine();
            if(clean.contains("y")){
                logged.getArtigos().remove(id);
                System.out.println("Artigo removido com sucesso!");
            }else{
                System.out.println("Cancelado com successo!");
            }
        } else{
            System.out.println("Artigo" + id + "não existe");
        }
    }

    private void user_encomendar(Utilizador logged){
        Encomendas current = new Encomendas();
        for(Map.Entry<String, Encomendas> entry : logged.getEncomendas().entrySet()){
            if(entry.getValue().getEstado() == 0){
                current = entry.getValue();
            }
        }

        NewMenu user_encomendar = new NewMenu(new String[]{
                "Comprar", "Adicionar artigo" , "Remover artigo", "Ver carrinho", "Limpar Carrinho",
                "Ver cardapio"
        });

        Encomendas finalCurrent = current;
        user_encomendar.setHandler(1, ()-> this.encomenda_comprar(finalCurrent, logged));
        user_encomendar.setHandler(2, ()-> this.encomenda_add(finalCurrent));
        user_encomendar.setHandler(3, ()-> this.encomenda_rm(finalCurrent));
        user_encomendar.setHandler(4, ()-> this.encomenda_see(finalCurrent));
        user_encomendar.setHandler(5, ()-> this.encomenda_clean(finalCurrent));
        user_encomendar.setHandler(6, this::encomenda_cardapio);

        user_encomendar.setPreCondition(1, ()-> finalCurrent.getArtigos().size() > 0);
        user_encomendar.setPreCondition(3, ()-> finalCurrent.getArtigos().size() > 0);
        user_encomendar.setPreCondition(5, ()-> finalCurrent.getArtigos().size() > 0);
        user_encomendar.setPreCondition(6, ()-> this.getModel().getArtigos().size() > 0);

        user_encomendar.run();
    }

    public void encomenda_cardapio(){
        System.out.println(this.getModel().getCardapio());
    }

    private void encomenda_comprar(Encomendas current, Utilizador logged){
        boolean send = true;
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            Artigo art = this.getModel().getArtigos().get(entry.getKey());
            if(!(art.isPremium() && art.getTransportadoras().getPremium())){
                send = false;
            }
        }
        if(send){
            current.enviar();
            for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
                Artigo art = this.getModel().getArtigos().get(entry.getKey());
                art.setSold(art.getSold() + 1);
            }
            logged.adicionarEncomenda(current);
        } else{
            System.out.println("Exista pelo menos uma transportadora sem premium ativado para um artigo premium!");
        }

    }

    private void encomenda_add(Encomendas current){
        System.out.println("Id do artigo a adicionar: ");
        String id = scin.nextLine();

        if(current.getArtigos().containsKey(id)){
            current.addArtigo(this.getModel().getArtigos().get(id));
        } else{
            System.out.println("Artigo com id: " + id + " não existe");
        }
    }

    private void encomenda_rm(Encomendas current){
        System.out.println("Id do artigo a remover: ");
        String id = scin.nextLine();

        if(current.getArtigos().containsKey(id)){
            System.out.println("Tem a certeza que quer remover o artigo do carrinho [y/n]?");
            String clean = scin.nextLine();

            if(clean.contains("y")){
                current.getArtigos().remove(id);
                System.out.println("Limpado com successo!");
            }else{
                System.out.println("Cancelado com successo!");
            }
        } else{
            System.out.println("Artigo com id: " + id + " não existe");
        }
    }

    private void encomenda_see(Encomendas current){
        double preco = 0.0;
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            if(entry.getValue().getPreco() != -1.0){
                preco += entry.getValue().getPreco();
            }
        }
        System.out.println("Artigos:" + current.getArtigos() + "\n"
                + "Preço total:" + preco + "\n"
        );
    }

    private void encomenda_clean(Encomendas current){
        System.out.println("Tem a certeza que quer limpar o carrinho [y/n]?");
        String clean = scin.nextLine();

        if(clean.contains("y")){
            current.clean();
            System.out.println("Limpado com successo!");
        }else{
            System.out.println("Cancelado com successo!");
        }
    }

    private void user_receita(Utilizador logged){
        System.out.println("Receita até ao momento: " + logged.getRevenue());
    }

    private void user_bought(Utilizador logged){
        boolean s = true;
        for (Map.Entry<String, Encomendas> entry : logged.getEncomendas().entrySet()) {
            if(!(entry.getValue().getEstado() == 0)){
                s = false;
                System.out.println(entry.getValue().getArtigos());
            }
        }
        if(s){
            System.out.println("Ainda não comprou nenhum artigo!");
        }
    }

    private void user_sold(Utilizador logged){
        for (Map.Entry<String, Artigo> entry : logged.getVendasEfetuadas().entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void user_selling(Utilizador logged){
        boolean s = true;
        for (Map.Entry<String, Artigo> entry : logged.getProdutosAVenda().entrySet()) {
            s = false;
            System.out.println(entry.getValue());
        }
    }

    private void registar(){
        NewMenu registarMenu = new NewMenu(new String[]{
                "User", "Transportadora", "Coleção"
        });

        registarMenu.setHandler(1, this::registar_user);
        registarMenu.setHandler(2, this::registar_transportadora);
        registarMenu.setHandler(3, this::registar_colecao);

        registarMenu.run();

    }

    private void registar_colecao(){
        String col, cancel = "";
        System.out.println("Nome da coleção:");
        col = scin.nextLine();

        while(this.getModel().getColecao().containsKey(col)){
            System.out.println("Já existe coleção com esse nome!");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Nome da coleção:");
            col = scin.nextLine();
        }
        if(cancel.contains("y")) return;

        Colecao cole = new Colecao(col);
        cole.setData(this.getModel().now());

        this.getModel().addColecao(cole);
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
