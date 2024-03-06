package korisnici;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import opcije.Komentar;
import opcije.Ponuda;
import opcije.Ponuda.Tip_Ponude;
import opcije.Profil;
import opcije.Vestina.Kategorija;
import opcije.Vestina.Nivo;
import opcije.Vestina;


public class Regruter extends Korisnik {
	private String naziv;
	private String adresa;
	private String zastupnik;
	private int telefon;
	private boolean premiumNalog;
	private ArrayList<Ponuda> ponude;
	//Getteri i seteri
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getZastupnik() {
		return zastupnik;
	}
	public void setZastupnik(String zastupnik) {
		this.zastupnik = zastupnik;
	}
	public int getTelefon() {
		return telefon;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
	public boolean isPremiumNalog() {
		return premiumNalog;
	}
	public void setPremiumNalog(boolean premiumNalog) {
		this.premiumNalog = premiumNalog;
	}
	public ArrayList<Ponuda> getPonude() {
		return ponude;
	}
	public void setPonude(ArrayList<Ponuda> ponude) {
		this.ponude = ponude;
	}
	public Regruter(String korisnickoIme, String lozinka, String email, String naziv, String adresa, String zastupnik,
			int telefon, boolean premiumNalog, ArrayList<Ponuda> ponude) {
		super(korisnickoIme, lozinka, email);
		this.naziv = naziv;
		this.adresa = adresa;
		this.zastupnik = zastupnik;
		this.telefon = telefon;
		this.premiumNalog = premiumNalog;
		this.ponude = ponude;
		//Konstruktor  sa atributima
	}
	
	@Override
	public String toString() {
		return "Regruter [naziv=" + naziv + ", adresa=" + adresa + ", zastupnik=" + zastupnik + ", telefon=" + telefon
				+ ", premiumNalog=" + premiumNalog + ", ponude=" + ponude + "]";
	}

	public Regruter() {
		super();
		////Konstruktor  bez atributa
	}
	public void kreiranjePonude() {
		System.out.println("Unesite naziv ponude: ");
		Scanner sc = new Scanner(System.in);//Otvaranje skenera
		String naziv = sc.nextLine(); //Unos korisnika
		System.out.println("Unesite opis ponude: ");
		String opis = sc.nextLine();//Unos korisnika
		System.out.println("Unesite tip ponude(posao/praksa): ");
		String tip =  sc.nextLine();//Unos korisnika
		while(!tip.equalsIgnoreCase("posao") && !tip.equalsIgnoreCase("praksa") ) { //Ako ne unese validno
			System.out.println("Unesite tip ponude(posao/praksa): ");
			tip =  sc.nextLine();//Unos korisnika
		}
		
		
		ArrayList <Vestina>listaVestina= new ArrayList<>();
		String krajnjiIzbor="da";
		while(!krajnjiIzbor.equals("ne")){ //Mogucnost dodavanja vise vestina
		Vestina.Kategorija kategorija = null;
		Vestina.Nivo nivo = null;
		System.out.println("Unesite naziv vestine: ");
		String nazivV =  sc.nextLine();//Unos korisnika
		boolean potvrda = false;
		boolean potvrda2 = false;
		while(potvrda==false) {
		System.out.println("Unesite kategoriju vestine: ");
		System.out.println("1. Intelektualna");
		System.out.println("2. Socijalna");
		System.out.println("3. Komunikaciona");
		System.out.println("4. Fizicka");
		int izbor = sc.nextInt();//Unos korisnika
		switch (izbor) {
		case 1:
			kategorija = Kategorija.INTELEKTUALNA;
			potvrda=true;
			break;
		case 2:
			kategorija = Kategorija.SOCIJALNA;
			potvrda=true;
			break;
		case 3:
			kategorija = Kategorija.KOMUNIKACIONA;
			potvrda=true;
			break;
		case 4:
			kategorija = Kategorija.FIZICKA;
			potvrda=true;
			break;
		default:
			System.out.println("Niste uneli validan izbor! Pokusajte ponovo. ");
		}}
		while(potvrda2==false) {
		System.out.println("Unesite nivo vestine: ");
		System.out.println("1. Nizak");
		System.out.println("2. Srednji");
		System.out.println("3. Visok");
		int izbor2 = sc.nextInt();//Unos korisnika
		switch (izbor2) {
		case 1:
			nivo = Nivo.NIZAK;
			potvrda2=true;
			break;
		case 2:
			nivo = Nivo.SREDNJI;
			potvrda2=true;
			break;
		case 3:
			nivo = Nivo.VISOK;
			potvrda2=true;
			break;
		default:
			System.out.println("Niste uneli validan izbor! Pokusajte ponovo. ");
		}
		}
		Vestina v1 =  new Vestina (nazivV, LocalDate.now(), kategorija, nivo);
		v1.upis(nazivV, LocalDate.now(), kategorija, nivo);
		listaVestina.add(v1);
		System.out.println("Da li zelite da dodate jos vestina?(da/ne)");
		krajnjiIzbor = sc.nextLine().toLowerCase();
		while (!krajnjiIzbor.equals("da") && !krajnjiIzbor.equals("ne")) {
			System.out.println("Unesite validan odgovor!");
			krajnjiIzbor = sc.nextLine().toLowerCase();
		}}
		
		ArrayList<Komentar> komentari = new ArrayList<>();
		Ponuda p1 = new Ponuda(this ,naziv, opis, Tip_Ponude.valueOf(tip.toUpperCase()), listaVestina, komentari); //Kreiranje ponude 
		p1.upis(this, naziv, opis, Tip_Ponude.valueOf(tip.toUpperCase()), listaVestina, komentari);//Upis kreirane ponude
	}
	public void upisPosle(Regruter r1, String naziv, String adresa, String zastupnik, int telefon, boolean premiumNalog,
			ArrayList<Ponuda> ponude) {
        String tempCsvFilePath = "data/temp.csv"; //Privremeni fajl
        String csvFilePath="data/regruteri.csv"; //Fajl sa regruterima
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath)); //Otvaranje fajlova sa try
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {

               String linija;
               while ((linija = br.readLine()) != null) {//Citanje do kraja fajla
                   if (!linija.isEmpty()) {
                       String[] podaciPonude = linija.split(";");//Podela podataka
                       
                       ArrayList<String> data = new ArrayList<>();
                       for (Ponuda ponuda : ponude) {
                           data.add(ponuda.getNaziv()); //Za svaku ponudu napisi samo naziv
                       }
                      
                       if (podaciPonude.length >= 9 && podaciPonude[0].equals(r1.getKorisnickoIme())) { //Ako se poklapa kreator promeni ponudu
                    	   String novaLinija = r1.getKorisnickoIme() + ";" + naziv + ";" + adresa + ";" + zastupnik + ";" + telefon + ";" +  premiumNalog + ";" + data;
                           bw.write(novaLinija); //Upis promene
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

        Path csvFilePathObj = Path.of(csvFilePath);//Put do csv fajla
        Path tempCsvFilePathObj = Path.of(tempCsvFilePath);//Put do privremenog fajla
        try {
            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING); //Promena fajla
            System.out.println("Uspesno promenjen fajl.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Neuspesno promenjen fajl.");
        }
    }
	
	public Regruter pretragaRegrutera(String str) {
		String csvFajl = "data/regruteri.csv"; //Ime falja
        String linija;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) { //Otvaranje sa try
            while ((linija = br.readLine()) != null) {
                String[] podaciRegrutera = linija.split(";");//Podela podataka
                if(podaciRegrutera[0].equalsIgnoreCase(str)) {
                	//Povratak regruta sa tim korisnickim imenom
                	return new Regruter(podaciRegrutera[0], podaciRegrutera[1], podaciRegrutera[2], podaciRegrutera[3], podaciRegrutera[4], podaciRegrutera[5], Integer.parseInt(podaciRegrutera[6]), Boolean.parseBoolean(podaciRegrutera[7]),  ucitavanjePonudaKorsinika(podaciRegrutera[0]));
                }}
            }
	  catch (IOException e) {
         e.printStackTrace();
     }
		return new Regruter();//U slucaju da nema tog regrutera
	}
	      
	
	public void upis(String korisnickoIme, String lozinka, String email,String naziv, String adresa, String zastupnik,
			int telefon, boolean premiumNalog, ArrayList<Ponuda> ponude) {
		String csvFajl="data/regruteri.csv"; //Ime fajla 
		ArrayList<String>podudeStr = new ArrayList<>();
		try (FileWriter pisac = new FileWriter(csvFajl, true)) { //Otvaranje fajla sa try
			if(ponude==null) { //Ako nema ponuda
				podudeStr=null;
			}else {
			for(Ponuda p1:ponude) { //Ako ima ponuda napisi im samo naziv
				podudeStr.add(p1.getNaziv());
			}}
            String regruter = korisnickoIme + ";" + lozinka + ";" + email + ";" +naziv+ ";" + adresa + ";" + zastupnik + ";" + telefon + ";" + premiumNalog + ";"+ podudeStr+ "\n";
            pisac.append(regruter); //Pisanje u fajl
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public boolean postojanjeRegrutera(String str)throws FileNotFoundException, IOException  {
		 String csvFajl = "data/regruteri.csv";//Ime csv fajla
	        String linija;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje csv fajla sa try
	            while ((linija = br.readLine()) != null) {
	                String[] podaciRegruta = linija.split(";"); //Podela podataka
	                if(podaciRegruta[0].equalsIgnoreCase(str)) {
	                	
	                	return true;//Pronadjen regrut u fajlu
	                }}
	            }
			return false;//Nije pronadjen regrut u fajlu
	        }    
	public void pretraga() {
		Scanner sc= new Scanner(System.in);//Otvaranje skenera
		 ArrayList<Ponuda> ponude = ucitavanjePonudaKorsinika(this.getKorisnickoIme());//Ucitavanje svih ponuda
		 if(ponude.size()==0) {
			 System.out.println("Nemate ponude");
			 return;
		 }
		 System.out.println("Odaberite ponudu na koju zelite dodati komentar:");
         for (int i = 0; i < ponude.size(); i++) {
             System.out.println((i + 1) + ". " + ponude.get(i).toIspis());
         }
         int ponudaIndeks = sc.nextInt();//Izbor korisnika
         
         if (ponudaIndeks >= 1 && ponudaIndeks <= ponude.size()) {
             Ponuda izabranaPonuda = ponude.get(ponudaIndeks - 1);
             this.pretragaRegruta(izabranaPonuda);//Ispis regruta
	}}
	public  ArrayList<Ponuda> ucitavanjePonudaKorsinika(String str) {
	    ArrayList<Ponuda> ponude = new ArrayList<>();
	    String csvFajl="data/ponude.csv";//Ime fajla
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje fajla preko try
	        String linija;
	        while ((linija = br.readLine()) != null) {
	        	
	            String[] offerData = linija.split(";");//Podela podataka
	            if(str.equalsIgnoreCase(offerData[0])) {//Ako se poklapa sa korisnickim imenom

	            String naziv = offerData[1];
	            String opis = offerData[2];
	            String tipString = offerData[3];
	            Tip_Ponude tip = Tip_Ponude.valueOf(tipString);
	            ArrayList<Vestina> potrebneVestine = new Vestina().pretragaVestina(offerData[4]);
                ArrayList<Komentar> komentari = new Komentar().pretragaKomentara(offerData[5]);
	            Ponuda ponuda = new Ponuda(this, naziv, opis, tip, potrebneVestine, komentari);//kreiraj ponudu
	            ponude.add(ponuda);//Dodaj ponudu
	        }}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return ponude;
	}
	public void pretragaRegruta(Ponuda ponuda) {
		String csvFajl = "data/profili.csv";//Ime fajla
        String linija;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje sa try
            while ((linija = br.readLine()) != null) {
            	
                String[] podaciRegruta = linija.split(";");
                String bezZagrada = podaciRegruta[1].substring(1, podaciRegruta[1].length() - 1);
                
                String [] nazivi=bezZagrada.split(",");
                for(Vestina vestina:ponuda.getPotrebneVestine()) {
                	for(String naziv:nazivi) 
                		
                		if(naziv.equalsIgnoreCase(vestina.getNaziv())) {//Ako se poklapaju sa nazivima vestina
                			Regrut r1 = new Regrut().pretragaRegruta(podaciRegruta[3]);
                			
                			System.out.println(r1.getIme()+","+ r1.getPrezime()+","+ r1.getTelefon() +","+ new Profil().podaciProfila(podaciRegruta[0]).getVestine() + new Profil().podaciProfila(podaciRegruta[0]).pretragaKomentaraPotvrdjeni().toString());
                	
                }}
            }}
	  catch (IOException e) {
         e.printStackTrace();//Hvatanje greske
     }
		
	}
	}

