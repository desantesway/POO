import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {
    public static void main(String[] args) throws ScriptException {
        System.out.printf("Hello world!\n");

        Transportadoras t1 = new Transportadoras(3,4,10,2);
        t1.preco("3 * valorBase - imposto");
        System.out.printf("Preço = "+ t1.getPrecoExp().getGrande());
    }
}