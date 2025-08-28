package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
        private static volatile boolean pausa = false;
        private final Object monitorPausa;
        
	public Galgo(Carril carril,Object monitorPausa, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
                this.monitorPausa = monitorPausa;
		paso = 0;
		this.regl=reg;
	}

	public  void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
                        synchronized(monitorPausa){
                            while(pausa){
                                monitorPausa.wait();
                            }
                        }
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
                        synchronized(regl) {
                            if (paso == carril.size()) {						
                                carril.finish();
                                int ubicacion=regl.getUltimaPosicionAlcanzada();
                                regl.setUltimaPosicionAlcanzada(ubicacion+1);
                                System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
                                if (ubicacion==1){
                                    regl.setGanador(this.getName());
                                }
                            
                            }
                        }
		
		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
        
        public void stopGalgos(){
            synchronized(monitorPausa){
                pausa = true;
            }
            
            
        }
        
        public void playGalgos(){
            synchronized(monitorPausa){
                pausa = false;
                monitorPausa.notifyAll();      
            }
        }

}
