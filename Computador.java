/**
 * @author Arthur Sudbrack Ibarra, Willian Magnum Albeche
 */
public class Computador {

	/* EXPLICACAO DA CLASSE
	====================================================================
	A classe Computador seria semelhante a um nodo (Node) de uma arvore.
	 
	Todos os computadores tem um nome unico e podem ter um valor,
	alem de possuirem referencias para seus computadores pais e filhos.
	====================================================================
	*/
	
	private String nome;
	private Double valor;
	
	private Computador pai;
	
	private Computador filhoE;
	private Computador filhoD;
	
	//Construtor da classe Computador.
	public Computador(String nome, Double valor) {
		
		this.nome = nome;
		this.valor = valor;
		
		this.pai = null;
		this.filhoE = null;
		this.filhoD = null;
		
	}
	
	//Metodos getters da classe Computador.
	public String getNome() {
		return this.nome;
	}
	public Double getValor() {
		return this.valor;
	}
	public Computador getPai() {
		return this.pai;
	}
	public Computador getFilhoE() {
		return this.filhoE;
	}
	public Computador getFilhoD() {
		return this.filhoD;
	}
	
	//Metodos setters da classe Computador.
	public void setPai(Computador computador) {
		this.pai = computador;
	}
	public void setFilhoE(Computador computador) {
		this.filhoE = computador;
	}
	public void setFilhoD(Computador computador) {
		this.filhoD = computador;
	}
	
	//Metodo toString da classe Computador.
	public String toString() {
		
		String pai = this.pai != null ? "\nNome Pai: " + this.pai.nome + "\nValor Pai: " + this.pai.valor : "-";
		String filhoE = this.filhoE != null ? "\nNome FilhoE: " + this.filhoE.nome + "\nValor FilhoE: " + this.filhoE.valor : "-";
		String filhoD = this.filhoD != null ? "\nNome FilhoD: " + this.filhoD.nome + "\nValor FilhoD: " + this.filhoD.valor : "-";
		
		return "\nNome: " + this.nome + "\nValor: " + this.valor + "\nPai: " + pai + "\nFilho E: " + filhoE + "\nFilho D: " + filhoD;
	}
	
}
