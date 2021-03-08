import java.util.HashMap;

public class Rede {
	
	/* EXPLICANDO A CLASSE
	============================================================================================================
	A classe Rede pode ser comparada a uma estrutura de dados de arvore. No entanto,
	o que diferencia a classe Rede de uma arvore comum e o fato de que estamos
	armazenando os nossos nodos (Computador) em um HashMap com uma chave do tipo String.
	
	Dessa forma, nao precisamos percorrer a arvore de forma recursiva para obter a referencia
	de algum Node, (ou Computador, neste caso), visto que temos acesso a eles atraves de uma chave String unica. 
	============================================================================================================
	*/

	//Aqui neste HashMap ficam guardados todos os computadores da rede.
	//Conseguimos ter acesso a cada um deles atraves de uma chave String, sendo esta seus nomes unicos.
	private HashMap<String, Computador> computadores;
	
	//Aqui neste atributo guardamos o Computador central (este seria analago as "roots" nas arvores).
	//Sera usado para comecar a percorrer todos os computadores da rede de forma otimizada.
	private Computador computadorCentral;
	
	//Construtor da classe Rede.
	public Rede() {
		this.computadores = new HashMap<>();
		this.computadorCentral = null;
	}
	
	//Este metodo define qual sera o computador central, ou a "root".
	public void definirComputadorCentral(Computador computador) {
		this.computadorCentral = computador;
	}
	
	//Este metodo adiciona um novo computador ao nosso HashMap, o qual representa nossa rede de computadores.
	//Para a chave "valor", sera guardado um objeto Computador em nosso HashMap.
	public void adicionarComputador(String valor, Computador computador) {
		this.computadores.put(valor, computador);
	}
	
	//Metodo para obter a referencia de algum objeto Computador presente na nossa rede de computadores.
	//Este metodo possui notacao O(1) em situacoes adequadas e O(n) como pior caso.
	public Computador obterComputador(String valor) {
		return this.computadores.get(valor);
	}
	
	//Metodo toString da classe Rede.
	public String toString() {
		
		String conteudo = "";
		
		for (Computador computador : this.computadores.values()) {
		    
		    conteudo = conteudo + "[Computador]\n" + computador + "\n\n";
		    
		}
		
		return conteudo;
	}
}
