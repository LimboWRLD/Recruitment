package opcije;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


import korisnici.Regruter;



public class Ponuda {
	private Regruter regruter;
	private String naziv;
	private String opis;
	public enum Tip_Ponude{
		POSAO,
		PRAKSA
	}
	private Tip_Ponude tip;
	private ArrayList<Vestina>potrebneVestine;
	private ArrayList<Komentar>komentari;
	public Ponuda(Regruter regruter, String naziv, String opis, Tip_Ponude tip, ArrayList<Vestina> potrebneVestine,
			ArrayList<Komentar> komentari) {
		super();
		this.regruter = regruter;
		this.naziv = naziv;
		this.opis = opis;
		this.tip = tip;
		this.potrebneVestine = potrebneVestine;
		this.komentari = komentari;
		//Konstruktor sa parametrima
	}
	//getteri i setteri
	public Tip_Ponude getTip() {
		return tip;
	}
	public void setTip(Tip_Ponude tip) {
		this.tip = tip;
	}
	
	public Regruter getRegruter() {
		return regruter;
	}
	public void setRegruter(Regruter regruter) {
		this.regruter = regruter;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<Vestina> getPotrebneVestine() {
		return potrebneVestine;
	}
	public void setPotrebneVestine(ArrayList<Vestina> potrebneVestine) {
		this.potrebneVestine = potrebneVestine;
	}
	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}
	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	public Ponuda() {
		//Konstruktor bez parametara
	}
	public void dodajKomentar(Komentar komentar) {
		komentari.add(komentar);//Dodavanje komentara
		
	}
	@Override
	public String toString() {
		return "Ponuda [regruter=" + regruter + ", naziv=" + naziv + ", opis=" + opis + ", potrebneVestine="
				+ potrebneVestine + ", komentari=" + komentari + "]";
	}
	public ArrayList<Komentar> pretragaKomentaraPotvrdjeni(){
		ArrayList<Komentar>komentariProfila= this.getKomentari();
		ArrayList<Komentar>potvrdjeniKomentari = new ArrayList<>();
		for(int i=0; i<komentariProfila.size(); i++) {
			if(komentariProfila.get(i).getDatumPotvrde()!=null) {
				potvrdjeniKomentari.add(komentariProfila.get(i));//Dodavanje samo potvrdjenih komentara
			}
		}
		return potvrdjeniKomentari;
	}
	public  ArrayList<Ponuda> ucitavanjePonuda() {
	    ArrayList<Ponuda> ponude = new ArrayList<>();//Lista za ponuda
	    String csvFajl="data/ponude.csv";//Ime datoteke
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Citanje preko try
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] offerData = line.split(";");
	            Regruter regruter =  new Regruter().pretragaRegrutera(offerData[0]); 
	            String naziv = offerData[1];
	            String opis = offerData[2];
	            String tipString = offerData[3];
	            Tip_Ponude tip = Tip_Ponude.valueOf(tipString);
	            ArrayList<Vestina> potrebneVestine = new Vestina().pretragaVestina(offerData[4]);
                ArrayList<Komentar> komentari = new Komentar().pretragaKomentara(offerData[5]);
	            Ponuda ponuda = new Ponuda(regruter, naziv, opis, tip, potrebneVestine, komentari);
	            ponude.add(ponuda);//Dodavanje ponude
	        }
	    } catch (IOException e) {
	        e.printStackTrace();//Hvatanje greske
	    }

	    return ponude;//Vracanje ponuda
	}
	public String toIspis() {//Lepsi ispis podataka za profil
		return "Ponuda regrutera "+ regruter.getKorisnickoIme()+", potrebne vestine"+potrebneVestine + ", Komentari na ponudi:" + komentari;
	}
	
	public  ArrayList<Ponuda> pretragaPonuda(String naziv) {
        String csvFajl = "data/ponude.csv";//Ime datoteke
        
        String bezZagrada = naziv.substring(1, naziv.length() - 1);

        String [] nazivi=bezZagrada.split(",");
        
        String linija;
        ArrayList<Ponuda>ponude=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
            while ((linija = br.readLine()) != null) {
                String[] podaciPonuda = linija.split(";");//Podela podataka
                for(int i=0;i<nazivi.length;i++) {
                if(podaciPonuda[1].equalsIgnoreCase(nazivi[i].trim())) {//Ako se Nazivi poklapaju
                	//Kreiraj ponudu
                    Ponuda p1 = new Ponuda(null, podaciPonuda[1],podaciPonuda[2],Tip_Ponude.valueOf(podaciPonuda[3]), new Vestina().pretragaVestina(podaciPonuda[4]), new Komentar().pretragaKomentara(podaciPonuda[5]));
                    ponude.add(p1);//Dodaj ponudu u listu
                
                }}
            }
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
        
        return ponude;//Povratak ponuda
    }
	public boolean postojiPonuda(String str) {
		String csvFajl = "data/ponude.csv";//Ime datoteke
        String linija;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
            while ((linija = br.readLine()) != null) {
                String[] podaciPonude = linija.split(";");
                if(podaciPonude[1].equalsIgnoreCase(str)) {//Provera da li postoji naziv ponude u ponudama
                	return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
        
        return false;//Nema ponude 
    }
	public Ponuda povratakPonude(String str) {
		String csvFajl = "data/ponude.csv";//Ime datoteke
        String linija;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Citanje preko try
            while ((linija = br.readLine()) != null) {
                String[] podaciPonude = linija.split(";");//Podela podataka
                if(podaciPonude[1].equalsIgnoreCase(str)) {//Ako se naziv poklapa
                	//Vrati ponudu
                	return new Ponuda(null, podaciPonude[1],podaciPonude[2],Tip_Ponude.valueOf(podaciPonude[3]), new Vestina().pretragaVestina(podaciPonude[4]), new Komentar().pretragaKomentara(podaciPonude[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
        
        return  new Ponuda();
    }
	public void upisPosle(Regruter regruter, String naziv, String opis, Tip_Ponude tip, ArrayList<Vestina> potrebneVestine,
			ArrayList<Komentar> komentari) {
		String tempCsvFilePath = "data/temp.csv";//Privremeni fajl
        String csvFilePath="data/ponude.csv";//Fajl za izmeniti
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {//Citanje sa try

               String linija;
               while ((linija = br.readLine()) != null) {
                   if (!linija.isEmpty()) {
                       String[] podaciPonude = linija.split(";");//Podela podataka
                       
                       ArrayList<String> vestine = new ArrayList<>();
                       ArrayList<String> komentariPonude = new ArrayList<>();
                       for (Vestina vestina : potrebneVestine) {
                           vestine.add(vestina.getNaziv());//Hvatanje svih naziva vestina
                       }
                       for (Komentar komentar : komentari) {
                    	   komentariPonude.add(komentar.getSadrzaj());//Hvatanje svih sadrzaja
                       }
                       if (podaciPonude.length >= 4 && podaciPonude[0].equals(regruter.getKorisnickoIme()) && podaciPonude[0].equalsIgnoreCase(naziv)) {
                    	   String novaLinija = regruter.getKorisnickoIme() + ";" + naziv + ";" + opis  + ";" + tip + ";" + vestine + ";" + komentariPonude;
                           bw.write(novaLinija);//Pisanje linije ako se poklapa po kreatoru
                       } else {
                           bw.write(linija);
                       }
                       bw.newLine();
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();//Hvatanje greske
               return;
           }

        Path csvFilePathObj = Path.of(csvFilePath);//Putanja do csv-a
        Path tempCsvFilePathObj = Path.of(tempCsvFilePath);//Putanja do privremenog fajla
        try {
            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING);//Zamena fajlova
           
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
            System.out.println("Neuspesna zamena.");
        }
    }
	
	public void upis(Regruter regruter, String naziv, String opis, Tip_Ponude tip, ArrayList<Vestina> potrebneVestine,
			ArrayList<Komentar> komentari) {
		String csvFajl="data/ponude.csv";//Ime datoteke
		try (FileWriter pisac = new FileWriter(csvFajl, true)) {
			ArrayList<String> vestine = new ArrayList<>();
			ArrayList<String> komentariPonude = new ArrayList<>();
			if(postojiPonuda(naziv)) {
				return;
			}
			for (Vestina vestina : potrebneVestine) {
				vestine.add(vestina.getNaziv());//Dohvatanje naziva
		         }
			for (Komentar komentar : komentari) {
				komentariPonude.add(komentar.getSadrzaj());//Dohvatanje sadrzaja komentara
		         }
			
            String ponuda = regruter.getKorisnickoIme() + ";" + naziv + ";" + opis + ";"+ tip + ";" + vestine + ";" + komentariPonude +  "\n";
            pisac.append(ponuda);//Pisanje ponude
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
	}
}
