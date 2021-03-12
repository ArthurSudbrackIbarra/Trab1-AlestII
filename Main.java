import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Arthur Sudbrack Ibarra, Willian Albeche
 */
public class Main {

	public static void main(String[] args) {
		
		//Objeto Rede, o qual tera acesso a todos os computadores.
		Rede rede = new Rede();
		
		//Diretorio do arquivo com as entradas.
		String nomeDiretorio = "teste5.txt";
		Path diretorio = Paths.get(nomeDiretorio);

		//Ler e interpretar o arquivo com as entradas.
		try (BufferedReader reader = Files.newBufferedReader(diretorio, Charset.defaultCharset())) {
					
			String linha = null;
		
			//Continuar lendo o arquivo enquanto ainda houver linhas.
			while ((linha = reader.readLine()) != null) {

				//Dividindo a linha lida pelos espacos em branco. Dessa forma, cada posicao de "partes" (0, 1 e 2) ira
				//conter o nome de um computador ou o valor de uma tarefa realizada.

				String[] partes = linha.split(" ");

				//Nao fazer nada se a linha nao possuir pelo menos 3 componentes.			
				if(partes.length < 3){
					continue;
				}
				
				//Pegamos o nome do primeiro computador lido na linha e tentamos achar sua referencia
				//em nosso HashMap dentro da classe Rede.

				Computador pai = rede.obterComputador(partes[0]);
				
				//Caso o computador nao seja encontrado, sabemos que estamos lendo a primeira linha do arquivo, e, portanto,
				//precisamos criar nosso primeiro objeto computador, o qual sera pai de todos os demais computadores da rede.
				//O computador criado recebe como parametro de nome partes[0], pois o seu nome sera aquele definido no arquivo 
				//que estamos lendo. Como parametro de valor, este computador recebera null, pois o primeiro computador lido
				//jamais comecara com um valor numerico.

				//Caso o computador seja encontrado, ja teremos acesso a sua referencia, a qual sera devolvida pelo metodo
				//da classe Rede "obterComputador".

				if(pai == null){
					pai = new Computador(partes[0], null);
					rede.adicionarComputador(partes[0], pai);
				}
				
				//Os demais componentes na linha serao filhos do pai acima.

				Computador filho1 = null;
				Computador filho2 = null;
				
				//Caso o segundo componente da linha represente um numero,
				//entao sabemos que a tarefa foi realizada.
				if(numerico(partes[1])) {					
					
					//Note que estes computadores serao os unicos que nao terao um nome unico, por isto
					//estamos passando null como primeiro parametro no momento em que instanciamos estes computadores.

					//O valor que estes computadores que realizaram a tarefa receberao sera o valor
					//informado nas linhas do arquivo, apos essa informacao ser convertida para Double.

					Double valor1 = Double.parseDouble(partes[1]);
					Double valor2 = Double.parseDouble(partes[2]);
					
					filho1 = new Computador(null, valor1);
					filho2 = new Computador(null, valor2);
					
				} else {
					
					//Caso o segundo componente da linha nao represente um numero,
					//entao sabemos que a tarefa ainda nao foi realizada e se dividiu em mais computadores.
					
					//Sendo assim, os nomes dos novos computadores podem ser os proprios nomes definidos
					//no arquivo (X1, por exemplo), e estes computadores nao terao nenhum valor, assim como o computador inicial.
				
					filho1 = new Computador(partes[1], null);
					filho2 = new Computador(partes[2], null);
				}
				
				//Aqui definimos as relacoes que cada computador possui um com o outro,
				//como se eles fossem nodos de uma arvore.

				pai.setFilhoE(filho1);
				pai.setFilhoD(filho2);
				
				filho1.setPai(pai);
				filho2.setPai(pai);
				
				//Iremos adicionar os novos computadores filhos em nossa rede de computadores (HashMap)
				//somente se eles nao forem computadores folhas, ou seja, somente se eles nao possuirem
				//filhos e tiverem um valor (realizaram a atividade). Isto porque nao e de nosso interesse
				//posteriormente calcular se estes computadores folhas estao equilibrados, dado que nao possuem filhos.

				if(filho1.getNome() != null) {

					rede.adicionarComputador(partes[1], filho1);
					rede.adicionarComputador(partes[2], filho2);

				}
				
			}
			
			//Apois sairmos do laco de repeticao que le o arquivo, ja podemos resolver o problema proposto.
			//O metodo "resolverProblema" da classe Rede nos informara quais e quantos computadores estao equilibrados.
			String resultado = rede.resolverProblema();

			//Agora podemos printar na tela as informacoes que recebemos do metodo.
			System.out.print(resultado);
			
		} catch (IOException erro){

			//Caso o arquivo nao seja encontrado, esta mensagem sera exibida.
			System.out.print("Houve algum erro durante a leitura do arquivo.\n" +
			"Certifique-se de que este exista e que se localiza no mesmo diretorio do executavel.");

		} catch (Exception erro){

			//Caso ocorra algum erro de execucao nao relacionada a leitura do arquivo em si,
			//sabemos que ha algum erro de formatacao no arquivo que nao esta de acordo com o padrao
			//estabelecido para as entradas de teste.		
			System.out.print("Houve algum de formatacao do arquivo. Certifique-se de que o conteudo do arquivo\n" + 
			"esta de acordo com os padroes estabelecidos de entrada.");

		}
		
	}
	
	//Este metodo estatico e responsavel por nos informar se uma String e somente numerica ou nao.
	public static boolean numerico(String str) {
		if (str == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(str);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}
