package it.unibs.square.roots;

import it.unibs.fp.mylib.EstrazioniCasuali;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.InputDati;

public class Main {

	private static final String ERRORE_DIVISIONE = "Errore! Nell'espressione è presente una divisione per 0 ...";
	private static final String TITOLO_MENU = "MENU ALGEBRICO";
	private static final String[] VOCI_MENU = {"STAMPA IL RISULTATO DELL'ESPRESSIONE", "STAMPA L'ESPRESSIONE", "CREA NUOVA ESPRESSIONE CASUALE", "INSERISCI ESPRESSIONE DA TASTIERA"};

	public static void main(String[] args) {
	
		String valoreNodoRadice = creaValoreNodoPrincipale();
		NodoAlbero alberoEspressione = new NodoAlbero(valoreNodoRadice, NodoAlbero.nodoCasuale(), NodoAlbero.nodoCasuale());
		String espressione = alberoEspressione.getEspressione();
		String espressioneDaTastiera = null;
		
		MyMenu menuAlgebra = new MyMenu(TITOLO_MENU, VOCI_MENU);
		int scelta = -1;
		
		do {
		scelta = menuAlgebra.scegli();
		
		switch (scelta) {
		
		case 1:                             //stampa il risultato dell'espressione
			int risultato;
			
			try {
				risultato = alberoEspressione.risultatoEspressione();
				System.out.println("Il risultato è: " + risultato);
				} catch (ArithmeticException e) {
					System.err.println(ERRORE_DIVISIONE);
				}
			break;
			
		case 2:                            //stampa l'espressione con le giuste parentesi
			System.out.println("\nEspressione = " + espressione);
			break;
		
		case 3:                            //crea una nuova espressione casuale (un albero binario)
			valoreNodoRadice = creaValoreNodoPrincipale();
			alberoEspressione = new NodoAlbero(valoreNodoRadice, NodoAlbero.nodoCasuale(), NodoAlbero.nodoCasuale());
			espressione = alberoEspressione.getEspressione();
			break;
			
		case 4:                           //dovrebbe trasformare la stringa dell'espressione in un albero binario ... ma non funziona
			espressioneDaTastiera = InputDati.leggiStringa("Inserisci l'espressione: ");
			NodoAlbero nodoEspressioneTastiera = new NodoAlbero(null, null, null);
			nodoEspressioneTastiera.alberoDaEspressione(espressioneDaTastiera);
			espressione = nodoEspressioneTastiera.getEspressione();
			break;
			
			
		default:
			break;
		}
		
		} while (scelta != 0);
		

	}
	
	private static String creaValoreNodoPrincipale () {       //metodo per la creazione della prima operazione che sarà il primo nodo
		                                                                                                         
		int estrazione = EstrazioniCasuali.estraiIntero(NodoAlbero.OPERAZIONE_MIN, NodoAlbero.OPERAZIONE_MAX); 
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
		
		return valoreNodo;
		
	}

}
