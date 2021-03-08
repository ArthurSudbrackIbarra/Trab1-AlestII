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
			int numeroLinhas = 0;
		
			//Continuar lendo o arquivo enquanto ainda houver linhas.
			while ((linha = reader.readLine()) != null) {

				//Dividindo a linha pelos espacos em branco. Dessa forma,
				//cada posicao de "partes" ira conter o nome de um computador ou o tamanho/valor de uma tarefa realizada.
				String[] partes = linha.split(" ");
				
				//Se estivermos lendo a primeira linha do arquivo, entao sera feito um setup inical.
				if(numeroLinhas == 0) {
					
					//O primeiro computador lido sera o computador central, analogo ao "root" das arvores.
					//Este computador podera se dividir em mais filhos, os quais podem ou se dividir em mais
					//computadores, ou realizar a tarefa proposta.
					Computador computador1 = new Computador(partes[0], null);
					
					rede.definirComputadorCentral(computador1);
					
					//Os demais computadores da linha serao filhos diretos do computador central.
					Computador computador2 = null;
					Computador computador3 = null;
					
					//Caso o segundo componente da linha representar um numero,
					//entao sabemos que a tarefa foi realizada.
					if(isNumeric(partes[1])) {					
						
						//Os nomes dos computadores precisam ser unicos, entao 
						//estamos definindo o nome destes computadores que realizaram a atividade
						//como "Leaf X", sendo que X representa um numero qualquer que sempre sera incrementado.
						String nome1 = "Leaf " + numeroLinhas;
						String nome2 = "Leaf " + (numeroLinhas + 1);
						
						//O valor que estes computadores que realizaram a tarefa receberao 
						//sera o valor informado nas linhas do arquivo, apos essa informacao ser convertida para Double.
						Double valor1 = Double.parseDouble(partes[1]);
						Double valor2 = Double.parseDouble(partes[2]);
						
						computador2 = new Computador(nome1, valor1);
						computador3 = new Computador(nome2, valor2);
						
					} else {
						
						//Caso o segundo componente da linha nao represente um numero,
						//entao sabemos que a tarefa ainda nao foi realizada e se dividiu em mais computadores.
						
						//Sendo assim, os nomes dos novos computadores podem ser os proprios nomes definidos
						//no arquivo (X1, por exemplo), e estes computadores nao terao nenhum valor.
						
						computador2 = new Computador(partes[1], null);
						computador3 = new Computador(partes[2], null);
					}								
					
					//O primeiro computador terá como filhos os computadores 2 e 3.
					computador1.setFilhoE(computador2);
					computador1.setFilhoD(computador3);
					
					//Os computadores 2 e 3 terao como pai o computador 1.
					computador2.setPai(computador1);
					computador3.setPai(computador1);
					
					//Adicionando os computadores criados em nossa rede de computadores.
					rede.adicionarComputador(computador1.getNome(), computador1);
					rede.adicionarComputador(computador2.getNome(), computador2);
					rede.adicionarComputador(computador3.getNome(), computador3);
					
				} else {
					
					//Se nao estivermos lendo a primeira linha, este trecho sera executado.
					//Note que muitos passos realizados aqui se assemelham aos passos feitos 
					//quando estavamos lendo a primeira linha do arquivo, no entanto, ha algumas 
					//mudancas importantes.
					
					//Pegamos o primeiro computador informado na linha pelo seu nome. Ele agora sera um pai.
					Computador pai = rede.obterComputador(partes[0]);
					
					//Os demais computadores na linha serao filhos do pai acima.
					Computador filho1 = null;
					Computador filho2 = null;
					
					//Caso o segundo componente da linha representar um numero,
					//entao sabemos que a tarefa foi realizada.
					if(isNumeric(partes[1])) {					
						
						//Os nomes dos computadores precisam ser unicos, entao 
						//estamos definindo o nome destes computadores que realizaram a atividade
						//como "Leaf X", sendo que X representa um numero qualquer que sempre sera incrementado.
						String nome1 = "Leaf " + numeroLinhas;
						String nome2 = "Leaf " + (numeroLinhas + 1);
						
						//O valor que estes computadores que realizaram a tarefa receberao 
						//sera o valor informado nas linhas do arquivo, apos essa informacao ser convertida para Double.
						Double valor1 = Double.parseDouble(partes[1]);
						Double valor2 = Double.parseDouble(partes[2]);
						
						filho1 = new Computador(nome1, valor1);
						filho2 = new Computador(nome2, valor2);
						
					} else {
						
						//Caso o segundo componente da linha nao represente um numero,
						//entao sabemos que a tarefa ainda nao foi realizada e se dividiu em mais computadores.
						
						//Sendo assim, os nomes dos novos computadores podem ser os proprios nomes definidos
						//no arquivo (X1, por exemplo), e estes computadores nao terao nenhum valor.
						
						filho1 = new Computador(partes[1], null);
						filho2 = new Computador(partes[2], null);
					}
					
					pai.setFilhoE(filho1);
					pai.setFilhoD(filho2);
					
					filho1.setPai(pai);
					filho2.setPai(pai);
					
					//Adicionando os novos computadores filhos a nossa rede de computadores.
					rede.adicionarComputador(filho1.getNome(), filho1);
					rede.adicionarComputador(filho2.getNome(), filho2);
					
				}
				
				numeroLinhas++;
				
			}
			
			System.out.print(rede);
			
		} catch (IOException e) {
			System.out.println("Houve algum erro em relacao a leitura do arquivo! Talvez o arquivo nao tenha sido encontrado.");
			
		} catch (Exception e){
			System.out.println("Houve algum erro inesperado:\n\n." + e.getMessage());
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
