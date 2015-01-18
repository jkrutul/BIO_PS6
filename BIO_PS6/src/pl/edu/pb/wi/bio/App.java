/*
Do zadania do��czone s� pliki z pr�bkami biometrycznymi kilku u�ytkownik�w.
Pojedynczy plik zawiera dane zebrane podczas jednej sesji.
Nazwa pliku sk�ada si� z imienia osoby oraz numeru sesji.
Zbierany by� czas wci�ni�cia klawiszy (czyli od naci�ni�cia do zwolnienia) w trakcie wpisywania pewnego tekstu.
W kolejnych wierszach pliku zapisano kolejne symbole wciskanych klawiszy (znak) i (po znaku tab) czas (w milisekundach).
Zadanie wymaga przetworzenia tego pliku na wektor cech - nale�y (osobno w ka�dym pliku) policzy� �redni czas wci�ni�cia - w ten spos�b ka�da osoba b�dzie reprezentowana przez wektor o d�ugo�ci ~26 (uwaga: by� mo�e nie wszystkie znaki by�y u�yte, st�d d�ugo�� wektora mo�e by� mniejsza).

Nast�pnie:
Aby uzyska� ocen� 3.0 nale�y wykona� badanie polegaj�ce na identyfikacji (klasyfikacji) os�b na podstawie tych pr�bek.
Mo�esz w tym celu pos�u�y� si� np. pakietem Weka.
Pami�taj o podzieleniu zbioru na cz�� ucz�c� i testow�. Ewentualnie zastosuj inn� metod� kroswalidacji.

W prezentacji przedstaw opis przeprowadzonych bada� oraz wykorzystanej metody,
a tak�e wyniki zawieraj�ce: procent prawid�owo rozpoznanych pr�bek (og�lny oraz dla poszczeg�lnych os�b) 
oraz macierz pomy�ek (pokazuj�c�, kt�re osoby by�y mylone z kt�rymi).

Na ocen� 4.0 nale�y stworzy� w�asny program - system biometryczny. Powinien pozwala� on na wykonanie zada� rejestracji i identyfikacji.
Mo�esz ograniczy� si� do jednej, prostej metody (np. k-najbli�szych s�siad�w), 
ale zbadaj wyniki dla r�nych warto�ci parametr�w (np. r�nych warto�ci k lub r�nych miar odleg�o�ci).

Na ocen� 5.0: rozszerz program o mo�liwo�� wykonania weryfikacji.
Wykonaj badania dla zadania weryfikacji i przedstaw wyniki z uwzgl�dnieniem miar FAR (False Acceptance Rate), FRR (False Rejection Rate), EER (Equal Error Rate). FAR i FRR przedstaw w postaci krzywych FAR(T), FRR(T) oraz ROC.
 */

package pl.edu.pb.wi.bio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pl.edu.pb.wi.bio.models.UserKeyboardScan;

public class App {
	public static long pressedT, releasedT;
	public static String lastKeyPressed;
	public static Map<String,UserKeyboardScan> data = new HashMap<>();

	
	public static void rel() {
		System.out.println(releasedT - pressedT);
	}
	private static void readData(File dataDir) throws IOException{
		
		String[] files = dataDir.list();
		
		for(String filename : files) {
			String userName = filename.split("\\s")[0];
			UserKeyboardScan uks = data.get(userName);
			if (uks==null) {
				uks = new UserKeyboardScan(userName);
			}
			
			uks.addDataFromFile(dataDir+File.separator+filename);
			data.put(userName, uks);
		}
	}
	
	public static void printData() {
		for(String userName : data.keySet()) {
			UserKeyboardScan uks = data.get(userName);
			System.out.println("User: "+userName+" uks:"+uks);
		}
		System.out.println("/n");
	}
	
	public static void main(String[] args) {

		
		try {
			readData(new File("data"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String userName : data.keySet()) {
			UserKeyboardScan uks = data.get(userName);
			System.out.println("User: "+userName+" uks:"+uks);
		}
			
		MyKeyboardScanner ks = new MyKeyboardScanner(data );


		

	}

}
