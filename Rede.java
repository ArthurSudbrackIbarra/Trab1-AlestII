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
	private static double somaFolhas = 0;

	//Metodo responsavel por indicar a soma dos valores de todas as folhas de uma
	//arvore ou subarvore. Este metodo nao retorna valor algum, no entanto, modifica diretamente
	//o valor da variavel "somaFolhas" acima, a qual pode ser usada posteriormente.
	private void somaDasFolhas(Computador computador){

		//A recursao e finalizada quando um computador nao possuir mais filhos (divisoes).
		if(computador.getFilhoE() == null){
			return;
		}

		//Neste problema, para que um computador seja uma folha, ele deve possuir algum valor diferente de nulo.
		//Se o computador atual possuir filhos folhas, entao incrementamos a variavel "somaFolhas" com os valores
		//armazenados em seus filhos esquerdo e direito.
		if(computador.getFilhoE().getValor() != null) {
			somaFolhas += computador.getFilhoE().getValor() + computador.getFilhoD().getValor();
		}

		//Aqui chamamos a recursao novamente passando os filhos esquerdo e direito do computador pai.
		this.somaDasFolhas(computador.getFilhoE());
		this.somaDasFolhas(computador.getFilhoD());

	}

	public String[] resolverProblema(){

		String nomesEquilibrados = "";
		int quantosEquilibrados = 0;

		for(Computador computador : this.computadores.values()){

			this.somaDasFolhas(computador);

			if(somaFolhas % 2 != 1){
				nomesEquilibrados += computador.getNome() + "\n";
				quantosEquilibrados++;
			}

			somaFolhas = 0;

		}

		String[] informacoes = new String[2];
		
		informacoes[0] = nomesEquilibrados;
		informacoes[1] = Integer.toString(quantosEquilibrados);

		return informacoes;

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
