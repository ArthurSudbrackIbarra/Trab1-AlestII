import java.util.HashMap;

/**
 * @author Arthur Sudbrack Ibarra, Luiza Lencina, Willian Magnum Albeche
 */
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
	
	//Construtor da classe Rede.
	public Rede() {
		this.computadores = new HashMap<>();
	}
	
	//Este metodo adiciona um novo computador ao nosso HashMap, o qual representa nossa rede de computadores.
	//Para a chave "nome", sera guardado um objeto Computador em nosso HashMap.
	public void adicionarComputador(String nome, Computador computador) {
		this.computadores.put(nome, computador);
	}
	
	//Metodo para obter a referencia de algum objeto Computador presente na nossa rede de computadores.
	//Este metodo possui notacao O(1) em situacoes adequadas e O(n) como pior caso.
	public Computador obterComputador(String valor) {
		return this.computadores.get(valor);
	}

	//Variavel estatica para guardar a soma total das folhas de uma arvore ou de uma
	//subarvore. Note que e interessante que este variavel seja estatica, uma vez
	//que ela e incrementada dentro de metodos recursivos, e, desta forma, sempre temos
	//como modifica-la independentemente do escopo em que nos localizamos.
	private static double somaFolhas;

	//Metodo responsavel por indicar a soma dos valores de todas as folhas de uma
	//arvore ou subarvore. Este metodo nao retorna valor algum, no entanto, modifica diretamente
	//o valor da variavel "somaFolhas" acima, a qual sera usada posteriormente.
	private void somaDasFolhas(Computador computador){

		//Se o computador for uma folha, ou seja, possuir um valor, entao iremos somar
		//o seu valor a nossa variavel estatica "somaFolhas" e acabar a chamada do metodo.
		if(computador.getValor() != null){
			somaFolhas += computador.getValor();
			return;
		}

		//Enquanto o computador que estamos analisando nao for uma folha, chamaremos este metodo
		//recursivamente mais duas vezes, passando como parametro os filhos esquerdo e direito do computador pai.
		this.somaDasFolhas(computador.getFilhoE());
		this.somaDasFolhas(computador.getFilhoD());

	}

	//Metodo responsavel por nos dizer com precisao quais e quantos computadores estao equilibrados.
	public String resolverProblema(){

		//String a qual guardara o nome dos computadores que estao equilibrados.
		String equilibrados = "";

		//Contador para marcarmos quantos computadores estao equilibrados.
		int quantosEquilibrados = 0;

		//Aqui estamos percorrendo linearmente todos os computadores nao-folhas,
		//os quais estao guardados em nosso HashMap de computadores.
		for(Computador computador : this.computadores.values()){

			//Chamamos o metodo "somaDasFolhas" para a a subarvore da esquerda
			//do computador que estamos retirando de nosso HashMap.	
			this.somaDasFolhas(computador.getFilhoE());
			double somaFolhasE = somaFolhas;
			somaFolhas = 0;

			//Chamamos o metodo "somaDasFolhas" para a a subarvore da direita
			//do computador que estamos retirando de nosso HashMap.	
			this.somaDasFolhas(computador.getFilhoD());
			double somaFolhasD = somaFolhas;
			somaFolhas = 0;

			//Se a soma dos valores das folhas das subarvores esquerdas e direitas do
			//computador forem iguais, entao o computador em questao esta equilibrado.
			if(somaFolhasE == somaFolhasD){
				equilibrados += computador.getNome() + "\n";
				quantosEquilibrados++;
			}

		}

		//Montando o resultado com as informacoes obtidas (nomes e quantidades dos computadores equilibrados).
		String resultado = "[Computadores Equilibrados]\n\n" + equilibrados + "\n\n[Quantidade de Computadores Equilibrados]\n\n" + quantosEquilibrados;

		return resultado;

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
