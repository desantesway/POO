import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserApp {
    private sys model;
    private Scanner scin;

    public static void main(String[] args) {
        UserApp app = new UserApp();
        app.run();
    }

    private void update_artigo(Artigo entry2, Utilizador entry){
        if(entry2.getClass().equals(Malas.class)){
            Malas mala = getMalaFromArtigo(entry2);
            mala.calcPreco(this.getModel().now());

            this.getModel().getUser().remove(entry2.getID());
            entry.getArtigos().put(mala.getID(), mala);
        } else if(entry2.getClass().equals(Sapatilhas.class)){
            Sapatilhas shoe = getShoeFromArtigo(entry2);
            shoe.calcPreco(this.getModel().now());

            this.getModel().getUser().remove(entry2.getID());
            entry.getArtigos().put(shoe.getID(), shoe);
        } else if(entry2.getClass().equals(TShirt.class)){
            TShirt tshirt = getTshirtFromArtigo(entry2);
            tshirt.calcPreco();

            this.getModel().getUser().remove(entry2.getID());
            entry.getArtigos().put(tshirt.getID(), tshirt);
        }
    }

    private void updates(){
        //update nos produtos vendidos
        for (Map.Entry<String, Utilizador> entry1 : this.getModel().getUser().entrySet()) {
            Utilizador logged = entry1.getValue();
            for (Map.Entry<String, Artigo> entry : logged.getProdutosAVenda().entrySet()) {
                if(entry.getValue().getSold() > 0){
                    logged.adicionarVendaEfetuada(entry.getValue());
                }
            }
        }
        //update dos calculos de cada artigo
        for (Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()) {
            for (Map.Entry<String, Artigo> entry2 : entry.getValue().getArtigos().entrySet()) {
                if(entry2.getValue().getClass().equals(Malas.class)){
                    Malas mala = getMalaFromArtigo(entry2.getValue());
                    mala.calcPreco(this.getModel().now());

                    this.getModel().getUser().remove(entry2.getKey());
                    entry.getValue().getArtigos().put(mala.getID(), mala);
                } else if(entry2.getValue().getClass().equals(Sapatilhas.class)){
                    Sapatilhas shoe = getShoeFromArtigo(entry2.getValue());
                    shoe.calcPreco(this.getModel().now());

                    this.getModel().getUser().remove(entry2.getKey());
                    entry.getValue().getArtigos().put(shoe.getID(), shoe);
                } else if(entry2.getValue().getClass().equals(TShirt.class)){
                    TShirt tshirt = getTshirtFromArtigo(entry2.getValue());
                    tshirt.calcPreco();

                    this.getModel().getUser().remove(entry2.getKey());
                    entry.getValue().getArtigos().put(tshirt.getID(), tshirt);
                }
            }
        }

        //update das encomendas
        for (Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()) {
            for (Map.Entry<String, Encomendas> entry2 : entry.getValue().getEncomendas().entrySet()) {
                entry2.getValue().setEstado(this.getModel().now());
            }
        }
    }

    private void viagem_tempo(){
        NewMenu viagem = new NewMenu(new String[]{
                "Futuro", "Presente", "Passado", "Ver dia"
        });

        viagem.setHandler(1, this::futuro);
        viagem.setHandler(2, this::presente);
        viagem.setHandler(3, this::passado);
        viagem.setHandler(4, this::day);

        viagem.setPreCondition(2, ()-> !(LocalDate.now().isEqual(this.getModel().now())));

        viagem.run();
    }

    private Malas getMalaFromArtigo(Artigo entry2){
        Malas mala = new Malas(entry2);
        Malas mala2 = mala.fromString(entry2.toString());
        mala.setDataPremium(mala2.getDataPremium());
        mala.setMaterial(mala2.getMaterial());
        mala.setValorizacao(mala2.getValorizacao());
        mala.setTamanho(mala2.getTamanho());

        return mala;
    }

    private Sapatilhas getShoeFromArtigo(Artigo entry2){
        Sapatilhas shoe = new Sapatilhas(entry2);
        Sapatilhas shoe2 = shoe.fromString(entry2.toString());
        shoe.setDataPremium(shoe2.getDataPremium());
        shoe.setAtacadores(shoe2.getAtacadores());
        shoe.setCor(shoe2.getCor());
        shoe.setTamanho(shoe2.getTamanho());

        return shoe;
    }

    private TShirt getTshirtFromArtigo(Artigo entry2){
        TShirt tshirt = new TShirt(entry2);
        TShirt tshirt2 = tshirt.fromString(entry2.toString());
        tshirt.setPadrao(tshirt2.getPadrao());
        tshirt.setTamanho(tshirt2.getTamanho());

        return tshirt;
    }

    private void day(){
        System.out.println("Estamos no dia: " + this.getModel().now());
    }

    private void passado(){
        System.out.println("Dias para o passado: ");
        String days = scin.nextLine(), cancel ="";
        while(Integer.parseInt(days) < 1){
            System.out.println("Dias de atraso inválido!");
            System.out.println("Cancelar a viagem no tempo [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) return;
            System.out.println("Dias para o passado: ");
            days = scin.nextLine();
        }
        this.getModel().past(Integer.parseInt(days));
        updates();
    }

    private void futuro(){
        System.out.println("Dias para o futuro: ");
        String days = scin.nextLine(), cancel ="";
        while(Integer.parseInt(days) < 1){
            System.out.println("Dias de avanço inválido!");
            System.out.println("Cancelar a viagem no tempo [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) return;
            System.out.println("Dias para o futuro: ");
            days = scin.nextLine();
        }
        this.getModel().future(Integer.parseInt(days));
        updates();
    }

    private void presente(){
        this.getModel().setNow(0);
        updates();
        System.out.println("Viagem para o presente concluída!");
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
        updates();
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

    private void save(){
        try{
            this.getModel().save("sys.obj");
            System.out.println("Salvo!");
        } catch (IOException e){
            System.out.println("Erro a registar ficheiro: " + e);
        }
    }

    private void closeApp(){
        String y;

        System.out.println("Queres guardar as mudanças [y/n]?");
        y = scin.nextLine();

        if(y.equals("y")){
            save();
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
                    "Ver cardapio", "Ver receita da vintage", "Vendedor que vendeu mais",
                    "Transportadora que faturou mais", "Guardar o sistema"
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
            adminMenu.setHandler(10, this::admin_receita);
            adminMenu.setHandler(11, this::admin_seller_receita);
            adminMenu.setHandler(12, this::admin_transportadora_receita);
            adminMenu.setHandler(13, this::save);

            adminMenu.setPreCondition(3, () -> this.getModel().getUser().size() > 0);
            adminMenu.setPreCondition(4, () -> this.getModel().getUser().size() > 0);
            adminMenu.setPreCondition(5, () -> this.getModel().getTransportadora().size() > 0);
            adminMenu.setPreCondition(6, () -> this.getModel().getTransportadora().size() > 0);
            adminMenu.setPreCondition(7, () -> this.getModel().getUser().size() > 0);
            adminMenu.setPreCondition(12, () -> this.getModel().getTransportadora().size() > 0);
            adminMenu.setPreCondition(11, () -> this.getModel().getUser().size() > 0);

            adminMenu.run();
        } else{
            System.out.println("Login Inválido!");
        }
    }

    private void admin_transportadora_receita(){
        double maior = -1.0;
        Transportadoras t = new Transportadoras(1,1,1,1);
        for(Map.Entry<String, Transportadoras> entry : this.getModel().getTransportadora().entrySet()){
            if(maior < entry.getValue().getRev()){
                t = entry.getValue();
                maior = t.getRev();
            }
        }
        System.out.println("Transportadora que faturou mais: " + t
                            + " Faturou: " + maior
        );
    }

    private void admin_seller_receita(){
        double maior = -1.0, temp = 0.0;
        Utilizador t = new Utilizador();
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            if(maior < entry.getValue().getRevenue()){
                t = entry.getValue();
                maior = t.getRevenue();
            }
        }
        System.out.println("Vendedor que faturou mais: " + t
                + " Faturou: " + maior
        );
    }

    private void admin_receita(){
        System.out.println("Receita total da Vintage: " + this.getModel().getRev());
    }

    private void see_artigos(){
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            System.out.println(entry.getValue().getEmail() + ": ");
            for(Map.Entry<String, Artigo> entry2 : entry.getValue().getArtigos().entrySet()){
                if(this.getModel().now().isBefore(entry2.getValue().getBorn())) {
                    System.out.println(entry2.getValue());
                }
            }
        }
    }

    public void see_encomendas(){
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            if (entry.getValue().getEncomendas().size() > 0) {
                System.out.println(entry.getValue().getEmail() +": " +  entry.getValue().getEncomendas());
            }
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
                    "Dados", "Alterar Dados", "Ver Receita"
            });

            userMenu.setHandler(1, ()->this.details_transportadora(logged));
            userMenu.setHandler(2, ()->this.change_config_transportadora(logged));
            userMenu.setHandler(3, ()->this.rev_transportadora(logged));

            userMenu.run();
        } else{
            System.out.println("Transportadora ainda não registada.");
        }
    }

    private void rev_transportadora(Transportadoras logged){
        System.out.println("Receita até ao momento: " + (logged.getRev() - (logged.getRev()*this.getModel().getVintagecut())));
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
                "Encomendar", "Ver Artigos comprados"
        });

        userMenu.setHandler(1, () -> this.user_encomendar(logged));
        userMenu.setHandler(2, () -> this.user_bought(logged));

        userMenu.run();
    }

    private void user_central_vendedor(Utilizador logged){

        NewMenu userMenu = new NewMenu(new String[]{
                "Criar novo artigo", "Publicar/Privar artigo",
                "Remover artigo", "Ver receita", "Ver artigos criados",
                "Ver Artigos vendidos", "Ver Artigos á venda",
                "Alterar configurações de um artigo", "Duplicar artigo",
                "Ver encomendas enviadas"
        });

        userMenu.setHandler(1, () -> this.user_new_artigo(logged));
        userMenu.setHandler(2, () -> this.user_publish_artigo(logged));
        userMenu.setHandler(3, () -> this.user_rm_artigo(logged));
        userMenu.setHandler(4, () -> this.user_receita(logged));
        userMenu.setHandler(5, () ->this.user_artigo_created(logged));
        userMenu.setHandler(6, () ->this.user_sold(logged));
        userMenu.setHandler(7, () -> this.user_selling(logged));
        userMenu.setHandler(8, () -> this.user_artigo_config(logged));
        userMenu.setHandler(9, () -> this.user_artigo_clone(logged));
        userMenu.setHandler(10, () -> this.user_sent(logged));

        userMenu.run();
    }

    private void user_sent(Utilizador logged){
        int equals;
        for(Map.Entry<String, Utilizador> entry : this.getModel().getUser().entrySet()){
            for(Map.Entry<String, Encomendas> entry2 : entry.getValue().getEncomendas().entrySet()){
                if(entry2.getValue().getEstado() != 0){
                    equals = 0;
                    for(Map.Entry<String, Artigo> entry3 : entry2.getValue().getArtigos().entrySet()){
                        for(Map.Entry<String, Artigo> entry4 : logged.getVendasEfetuadas().entrySet()){
                            if(entry3.getValue().equals(entry4.getValue())){
                                equals +=1;
                            }
                        }
                    }
                    if(equals == entry2.getValue().getArtigos().size()){
                        System.out.println(entry2.getValue());
                    }
                }
            }
        }
    }

    private void user_artigo_clone(Utilizador logged){
        System.out.println("Introduza o id do artigo a duplicar: ");
        String id = scin.nextLine(), cancel = "";
        while(!(logged.getArtigos().containsKey(id))){
            System.out.println("Não existe artigo com id " + id + " na sua conta.");
            System.out.println("Cancelar a mudança [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("id: ");
            id = scin.nextLine();
        }
        if(cancel.contains("y")) return;

        Artigo nArtigo = logged.getArtigos().get(id).clone();

        while(this.getModel().getArtigos().containsKey(nArtigo.getID())){
            nArtigo.setID(nArtigo.generateID());
        }
        System.out.println("Id do novo artigo: " + nArtigo.getID());

        logged.addArtigo(nArtigo);

    }

    private void user_artigo_created(Utilizador logged){
        for(Map.Entry<String, Artigo> entry : logged.getArtigos().entrySet()) {
            if (!this.getModel().now().isBefore(entry.getValue().getBorn())) {
                System.out.println(entry.getValue());
            }
        }
    }

    private void user_artigo_config(Utilizador logged){
        System.out.println("Introduza o id do artigo a editar: ");
        String id = scin.nextLine();

        if(logged.getArtigos().containsKey(id)){
            NewMenu artigo_Menu = new NewMenu(new String[]{
                    "Mudar estado", "Mudar descricao",
                    "Mudar Marca", "Mudar Numero de Donos",
                    "Mudar preco base","Mudar colecao","Mudar transportadora"

            });
            Artigo artigo = logged.getArtigos().get(id);
            artigo_Menu.setHandler(1, () -> this.artigo_estado(artigo, logged));
            artigo_Menu.setHandler(2, () -> this.artigo_descricao(artigo));
            artigo_Menu.setHandler(3, () -> this.artigo_brand(artigo));
            artigo_Menu.setHandler(4, () -> this.artigo_NDonos(artigo, logged));
            artigo_Menu.setHandler(5, () -> this.artigo_precobase(artigo, logged));
            artigo_Menu.setHandler(6, () -> this.artigo_colecao(artigo, logged));
            artigo_Menu.setHandler(7, () -> this.artigo_transportadora(artigo));

            artigo_Menu.setPreCondition(1, ()-> artigo.getNumeroDonos() > 0);

            artigo_Menu.run();
        } else{
            System.out.println("Artigo com id " + id + " não existe.");
        }
    }

    private void artigo_estado(Artigo artigo, Utilizador logged) {
        String estado="", cancel="";
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
        if(cancel.contains("y")) return;
        artigo.setEstado(estado);
        update_artigo(artigo, logged);
    }

    private void artigo_descricao(Artigo artigo) {
        System.out.println("Nova descricao:");
        String descricao= scin.nextLine();
        artigo.setDescricao(descricao);
    }

    private void artigo_brand(Artigo artigo){
        System.out.println("Nova marca:");
        String brand= scin.nextLine();
        artigo.setBrand(brand);
    }

    private void artigo_NDonos(Artigo artigo, Utilizador logged){
        int numerodonos=0;
        System.out.println("Introduza o numero de donos: ");
        String nd = scin.nextLine(), cancel="";
        numerodonos=Integer.parseInt(nd);
        while(numerodonos < 0){
            System.out.println("Numero de donos invalido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o numero de donos:");
            nd = scin.nextLine();
            numerodonos=Integer.parseInt(nd);
        }
        if(cancel.contains("y")) return;
        artigo.setNumeroDonos(numerodonos);

        if(numerodonos != 0){
            artigo_estado(artigo, logged);
        } else{
            artigo.setEstado("Novo");
        }
    }

    private void artigo_precobase(Artigo artigo, Utilizador logged){
        System.out.println("Novo preco base:");
        double val = Integer.parseInt(scin.nextLine());
        String cancel="";
        while(val < 0){
            System.out.println("Preço invalido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o preço:");
            val = Integer.parseInt(scin.nextLine());
        }
        if(cancel.contains("y")) return;

        artigo.setPrecobase(val);
        update_artigo(artigo, logged);
    }

    private void artigo_colecao(Artigo artigo, Utilizador logged){
        System.out.println("Nome da nova coleção:");
        String id = scin.nextLine(),cancel="";
        while(!(this.getModel().getColecao().containsKey(id))){
            System.out.println("Não existe coleção com id/nome: " + id);
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o nome da colecao: ");
            id = scin.nextLine();
        }

        artigo.setColecao(this.getModel().getColecao().get(id));
        update_artigo(artigo, logged);
    }

    private void artigo_transportadora(Artigo artigo){
        System.out.println("Id da nova transportadora:");
        String idt = scin.nextLine(),cancel="";
        boolean loop;
        loop = !(this.getModel().getTransportadora().containsKey(idt));
        if(!loop) loop = artigo.isPremium() && !this.getModel().getTransportadora().get(idt).getPremium();
        while(loop){
            if(!(this.getModel().getTransportadora().containsKey(idt))) System.out.println(
                    "Não existe transportadora com id: " + idt);
            else if(artigo.isPremium() && !this.getModel().getTransportadora().get(idt).getPremium()) System.out.println(
                    "Essa transportadora não tem expedição premium!");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o id da transportadora: ");
            idt = scin.nextLine();
            loop = !(this.getModel().getTransportadora().containsKey(idt));
            if(!loop) loop = artigo.isPremium() && !this.getModel().getTransportadora().get(idt).getPremium();
        }

        artigo.setTransportadoras(this.getModel().getTransportadora().get(idt));
    }

    private void user_details(Utilizador logged){
        String print="";
        if(!(logged.getRevenue() == 0.0)) print += "Receita: " + logged.getRevenue() + "\n";
        if(logged.getProdutosAVenda().size() > 0) print += "Artigos a venda: " + logged.getProdutosAVenda()+ "\n";
        if(logged.getVendasEfetuadas().size() > 0) print += "Vendas efetuadas: " + logged.getVendasEfetuadas() + "\n";
        if(logged.getArtigos().size() > 0) print += "Artigos á venda: " + logged.getArtigos() + "\n";
        if(logged.getEncomendas().size() > 0) print += "Encomendas Realizadas: " + logged.getEncomendas();
        System.out.println("Email: " + logged.getEmail() + "\n"
                + "Nome: " + logged.getNome() + "\n"
                + "Morada: " + logged.getMorada() + "\n"
                + "Nif: " + logged.getNif() + "\n"
                + "User ID: " + logged.getID() + "\n"
                + print
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

        String bool;
        System.out.println("Deseja ativar premium [y/n]:");
        bool=scin.nextLine();
        int p= 0;
        if(bool.contains("y")){
            p = 1;
            mala.ativaPremium();
            mala.setDataPremium(this.getModel().now());

            System.out.println("Valorização do premium ao ano (0-100): ");
            double val = Integer.parseInt(scin.nextLine());

            while(val < 0 || val > 100){
                System.out.println("A valorização só pode ser entre 0% e 100%");
                System.out.println("Cancelar a adição [y/n]?");
                cancel = scin.nextLine();
                if(cancel.contains("y")) break;
                System.out.println("Valorização: ");
                val = Integer.parseInt(scin.nextLine());
            }
            if(cancel.contains("y")) return;
            mala.setValorizacao(val/100);

            mala.setDataPremium(this.getModel().now());
        }

        Artigo artigo = common_artigo(p);
        if(artigo.getTransportadoras() == null){
            return;
        }

        mala.setNumeroDonos(artigo.getNumeroDonos());
        mala.setEstado(artigo.getEstado());
        mala.setBrand(artigo.getBrand());
        mala.setPrecobase(artigo.getPrecobase());
        mala.setDescricao(artigo.getDescricao());
        mala.setTransportadoras(artigo.getTransportadoras());
        mala.setColecao(artigo.getColecao());
        mala.calcPreco(this.getModel().now());

        System.out.println("Tamanho da mala: " + mala.getTamanho());
        System.out.println("Material da mala: " + mala.getMaterial());
        if(mala.isPremium()) System.out.println("Valorização ao ano: " + mala.getValorizacao()*100 + "%");

        System.out.println("Numero de donos: " + mala.getNumeroDonos());
        System.out.println("Estado: " + mala.getEstado());
        System.out.println("Marca: " + mala.getBrand());
        System.out.println("Descricao do artigo: "+ mala.getDescricao());

        System.out.println("Preco base: " + mala.getPrecobase());
        System.out.println("Preco após cálculos: " + mala.getPreco());

        while(this.getModel().getArtigos().containsKey(mala.getID())){
            mala.setID(mala.generateID());
        }
        System.out.println("Id do artigo: " + mala.getID());


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


        System.out.println("Introduza o padrao da TShirt (liso, riscas, palmeiras): ");
        String padrao=scin.nextLine();
        while(!(padrao.equals("palmeiras") || padrao.equals("riscas") || padrao.equals("liso"))){
            System.out.println("Padrão Inválido");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Padrao: (liso, riscas, palmeiras: ");
            padrao = scin.nextLine();
        }
        if(cancel.contains("y")) return;
        tshirt.setPadrao(padrao);

        Artigo artigo = common_artigo(0);
        if(artigo.getTransportadoras() == null){
            return;
        }

        tshirt.setNumeroDonos(artigo.getNumeroDonos());
        tshirt.setEstado(artigo.getEstado());
        tshirt.setBrand(artigo.getBrand());
        tshirt.setPrecobase(artigo.getPrecobase());
        tshirt.setDescricao(artigo.getDescricao());
        tshirt.setTransportadoras(artigo.getTransportadoras());
        tshirt.setColecao(artigo.getColecao());
        tshirt.calcPreco();

        System.out.println("Tamanho da tshirt: " + tshirt.getTamanho());
        System.out.println("Padrao da tshirt: " + tshirt.getPadrao());

        System.out.println("Numero de donos: " + tshirt.getNumeroDonos());
        System.out.println("Estado: " + tshirt.getEstado());
        System.out.println("Marca: " + tshirt.getBrand());
        System.out.println("Descricao do artigo: "+ tshirt.getDescricao());

        System.out.println("Preco base: " + tshirt.getPrecobase());
        System.out.println("Preco após cálculos: " + tshirt.getPreco());

        while(this.getModel().getArtigos().containsKey(tshirt.getID())){
            tshirt.setID(tshirt.generateID());
        }
        System.out.println("Id do artigo: " + tshirt.getID());

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

        String bool;
        System.out.println("Deseja ativar premium [y/n]:");
        bool=scin.nextLine();
        int p = 0;
        if(bool.contains("y")){
            p=1;
            sapatilha.ativaPremium();
            sapatilha.setDataPremium(this.getModel().now());
        }

        Artigo artigo = common_artigo(p);
        if(artigo.getTransportadoras() == null){
            return;
        }

        sapatilha.setNumeroDonos(artigo.getNumeroDonos());
        sapatilha.setEstado(artigo.getEstado());
        sapatilha.setBrand(artigo.getBrand());
        sapatilha.setPrecobase(artigo.getPrecobase());
        sapatilha.setDescricao(artigo.getDescricao());
        sapatilha.setTransportadoras(artigo.getTransportadoras());
        sapatilha.setColecao(artigo.getColecao());
        sapatilha.calcPreco(this.getModel().now());

        System.out.println("Cor da sapatilha: " + sapatilha.getCor());
        System.out.println("Tamanho da sapatilha: " + sapatilha.getTamanho());
        System.out.println("Atacadores da sapatilha: " + sapatilha.getAtacadores());

        System.out.println("Numero de donos: " + sapatilha.getNumeroDonos());
        System.out.println("Estado: " + sapatilha.getEstado());
        System.out.println("Marca: " + sapatilha.getBrand());

        System.out.println("Descricao do artigo: "+ sapatilha.getDescricao());
        System.out.println("Preco base: " + sapatilha.getPrecobase());
        System.out.println("Preco após cálculos: " + sapatilha.getPreco());

        while(this.getModel().getArtigos().containsKey(sapatilha.getID())){
            sapatilha.setID(sapatilha.generateID());
        }
        System.out.println("Id do artigo: " + sapatilha.getID());

        logged.addArtigo(sapatilha);
    }

    public Artigo common_artigo(int p){
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

        if(numerodonos != 0){
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
        } else{
            artigo.setEstado("Novo");
        }

        System.out.println("Introduza a marca do artigo: ");
        String brand=scin.nextLine();
        artigo.setBrand(brand);

        double precobase=0;
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


        System.out.println("Nome da colecao do artigo:");
        String id = scin.nextLine();
        while(!(this.getModel().getColecao().containsKey(id))){
            System.out.println("Não existe coleção com id/nome: " + id);
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza um Id/nome da colecao: ");
            id = scin.nextLine();
        }
        if(cancel.contains("y")) return new Artigo();

        artigo.setColecao(this.getModel().getColecao().get(id));

        System.out.println("Id da transportadora:");
        String idt = scin.nextLine();
        boolean loop;
        loop = !(this.getModel().getTransportadora().containsKey(idt));
        if(!loop) loop = p == 1 && !this.getModel().getTransportadora().get(idt).getPremium();
        while(loop){
            if(!(this.getModel().getTransportadora().containsKey(idt))) System.out.println("Não existe transportadora com id: " + idt);
            else if(p == 1 && !this.getModel().getTransportadora().get(idt).getPremium()) System.out.println("Essa transportadora não tem expedição premium!");
            System.out.println("Cancelar a adição [y/n]?");
            cancel = scin.nextLine();
            if(cancel.contains("y")) break;
            System.out.println("Introduza o id da transportadora: ");
            idt = scin.nextLine();
            loop = !(this.getModel().getTransportadora().containsKey(idt));
            if(!loop) loop = p == 1 && !this.getModel().getTransportadora().get(idt).getPremium();
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
                logged.getProdutosAVenda().remove(id);
                System.out.println("Privado com sucesso!");
            } else {
                logged.getArtigos().get(id).publicar();
                logged.getProdutosAVenda().put(id, logged.getArtigos().get(id));
                System.out.println("Publicado com sucesso!");
            }
        } else{
            System.out.println("Artigo " + id + " não existe");
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
            System.out.println("Artigo " + id + " não existe");
        }
    }

    private void user_encomendar(Utilizador logged){
        Encomendas current = new Encomendas();
        for(Map.Entry<String, Encomendas> entry : logged.getEncomendas().entrySet()){
            if(entry.getValue().getEstado() == 0){
                current = entry.getValue();
                logged.getEncomendas().remove(entry.getKey());
            }
        }

        NewMenu user_encomendar = new NewMenu(new String[]{
                "Comprar", "Adicionar artigo" , "Remover artigo", "Ver carrinho", "Limpar Carrinho",
                "Ver cardapio"
        });

        Encomendas finalCurrent = current;
        AtomicInteger c = new AtomicInteger();
        user_encomendar.setHandler(1, ()-> c.set(this.encomenda_comprar(finalCurrent, logged)));
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

        if(c.get()!=1){
            logged.adicionarEncomenda(finalCurrent);
        }
    }

    public void encomenda_cardapio(){
        System.out.println(this.getModel().getCardapio(this.getModel().now()));
    }

    private int encomenda_comprar(Encomendas current, Utilizador logged){

        Map<String, Transportadoras> adicionadas = new HashMap<>();
        Map<String, Integer> quantidade = new HashMap<>();
        Map<String, Transportadoras> adicionadasp = new HashMap<>();
        Map<String, Integer> quantidadep = new HashMap<>();

        transportadoras_price(current, adicionadas, quantidade, adicionadasp, quantidadep);
        double transportadoras = 0.0;

        for (Map.Entry<String, Transportadoras> entry : adicionadas.entrySet()){
            if(quantidade.get(entry.getKey()) <= 1){
                transportadoras += entry.getValue().getPrecoExp().getPequeno();
                entry.getValue().setRev(entry.getValue().getPrecoExp().getPequeno()
                        + entry.getValue().getRev());
            }else if(quantidade.get(entry.getKey()) <= 5){
                transportadoras += entry.getValue().getPrecoExp().getMedio();
                entry.getValue().setRev(entry.getValue().getPrecoExp().getMedio()
                        + entry.getValue().getRev());
            }else if(quantidade.get(entry.getKey()) > 5){
                transportadoras += entry.getValue().getPrecoExp().getGrande();
                entry.getValue().setRev(entry.getValue().getPrecoExp().getGrande()
                        + entry.getValue().getRev());
            }
        }

        for (Map.Entry<String, Transportadoras> entry : adicionadasp.entrySet()){
            if(quantidadep.get(entry.getKey()) <= 1){
                transportadoras += entry.getValue().getPrecoExp().getPequeno();
                entry.getValue().setRev(entry.getValue().getPrecoPremium().getPequeno()
                        + entry.getValue().getRev());
            }else if(quantidadep.get(entry.getKey()) <= 5){
                transportadoras += entry.getValue().getPrecoExp().getMedio();
                entry.getValue().setRev(entry.getValue().getPrecoPremium().getMedio()
                        + entry.getValue().getRev());
            }else if(quantidadep.get(entry.getKey()) > 5){
                transportadoras += entry.getValue().getPrecoExp().getGrande();
                entry.getValue().setRev(entry.getValue().getPrecoPremium().getGrande()
                        + entry.getValue().getRev());
            }
        }

        double preco = 0.0;
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            if(entry.getValue().getPreco() != -1.0){
                preco += entry.getValue().getPreco();
            }
        }

        this.getModel().setRev(this.getModel().getRev() + ((preco + transportadoras) * this.getModel().getVintagecut()));

        current.enviar(this.getModel().now());
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            Artigo art = this.getModel().getArtigos().get(entry.getKey());
            art.setSold(art.getSold() + 1);
        }
        logged.adicionarEncomenda(current);
        updates();
        System.out.println("Obrigado e volte sempre!");
        return 1;
    }

    private void encomenda_add(Encomendas current){
        System.out.println("Id do artigo a adicionar: ");
        String id = scin.nextLine();

        if(this.getModel().getArtigos().containsKey(id)){
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

    // analisa o numero de artigos por transportadora, por ex: se ha 2 transportadoras e 4 artigos, sendo 1 associado a uma e 3 a outra:
    // o preço das tranportadoras vai ser o pequeno da 1 + medio do 2.
    // aplicar a lógica de cima com premium também
    private void transportadoras_price(Encomendas current,
                                       Map<String, Transportadoras> adicionadas, Map<String, Integer> quantidade,
                                       Map<String, Transportadoras> adicionadasp, Map<String, Integer> quantidadep){
        boolean equal;
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            if(entry.getValue().isPremium()){
                if(adicionadasp.size() == 0){
                    quantidadep.put("0", 1);
                    adicionadasp.put("0", entry.getValue().getTransportadoras());
                } else{
                    equal = false;
                    for (Map.Entry<String, Transportadoras> entry2 : adicionadasp.entrySet()){
                        if(entry.getValue().getTransportadoras().equals(entry2.getValue())){
                            quantidadep.put(entry2.getKey(), quantidadep.get(entry2.getKey()) + 1);
                            equal = true;
                        }
                    }
                    if(!equal){
                        quantidadep.put(Integer.toString(adicionadasp.size()), 1);
                        adicionadasp.put(Integer.toString(adicionadasp.size()), entry.getValue().getTransportadoras());
                    }
                }
            } else{
                if(adicionadas.size() == 0){
                    quantidade.put("0", 1);
                    adicionadas.put("0", entry.getValue().getTransportadoras());
                } else{
                    equal = false;
                    for (Map.Entry<String, Transportadoras> entry2 : adicionadas.entrySet()){
                        if(entry.getValue().getTransportadoras().equals(entry2.getValue())){
                            quantidade.put(entry2.getKey(), quantidade.get(entry2.getKey()) + 1);
                            equal = true;
                        }
                    }
                    if(!equal){
                        quantidade.put(Integer.toString(adicionadas.size()), 1);
                        adicionadas.put(Integer.toString(adicionadas.size()), entry.getValue().getTransportadoras());
                    }
                }
            }
        }
    }

    private void encomenda_see(Encomendas current){
        Map<String, Transportadoras> adicionadas = new HashMap<>();
        Map<String, Integer> quantidade = new HashMap<>();
        Map<String, Transportadoras> adicionadasp = new HashMap<>();
        Map<String, Integer> quantidadep = new HashMap<>();

        transportadoras_price(current, adicionadas, quantidade, adicionadasp, quantidadep);

        double transportadoras = 0.0;

        for (Map.Entry<String, Transportadoras> entry : adicionadas.entrySet()){
            if(quantidade.get(entry.getKey()) <= 1){
                transportadoras += entry.getValue().getPrecoExp().getPequeno();
            }else if(quantidade.get(entry.getKey()) <= 5){
                transportadoras += entry.getValue().getPrecoExp().getMedio();
            }else if(quantidade.get(entry.getKey()) > 5){
                transportadoras += entry.getValue().getPrecoExp().getGrande();
            }
        }

        for (Map.Entry<String, Transportadoras> entry : adicionadasp.entrySet()){
            if(quantidadep.get(entry.getKey()) <= 1){
                transportadoras += entry.getValue().getPrecoPremium().getPequeno();
            }else if(quantidadep.get(entry.getKey()) <= 5){
                transportadoras += entry.getValue().getPrecoPremium().getMedio();
            }else if(quantidadep.get(entry.getKey()) > 5){
                transportadoras += entry.getValue().getPrecoPremium().getGrande();
            }
        }

        double preco = 0.0;
        for (Map.Entry<String, Artigo> entry : current.getArtigos().entrySet()) {
            if(entry.getValue().getPreco() != -1.0){
                preco += entry.getValue().getPreco();
            }
        }
        System.out.println("Artigos: " + current.getArtigos() + "\n"
                + "Preço dos artigos: " + preco + "\n"
                + "Preço das transportadoras: " + transportadoras  + "\n"
                + "Preço total: " + (preco + transportadoras)
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
        System.out.println("Receita até ao momento: " + (logged.getRevenue() - (logged.getRevenue()*this.getModel().getVintagecut())));
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
            System.out.println(entry.getValue() + " vendeu: " + entry.getValue().getSold());
        }
    }

    private void user_selling(Utilizador logged){
        for (Map.Entry<String, Artigo> entry : logged.getProdutosAVenda().entrySet()) {
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

        Colecao cole = new Colecao(col, this.getModel().now());
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
}
