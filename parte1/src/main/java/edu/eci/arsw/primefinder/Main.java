package edu.eci.arsw.primefinder;

import edu.eci.arsw.primefinder.PrimeFinderThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		PrimeFinderThread pft = new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft2 = new PrimeFinderThread(10000000, 20000000);
		PrimeFinderThread pft3 = new PrimeFinderThread(20000000, 30000000);
		
		pft.start();
		pft2.start();
		pft3.start();

		Thread.sleep(5000);

		// Pause all threads
		pft.pauseThread();
		pft2.pauseThread();
		pft3.pauseThread();

		int totalPrimes = pft.getPrimes().size() +
				pft2.getPrimes().size() +
				pft3.getPrimes().size();

		System.out.println("Tiempo agotado. Primos encontrados hasta ahora: " + totalPrimes);
		System.out.println("Presiona ENTER para continuar...");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();

		// Resume threads
		pft.resumeThread();
		pft2.resumeThread();
		pft3.resumeThread();

		pft.join();
		pft2.join();
		pft3.join();

	}
	
}
