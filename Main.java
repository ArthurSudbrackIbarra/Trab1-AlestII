import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		//Objeto Rede, o qual tera acesso a todos os computadores.
		Rede rede = new Rede();
		
		//Diretorio do arquivo com as entradas.
		String nomeDiretorio = "entrada.txt";
		Path diretorio = Paths.get(nomeDiretorio);

		//Ler e interpretar o arquivo com as entradas.
		try (BufferedReader reader = Files.newBufferedReader(diretorio, Charset.defaultCharset())) {
					
			String linha = null;
		
			//Continuar lendo o arquivo enquanto ainda houver linhas.
			while ((linha = reader.readLine()) != null) {

				//Dividindo a linha lida pelos espacos em branco. Dessa forma, cada posicao de "partes" (0, 1 e 2) ira
				//conter o nome de um computador ou o valor de uma tarefa realizada.

				String[] partes = linha.split(" ");
				
				//Pegamos o nome do primeiro computador lido na linha e tentamos achar sua referencia
				//em nosso HashMap dentro da classe Rede.
				Computador pai = rede.obterComputador(partes[0]);
				
				//Caso o computador nao seja encontrado, sabemos que estamos lendo a primeira linha do arquivo, e, portanto,
				//precisamos criar nosso primeiro objeto computador, o qual sera pai de todos os demais computadores da rede.
				//O computador criado recebe como parametro de nome partes[0], pois o seu nome sera aquele definido no arquivo 
				//que estamos lendo. Como parametro de valor, este computador recebera null, pois o primeiro computador lido jamais
				//comecara com um valor numerico.

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
				if(isNumeric(partes[1])) {					
					
					//Estamos definindo o nome destes computadores que realizaram a atividade
					//como "Folha X de Y", sendo que X representa a direcao (esquerda [E] ou direita [D])
					//e Y representa o nome do pai desta folha (X0, por exemplo).

					String nome1 = "Folha E de " + partes[0];
					String nome2 = "Folha D de " + partes[0];
					
					//O valor que estes computadores que realizaram a tarefa receberao sera o valor
					//informado nas linhas do arquivo, apos essa informacao ser convertida para Double.

					Double valor1 = Double.parseDouble(partes[1]);
					Double valor2 = Double.parseDouble(partes[2]);
					
					filho1 = new Computador(nome1, valor1);
					filho2 = new Computador(nome2, valor2);
					
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

				if(!filho1.getNome().startsWith("Folha")) {

					rede.adicionarComputador(partes[1], filho1);
					rede.adicionarComputador(partes[2], filho2);

				}
				
			}
			
			//Apois sairmos do laco de repeticao que le o arquivo, ja podemos resolver o problema proposto.
			//O metodo "resolverProblema" da classe Rede nos informara quais e quantos computadores estao equilibrados.
			String[] informacoes = rede.resolverProblema();

			//Agora podemos printar na tela as informacoes que recebemos do metodo.
			System.out.print("Computadores equilibrados: \n\n" + informacoes[0] + "\n");
			System.out.print("Quantidade de computadores equilibrados: " + informacoes[1]);
			
		} catch (IOException e) {
			System.out.println("Houve algum erro em relacao a leitura do arquivo! Talvez o arquivo nao tenha sido encontrado.");		
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static boolean isNumeric(String str) {
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
