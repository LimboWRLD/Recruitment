package opcije;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Interfejsi.Validirajuce;
import korisnici.Korisnik;
import korisnici.Regrut;
import korisnici.Regruter;
import korisnici.Validator;




public class Komentar implements Validirajuce{
	private String sadrzaj;
	private	LocalDateTime datumKreacije;
	private LocalDateTime datumPotvrde;
	private Korisnik kreator;
	//Getteri i seteri
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	public LocalDateTime getDatumKreacije() {
		return datumKreacije;
	}
	public void setDatumKreacije(LocalDateTime datumKreacije) {
		this.datumKreacije = datumKreacije;
	}
	public LocalDateTime getDatumPotvrde() {
		return datumPotvrde;
	}
	public void setDatumPotvrde(LocalDateTime datumPotvrde) {
		this.datumPotvrde = datumPotvrde;
	}
	
	public Korisnik getKreator() {
		return kreator;
	}
	public void setKreator(Korisnik kreator) {
		this.kreator = kreator;
	}
	public Komentar(String sadrzaj, LocalDateTime datumKreacije, LocalDateTime datumPotvrde, Korisnik kreator) {
		super();
		this.sadrzaj = sadrzaj;
		this.datumKreacije = datumKreacije;
		this.datumPotvrde = datumPotvrde;
		this.kreator = kreator;
		//Komentar sa parametrima
	}
	public Komentar() {
		//Komentar bez parametara
	}
	public Regrut pretragaRegruta(String str)throws FileNotFoundException, IOException  {
		 String csvFajl = "data/regruti.csv";//Ime datoteke koja se otvara
	        String linija;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje preko try
	            while ((linija = br.readLine()) != null) {
	                String[] podaciRegruta = linija.split(";");//Podela podataka
	                
	                if(podaciRegruta[0].equalsIgnoreCase(str)) {//Ako postoji regrut
	                	if(podaciRegruta[3].isEmpty()) {
	                		podaciRegruta[3]=null;
	                	}
	                	//Kreiranje nadjenog regruta
	                	Regrut r1=new Regrut(podaciRegruta[0], podaciRegruta[1], podaciRegruta[2], 
	                			null, podaciRegruta[4], podaciRegruta[5], Integer.parseInt(podaciRegruta[6]));
	                	return r1;
	                }}
	            }
			return new Regrut();
	        } 
	public void upisPosleBrisanja(ArrayList<Komentar>komentari) throws IOException {
		String csvFajl="data/komentari.csv";//Ime fajla
		try (FileWriter pisac = new FileWriter(csvFajl)) {//Otvaranje preko try
			for(Komentar komentar:komentari) {//Za svaki komentar uradi upis
				komentar.upis(komentar.getSadrzaj(), komentar.getDatumKreacije(), komentar.getDatumPotvrde(), komentar.getKreator());
			}
		}
	}
	public void upisPosle(String sadrzaj, LocalDateTime datumKreacije, LocalDateTime datumPotvrde, Korisnik kreator) {
		String tempCsvFilePath = "data/temp.csv";//Privremeni fajl
        String csvFilePath="data/komentari.csv";//Fajl na lokaciji za komentare
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {//Otvaranje preko try

               String linija;
               while ((linija = br.readLine()) != null) {//Citanje do kraja fajla
                   if (!linija.isEmpty()) {
                       String[] podaciKomentara = linija.split(",");
                       
                       
                       if (podaciKomentara.length >= 4 && podaciKomentara[0].equals(sadrzaj)) {//Ako se poklapa sadrzaj
                    	   String novaLinija = sadrzaj + "," + datumKreacije + "," + datumPotvrde + "," + podaciKomentara[3];
                           bw.write(novaLinija);
                       } else {
                           bw.write(linija);//Ako ne pisi normalno
                       }
                       bw.newLine();
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();//Hvatanje sadrzaja
               return;
           }

        Path csvFilePathObj = Path.of(csvFilePath);//Lokacija csv fajla
        Path tempCsvFilePathObj = Path.of(tempCsvFilePath);//Lokacija privremenog fajla
        try {
            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING);//Promena fajlova
           
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
            System.out.println("Neuspesna zamena.");
        }
    }
	
	public void upis(String sadrzaj, LocalDateTime datumKreacije, LocalDateTime datumPotvrde, Korisnik kreator) {
		String csvFajl="data/komentari.csv";//Ime datoteke 
		try (FileWriter pisac = new FileWriter(csvFajl, true)) {//Otvaranje preko try
			
            String komentar = sadrzaj + "," + datumKreacije + "," + datumPotvrde + "," +kreator.getKorisnickoIme()+ "\n";
            pisac.append(komentar);//Upis komentara u csv
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
	}public ArrayList<Komentar> sviKomentari() {
	    String csvFile = "data/komentari.csv";//Ime datoteke
	    DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");//Formatter za Datum i vreme zato sto se desavalo da drugacije zapise napr nekad zapise sa vise mili sekundi i to remeti posle dohvatanje komentara
	    
	    
	    ArrayList<Komentar> komentari = new ArrayList<>();//Lista za komentare
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        String linija;
	        while ((linija = br.readLine()) != null) {
	            String[] podaciKomentara = linija.split(",");//Podela podataka
	            
	                
	                    LocalDateTime datumKreacije = null;
	                    if (!podaciKomentara[1].equalsIgnoreCase("null")) {
	                    	datumKreacije = LocalDateTime.parse(podaciKomentara[1], formater);
	                    }
	                    
	                    LocalDateTime datumPotvrde = null;
	                    if (!podaciKomentara[2].equalsIgnoreCase("null")) {
	                    	datumPotvrde = LocalDateTime.parse(podaciKomentara[2], formater);
	                    }
	                    
	                    Komentar v1;//Kreiranje komentara na osnovu kreatora
	                    if (new Regrut().postojanjeRegruta(podaciKomentara[3])) {
	                        v1 = new Komentar(podaciKomentara[0], datumKreacije, datumPotvrde, pretragaRegruta(podaciKomentara[3]));
	                    } else if (new Regruter().postojanjeRegrutera(podaciKomentara[3])) {
	                        v1 = new Komentar(podaciKomentara[0], datumKreacije, datumPotvrde, new Regruter().pretragaRegrutera(podaciKomentara[3]));
	                    } else {
	                        v1 = new Komentar(podaciKomentara[0], datumKreacije, datumPotvrde, new Validator().pretragaValidatora(podaciKomentara[3]));
	                    }
	                    //Dodavanje komentara u listu
	                    komentari.add(v1);
	                
	            
	        }
	    } catch (IOException e) {
	        e.printStackTrace();//Hvatanje greske
	    }
	    
	    return komentari;//Vracanje liste
	}
	public ArrayList<Komentar> pretragaKomentara(String str) {
	    String csvFile = "data/komentari.csv";//Ime datoteke
	    DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");//Formater radi ranije objasnjenog
	    
	    String bezZagrada = str.substring(1, str.length() - 1);//String koji se prosledi je u ovom obliku [Primer, Primer] i tako se otarasimo zagrada

	    String[] nazivi = bezZagrada.split(",");//Podela naziva
	    ArrayList<Komentar> komentari = new ArrayList<>();//Lista za komentare
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {//Otvaranje preko try
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] podaciKomentara = line.split(",");
	            for (int i = 0; i < nazivi.length; i++) {
	                if (podaciKomentara[0].trim().equalsIgnoreCase(nazivi[i].trim())) {//Ako se sadrzaji poklapaju
	                   
	                    //Ako je datum potvrde nije string null
	                    LocalDateTime datumKreacije = LocalDateTime.parse(podaciKomentara[1], formater);
	                    
	                    
	                    LocalDateTime datumPotvrde = null;
	                    if (!podaciKomentara[2].equalsIgnoreCase("null")) {
	                    	datumPotvrde = LocalDateTime.parse(podaciKomentara[2], formater);
	                    }
	                    //Kreiranje na osnovu kreatora
	                    Komentar v1;
	                   
	                    v1 = new Komentar(podaciKomentara[0], datumKreacije, datumPotvrde,null);
	                   
	                    //Dodavanje komentara
	                    komentari.add(v1);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();//Hvatanje greske
	    }
	    
	    return komentari;//Vracanje komentara
	}
	
	public String toString() {
		String datumPotvrdeStr = datumPotvrde != null ? datumPotvrde.toString() : "";//Ukoliko je datum potvrde null pretvaramo ga u string
        return sadrzaj + ";" + datumKreacije + ";" + datumPotvrdeStr;
    }
	@Override
	public void validiraj() {
		LocalDateTime datum  = LocalDateTime.now();
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");//Formater zbog prethodno navedenog
        LocalDateTime formatiraniDatum = LocalDateTime.parse(datum.format(formater), formater);
		this.setDatumPotvrde(formatiraniDatum);//Dodavanje datuma potvrde
		
	}
	
   
}

