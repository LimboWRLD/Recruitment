package opcije;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import Interfejsi.Validirajuce;
import korisnici.Regrut;



public class Profil  implements Validirajuce{
	
	
	private boolean potvrdjen;
	private ArrayList<Vestina> vestine;
	private ArrayList<Komentar>komentari;
	private Regrut regrut;
	//Getteri i seteri
	public boolean isPotvrdjen() {
		return potvrdjen;
	}
	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
	}
	public ArrayList<Vestina> getVestine() {
		return vestine;
	}
	public void setVestine(ArrayList<Vestina> vestine) {
		this.vestine = vestine;
	}
	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}
	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	public Regrut getRegrut() {
		return regrut;
	}
	public void setRegrut(Regrut regrut) {
		this.regrut = regrut;
	}
	public Profil(boolean potvrdjen, ArrayList<Vestina> vestine, ArrayList<Komentar> komentari ,Regrut regrut) {
		super();
		this.potvrdjen = potvrdjen;
		this.vestine = vestine;
		this.komentari = komentari;
		this.regrut= regrut;
		//Konstruktor sa parametrima
	}
	public Profil() {
		//Kostruktor bez parametara
	}
	public ArrayList<Komentar> pretragaKomentaraPotvrdjeni(){
		ArrayList<Komentar>komentariProfila= this.getKomentari();
		ArrayList<Komentar>potvrdjeniKomentari = new ArrayList<>();
		for(int i=0; i<komentariProfila.size(); i++) {
			if(komentariProfila.get(i).getDatumPotvrde()!=null) {
				potvrdjeniKomentari.add(komentariProfila.get(i));//Dodavanje samo komentara koji su potvrdjeni
			}
		}
		return potvrdjeniKomentari;//Povracanje potvrdjenih komentara
	}
	
	public void dodajKomentar(Komentar komentar) {
		this.getKomentari().add(komentar);//Dodaj komentar
	}
	public  void upis(boolean potvrdjen, ArrayList<Vestina> vestine, ArrayList<Komentar> komentari, Regrut regrut) {
		String csvFajl="data/profili.csv";//Lokacija fajla
		try (FileWriter pisac = new FileWriter(csvFajl, true)) {//Upis preko try
			ArrayList<String> vestineProfila = new ArrayList<>();
			ArrayList<String> komentariProfila = new ArrayList<>();
			for (Vestina vestina : vestine) {
				vestineProfila.add(vestina.getNaziv());//Dodavanje samo naziva vestina
		         }
			for (Komentar komentar : komentari) {
				komentariProfila.add(komentar.getSadrzaj());//Dodavanje samo sadrzaja komentara
		         }
            String profil = potvrdjen + ";" + vestineProfila + ";" + komentariProfila + ";" +regrut.getKorisnickoIme()+ "\n";
            pisac.append(profil);//Pisanje profila
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
	}
	 public void upisPosle(boolean potvrdjen, ArrayList<Vestina> vestine, ArrayList<Komentar> komentari, Regrut regrut) {
	        String tempCsvFilePath = "data/temp.csv";//Lokacija privremenog fajla
	        String csvFilePath="data/profili.csv";//Lokacija fajla sa profilima
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
	                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {//Otvaranje sa try

	               String linija;
	               while ((linija = br.readLine()) != null) {//Citanje do kraja fajla
	                   if (!linija.isEmpty()) {
	                       String[] podaciProfila = linija.split(";");//Podela podataka
	                       
	                       ArrayList<String> vestineProfila = new ArrayList<>();
	                       ArrayList<String> komentariProfila = new ArrayList<>();
	                       for (Vestina vestina : vestine) {
	                    	   vestineProfila.add(vestina.getNaziv());
	                       }
	                       for (Komentar komentar : komentari) {
	                    	   komentariProfila.add(komentar.getSadrzaj());
	                       }
	                       if (podaciProfila.length >= 4 && podaciProfila[3].equals(regrut.getKorisnickoIme())) {
	                    	   String newLine = potvrdjen + ";" + vestineProfila + ";" + komentariProfila + ";" + regrut.getKorisnickoIme();
	                           bw.write(newLine);//Pisanje novih podataka
	                       } else {
	                           bw.write(linija);//Pisanje nepromenjenih linija
	                       }
	                       bw.newLine();//Nova linija
	                   }
	               }
	           } catch (IOException e) {
	               e.printStackTrace();//Hvatanje greske
	               return;
	           }

	        Path csvFilePathObj = Path.of(csvFilePath);//Putanja csv fajla
	        Path tempCsvFilePathObj = Path.of(tempCsvFilePath);//Putanja privremenog fajla
	        try {
	            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING);//Promena fajlova
	           
	        } catch (IOException e) {
	            e.printStackTrace();//Hvatanje greske
	            System.out.println("Neuspesna promena.");
	        }
	    }
	public Profil podaciProfila(String str) throws FileNotFoundException, IOException {
		if( pretragaProfila(str)) {//Ako postoji profil u fajlu
			String csvFajl = "data/profili.csv";//Ime datoteke
	        String linija;
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
	            while ((linija = br.readLine()) != null) {
	            	 if (!linija.isEmpty()) {
	                String[] podaciProfila = linija.split(";");

	                Vestina v1 = new Vestina();
	                Komentar k1=new Komentar();
	                ArrayList<Vestina>vestine = v1.pretragaVestina(podaciProfila[1]);
	               
	                ArrayList<Komentar>komentari = k1.pretragaKomentara(podaciProfila[2]);
	                
	                for (String data : podaciProfila) {
	                    if (data.equals(str)) {
	                        return new Profil(Boolean.parseBoolean(podaciProfila[0]), vestine, komentari, null);
	                        //Vracanje profila
	                    }}}}}
	        
		}
		return new Profil();
	}
	public boolean pretragaProfila(String pretragaKorisnickogImena)throws FileNotFoundException {
        String csvFajl = "data/profili.csv";//Ime datoteke
        String linija;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
            while ((linija = br.readLine()) != null) {//Citanje do kraja fajla
            	 if (!linija.isEmpty()) {
                String[] podaciProfila = linija.split(";");//Podela podataka
               
                
                for (String data : podaciProfila) {
                    if (data.equals(pretragaKorisnickogImena)) {
                        return true;//Postoji profil za korisnika
                        
                    }
                }
            }}
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
       
        
        return false; // Ne postoji profil za korisnika
    }
	public ArrayList<Profil> ucitavanjeSvihProfila() {
	    String csvFajl = "data/profili.csv";//Ime fajla
	    ArrayList<Profil> profili = new ArrayList<>();//Lista za profile

	    try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) {
	        
	        String linija;
	        while ((linija = citac.readLine()) != null) {
	            String[] profileData = linija.split(";");//Podela podataka profila

	           
	            boolean potvrdjen = Boolean.parseBoolean(profileData[0]);
	            
	           
	            Vestina v1=new Vestina();
	            ArrayList<Vestina> vestine = v1.pretragaVestina(profileData[1]);
	            Komentar k1=new Komentar();

	            ArrayList<Komentar> komentari = k1.pretragaKomentara(profileData[2]); 
	            Regrut regrut = new Regrut().pretragaRegruta(profileData[3]);

	            // Kreiranje profila preko atributa
	            Profil profile = new Profil(potvrdjen, vestine, komentari, regrut);

	            // Dodavanje profila u listu
	            profili.add(profile);
	        }

	        System.out.println("Svi profili su ucitani.");
	    } catch (IOException e) {
	        System.out.println("Desila se greska pri ucitavanju: " + e.getMessage());//Hvatanje greske
	    }

	    return profili;
	}
	public String toIspis() {//Lepsi ispis komentara za profil
		return "Profil regruta "+ regrut.getKorisnickoIme() + ", Komentari na profilu:" + komentari;
	}
    
	@Override
	public String toString() {
		return "Profil [potvrdjen=" + potvrdjen + ", vestine=" + vestine + ", komentari=" + komentari + ", regrut="
				+ regrut + "]";
	}
	@Override
	public void validiraj() {
		this.setPotvrdjen(true);//Promena vrednosti za potrvrdu profila
		
	}
	
}
