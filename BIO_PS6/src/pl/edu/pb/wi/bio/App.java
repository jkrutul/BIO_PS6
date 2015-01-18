/*
Do zadania do³¹czone s¹ pliki z próbkami biometrycznymi kilku u¿ytkowników.
Pojedynczy plik zawiera dane zebrane podczas jednej sesji.
Nazwa pliku sk³ada siê z imienia osoby oraz numeru sesji.
Zbierany by³ czas wciœniêcia klawiszy (czyli od naciœniêcia do zwolnienia) w trakcie wpisywania pewnego tekstu.
W kolejnych wierszach pliku zapisano kolejne symbole wciskanych klawiszy (znak) i (po znaku tab) czas (w milisekundach).
Zadanie wymaga przetworzenia tego pliku na wektor cech - nale¿y (osobno w ka¿dym pliku) policzyæ œredni czas wciœniêcia - w ten sposób ka¿da osoba bêdzie reprezentowana przez wektor o d³ugoœci ~26 (uwaga: byæ mo¿e nie wszystkie znaki by³y u¿yte, st¹d d³ugoœæ wektora mo¿e byæ mniejsza).

Nastêpnie:
Aby uzyskaæ ocenê 3.0 nale¿y wykonaæ badanie polegaj¹ce na identyfikacji (klasyfikacji) osób na podstawie tych próbek.
Mo¿esz w tym celu pos³u¿yæ siê np. pakietem Weka.
Pamiêtaj o podzieleniu zbioru na czêœæ ucz¹c¹ i testow¹. Ewentualnie zastosuj inn¹ metodê kroswalidacji.

W prezentacji przedstaw opis przeprowadzonych badañ oraz wykorzystanej metody,
a tak¿e wyniki zawieraj¹ce: procent prawid³owo rozpoznanych próbek (ogólny oraz dla poszczególnych osób) 
oraz macierz pomy³ek (pokazuj¹c¹, które osoby by³y mylone z którymi).

Na ocenê 4.0 nale¿y stworzyæ w³asny program - system biometryczny. Powinien pozwalaæ on na wykonanie zadañ rejestracji i identyfikacji.
Mo¿esz ograniczyæ siê do jednej, prostej metody (np. k-najbli¿szych s¹siadów), 
ale zbadaj wyniki dla ró¿nych wartoœci parametrów (np. ró¿nych wartoœci k lub ró¿nych miar odleg³oœci).

Na ocenê 5.0: rozszerz program o mo¿liwoœæ wykonania weryfikacji.
Wykonaj badania dla zadania weryfikacji i przedstaw wyniki z uwzglêdnieniem miar FAR (False Acceptance Rate), FRR (False Rejection Rate), EER (Equal Error Rate). FAR i FRR przedstaw w postaci krzywych FAR(T), FRR(T) oraz ROC.
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
