package it.unibs.square.roots;

import it.unibs.fp.mylib.EstrazioniCasuali;

public class NodoAlbero {
	
	public static final int OPERAZIONE_MAX = 3;
	public static final int OPERAZIONE_MIN = 0;
	private static final int NUMERO_MASSIMO = 10;
	private static final int NUMERO_MINIMO = 1;
	
	private String valoreNodo;
	private NodoAlbero destra;
	private NodoAlbero sinistra;
	
	public NodoAlbero(String valoreNodo, NodoAlbero destra, NodoAlbero sinistra) {
		super();
		this.valoreNodo = valoreNodo;
		this.destra = destra;
		this.sinistra = sinistra;
	}
	

	public String getValoreNodo() {
		return valoreNodo;
	}

	public void setValoreNodo(String valoreNodo) {
		this.valoreNodo = valoreNodo;
	}

	public NodoAlbero getDestra() {
		return destra;
	}

	public void setDestra(NodoAlbero destra) {
		this.destra = destra;
	}

	public NodoAlbero getSinistra() {
		return sinistra;
	}

	public void setSinistra(NodoAlbero sinistra) {
		this.sinistra = sinistra;
	}
	
	//questo è il metodo vero e proprio per la creazione dell'espressione casuale
	public static NodoAlbero nodoCasuale () {
		
		if (sceltaNumero()) {
			
			int estrazione = EstrazioniCasuali.estraiIntero(NUMERO_MINIMO, NUMERO_MASSIMO);
			String valoreNodo = String.valueOf(estrazione);
			
			return new NodoAlbero(valoreNodo, null, null);                                 //se il nodo è un numero ritorna il nodo con null sia a destra
			                                                                               //che a sinistra
		} else {
			
			int estrazione = EstrazioniCasuali.estraiIntero(OPERAZIONE_MIN, OPERAZIONE_MAX);         
			String valoreNodo = null;
			
			switch (estrazione) {
			
			case 0:
				valoreNodo = "+";
				break;
			
			case 1:
				valoreNodo = "-";
				break;
				
			case 2:
				valoreNodo = ":";
				break;
				
			case 3:
				valoreNodo = "*";
				break;
				
			default:
				break;	
			}                                           
			
			return new NodoAlbero(valoreNodo, nodoCasuale(), nodoCasuale());	
		}
	}
	
	//metodo booleano per capire se nell'estrazione casuale è stato scelto un numero
	public static boolean sceltaNumero () {                                  
		
		int scelta = EstrazioniCasuali.estraiIntero(1, 10);
		if (scelta >= 1 && scelta <=6) {
			return true;
		} else return false;
	}
	
	public String getEspressione () {
		
		if (isNumerical(valoreNodo)) {
			return valoreNodo;
			
		} else {
			return "(" + sinistra.getEspressione() + valoreNodo + destra.getEspressione() + ")";      //metto le parentesi per riuscire a leggere 
		}                                                                                             //l'espressione una volta stampata a video
	}
	
	private boolean isNumerical (String valore) {
		
		if (valore != "+" && valore != "-" && valore!= ":" && valore != "*") {
			return true;
		} else
			return false;	
	}
	
	//metodo per calcolare il risultato dell'espressione
	public int risultatoEspressione () throws ArithmeticException {
		
		if (isNumerical(valoreNodo)) {
			return Integer.parseInt(valoreNodo);
			
		} else {
			if (valoreNodo == "+") 
				return sinistra.risultatoEspressione() + destra.risultatoEspressione();
			 
			else if (valoreNodo == "-") 
				return sinistra.risultatoEspressione() - destra.risultatoEspressione();
			
			else if (valoreNodo == "*") 
				return sinistra.risultatoEspressione() * destra.risultatoEspressione();
			
			else if (destra.risultatoEspressione() == 0) {
				throw new ArithmeticException();
			}
			
			else
				return sinistra.risultatoEspressione() / destra.risultatoEspressione();
			
		} 
	}
	
	//metodo per la creazione dell'albero dall'espressione
	public NodoAlbero alberoDaEspressione (String espressione) {
		
		espressione = espressione.replace(" ", "");
		String espressioneDestra = null;
		String espressioneSinistra = null;
		
		
		if (nodoFoglia(espressione)) {
			
			int posizioneOperatore = 0;
			for (int i=0; i<espressione.length(); i++) {
				if (eOperazione(espressione.charAt(i)))
					posizioneOperatore = i;
			}
			valoreNodo = espressione.charAt(posizioneOperatore) + "";
			espressioneDestra = espressione.substring(posizioneOperatore+1);
			espressioneSinistra = espressione.substring(1, posizioneOperatore-1);
			destra = new NodoAlbero(espressioneDestra, null, null);
			sinistra = new NodoAlbero(espressioneSinistra, null, null);
			return new NodoAlbero(valoreNodo, destra, sinistra);
		
		} else {                                                                             
				
			String primoCarattere = espressione.charAt(1)+"";
				
			if (isNumerical(primoCarattere)) {                                              //questo è il caso del tipo 3+(....)
				valoreNodo = espressione.charAt(2)+"";
				espressioneDestra = espressione.substring(3);
				NodoAlbero nodoSinistra = new NodoAlbero(primoCarattere, null, null);
					
				return new NodoAlbero(valoreNodo, alberoDaEspressione(espressioneDestra), nodoSinistra);
				
			} else {
					
					int posizioneNodo = posizioneNodoPrincipale(espressione);
					valoreNodo = espressione.charAt(posizioneNodo)+"";
					espressioneDestra = espressione.substring(posizioneNodo+1);
					espressioneSinistra = espressione.substring(0, posizioneNodo-1);
					
					return new NodoAlbero(valoreNodo, alberoDaEspressione(espressioneDestra), alberoDaEspressione(espressioneSinistra));
			
			}
		}	
	}
	
	//metodo creato per inidividuare l'operatore padre in una cosa del tipo   ((3+(2*4)-(9+(2*3)))
	public int posizioneNodoPrincipale (String espressione) {
		
		int posizione = 0;
		for (int i=0; i<espressione.length()-1 || posizione == 0; i++) {
			
			if (espressione.charAt(i) == ')') {
			  posizione = i;    
			}
		}
		return posizione+1;
	}
	
	//un metodo per il caso base nella generazione dell'Albero a partire dalla stringa dell'espressione
	//in questo caso sto verificando come deve essere l'espressione per essere ricondotta al caso base
	//nella fattispecie del tipo ... (a+b)
	
	private boolean nodoFoglia (String espressione) {
		
		int operazione = 0;
		int parentesiA = 0;
		int parentesiC = 0;
		
		
		for (int i=0; i<espressione.length(); i++) {
			
			if (eOperazione(espressione.charAt(i))) 
				operazione++;
			if (parentesiAperta(espressione.charAt(i)))
				parentesiA++;
			if (parentesiChiusa(espressione.charAt(i)))
				parentesiC++;
		}
		
		if (operazione == 1 && parentesiA == 1 && parentesiC == 1) 
			return true;
		else
			return false;
	}
	
	//un metodo per capire se il carattere che sto analizzando è un operatore oppure no
	private boolean eOperazione (char carattere) {
		if (carattere == '+' || carattere == '-' || carattere == '*' || carattere == ':')
			return true;
		else
			return false;
	}
	
	// ... uguale a quello sopra ma con la parentesi aperta
	private boolean parentesiAperta (char carattere) {
		if (carattere == '(')
			return true;
		else
			return false;
	}
	
	// ... e con la parentesi chiusa 
	private boolean parentesiChiusa (char carattere) {
		if (carattere == ')')
			return true;
		else
			return false;
	}
	

}
