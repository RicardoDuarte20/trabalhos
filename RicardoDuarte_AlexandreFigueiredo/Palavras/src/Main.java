import java.lang.Character;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
	mainMenu();
	}
	
	public static void mainMenu() {
		String Palavra; //Palavra original formato String
		String Letra = "";	//Letra original formato string
		do { //loop principal de jogo
			
			Palavra = JOptionPane.showInputDialog("Palavra a descobrir / 0 para sair: ");	
			
			Jogo(Palavra, Letra);
		
		}while(!Palavra.contentEquals("0")); //terminar programa quando for inserido 0 em vez de uma palavra
	}
	
	public static void Jogo(String Palavra, String Letra) {
		if(!Palavra.contentEquals("0")) {
			
			char[] Palavra2 = new char[Palavra.length()]; //Palavra original em formato array
			char[] Revealed = new char[Palavra.length()]; //Letras reveladas
			List<Character> Chamadas = new ArrayList<>();	//Letras chamadas
			int Erros = 0;	//Contador de erros
			
			Startup(Palavra, Revealed, Palavra2, Erros, Chamadas); //Preencher arrays etc
			
			while(!checkWin(Palavra, Revealed) && !checkLoss(Palavra, Revealed, Erros)) { //loop secundario de jogo
				
				do { //Letra pode ter apenas um carater
					Letra = JOptionPane.showInputDialog("Letra: ");
				}while(Letra.length() != 1);
				
					Erros = chamarLetra(Letra.charAt(0),Chamadas,Palavra2,Palavra,Revealed, Erros); //Chamar letra inserida
				
			}
			
			if(checkWin(Palavra, Revealed)) {
				System.out.println("\n" + "You win! :)");
			}else {
				System.out.println("\n" + "You lose! :(");
			}
		}
	}
	
	public static void Startup(String Palavra, char[] Revealed, char[] Palavra2, int Erros, List<Character> Chamadas) {
		Boolean Check = false; //Verificar se existe um espaco na palavra
		
		for(int i=0;i<Palavra.length();i++) { //Preencher Palavra2 com letras da palavra original
			Palavra2[i] = Palavra.charAt(i);
			if(Palavra2[i] == ' ') { //verificar se existe espaco
				Check = true;
			}
		}

		for(int i=0;i<Palavra.length();i++) { //Colocar posicoes de array Revealed como '_'
			Revealed[i] = '_';
		}
		
		for(int i =0;i<(Palavra.length()+18);i++){
			System.out.print("-");
		}
		System.out.print("\n\n");
		if(Check) { //Se existirem espacos chamar letra ' ' por default
			Erros = chamarLetra(' ',Chamadas,Palavra2,Palavra,Revealed, Erros);
		}else {	//senao mostrar '_' numero igual a quantidade de letras
			for(int i=0;i<Palavra.length();i++) {
				System.out.print("_" + " ");
			}
			System.out.print(" | Erros: " + Erros + "\n\n");
		}
		
	}
	
	public static int chamarLetra(char Letra, List<Character> Chamadas, char[] Palavra2, String Palavra, char[] Revealed, int Erros) {	
		if(!existeChamadas(Letra, Chamadas, Erros)) { //verificar se letra ja foi chamada
				
			Erros = existePalavra(Palavra, Letra, Palavra2, Chamadas, Revealed, Erros); //Verificar se letra existe na palavra
		}else {
			System.out.println("Letra ja chamada | " + "Erros: " + Erros + "\n");
		}
			
		
		return Erros; 
	}
	
	public static int existePalavra(String Palavra, char Letra, char[] Palavra2, List<Character> Chamadas, char[] Revealed, int Erros) {
		boolean check = false; //Check para verificar se letra existe na palavra
		
		for(int i=0;i<Palavra.length();i++) {
			if((Character.toLowerCase(Letra) == Palavra2[i]) || (Character.toUpperCase(Letra) == Palavra2[i])) {	//Se letra inserida for igual a letra na posicao i na palavra entao
				Revealed[i] = Palavra2[i];	//na posicao i '_' passa a ser i no array Palavra2
				Chamadas.add(Letra); //Adicionar letra a List de letras chamadas
				check = true;	//Check passa a true confirmado que letra existe
			}

		}

		if(!check) { //se letra nao existier na palavra inteira
			Erros += 1;	//Erros avanca um valor
			Chamadas.add(Letra);
			System.out.println("Letra nao existe na palavra | " + "Erros: " + Erros + "\n");
			
		}else {
			mostrarRevealed(Revealed, Palavra, Erros);	//mostrar palavra revelada
		}
		return Erros;
	}
	
	public static boolean existeChamadas(char Letra, List<Character> Chamadas, int Erros) {
		for(int i=0;i<Chamadas.size();i++) {	//Verificar se letra ja foi chamada considerando uppercase e lowercase
			if ((Character.toLowerCase(Letra) == Chamadas.get(i)) || (Character.toUpperCase(Letra) == Chamadas.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkWin(String Palavra, char[] Revealed) {
		for(int i=0;i<Palavra.length();i++) {
			if(Revealed[i]  == '_') {	//se algum valor for '_' ou seja letra nao revelado return false
				return false;
			}
		}	//senao return true
		return true;
		
	}
	
	public static boolean checkLoss(String Palavra, char[] Revealed, int Erros) {
		if(Erros == 6) {	//O jogador eprde quando o contador de erros seja igual a 6
			return true;
		}
		return false;
	}
	
	public static void mostrarRevealed(char[] Revealed, String Palavra, int Erros) {
		for(int i=0;i<Palavra.length();i++) {
				System.out.print(Revealed[i]);
				System.out.print(" ");
		}
		System.out.print(" | " + "Erros: " + Erros + "\n\n");
	}
	
}