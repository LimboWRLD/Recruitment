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
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import opcije.Komentar;
import opcije.Ponuda;
import opcije.Profil;
import opcije.Vestina;


public class Regrut extends Korisnik {
	private Profil profil;
	private String ime;
	private String prezime;
	private int telefon;
	//Getteri i seteri
	public Profil getProfil() {
		return profil;
	}
	public void setProfil(Profil profil) {
		this.profil = profil;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public int getTelefon() {
		return telefon;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
	public Regrut() {
		//Konstruktor bez atributa
	}
	public Regrut(String korisnickoIme, String lozinka, String email, Profil profil, String ime, String prezime,
			int telefon) {
		super(korisnickoIme, lozinka, email);
		this.profil = profil;
		this.ime = ime;
		this.prezime = prezime;
		this.telefon = telefon;
		//Konstruktor sa atributima
	}
	public void ispisPonuda() throws IOException {
	    String cavFajl = "data/ponude.csv"; // Ime datoteke
	    String linija; 
	    int potrebneVestine = 3; //filter vestina

	    try (BufferedReader br = new BufferedReader(new FileReader(cavFajl))) { //Otvaranje sa try 
	        boolean imaPoklapanjaVestina = false; 
	        ArrayList<Vestina>vestineRegruta=new Profil().podaciProfila(this.getKorisnickoIme()).getVestine();
	        while ((linija = br.readLine()) != null) { //Citanje do kraja fajla
	            String[] podaciPonude = linija.split(";"); //Podela podataka
	            String[] ponudaVesetine = podaciPonude[4].substring(1, podaciPonude[4].length() - 1).split(","); 
	            int poklopljeneVestine = 0;

	            if (ponudaVesetine.length >= potrebneVestine) { //Ako ponuda ima vise od 3 vestine
	                for (String ponudaVestina : ponudaVesetine) {
	                    for (Vestina vestina : vestineRegruta) {
	                        if (vestina.getNaziv().equalsIgnoreCase(ponudaVestina.trim())) { //Ako se imena vestina poklapaju
	                        	poklopljeneVestine++;
	                            break;
	                        }
	                    }
	                }

	                if (poklopljeneVestine >= potrebneVestine) {//Ako korisnik ima vise vestina od potrebnih
	                	imaPoklapanjaVestina = true;
	                    System.out.println("Naziv ponude: " + podaciPonude[0]);
	                    System.out.println("Opis ponude: " + podaciPonude[1]);
	                    System.out.println("Vestine sa kategorijama i nivoima: " + new Ponuda().povratakPonude(podaciPonude[1]).getPotrebneVestine());
	                    System.out.println("Potvrdeni komentari za tu ponudu: " + (new Ponuda().povratakPonude(podaciPonude[1]).pretragaKomentaraPotvrdjeni()));
	                    System.out.println("----------------------------------------");
	                }
	            } else {
	                for (String ponudaVestine : ponudaVesetine) { 
	                    for (Vestina vestina : vestineRegruta) {
	                        if (vestina.getNaziv().equalsIgnoreCase(ponudaVestine.trim())) {
	                        	poklopljeneVestine++;
	                            break;
	                        }
	                    }
	                }

	                if (poklopljeneVestine >= 3) {//Posle prolaska kroz vestine
	                	imaPoklapanjaVestina = true;
	                    System.out.println("Naziv ponude: " + podaciPonude[1]);
	                    System.out.println("Opis ponude: " + podaciPonude[2]);
	                    System.out.println("Vestine sa kategorijama i nivoima: " + new Ponuda().povratakPonude(podaciPonude[1]).getPotrebneVestine());
	                    System.out.println("Potvrdeni komentari za tu ponudu: " + (new Ponuda().povratakPonude(podaciPonude[1]).pretragaKomentaraPotvrdjeni()));
	                    System.out.println("----------------------------------------");
	                }
	            }
	        }

	        if (!imaPoklapanjaVestina) {
	            System.out.println("Nema ponuda za vaše veštine.");
	        }
	    }
	}
	public Regrut pretragaRegruta(String str)throws FileNotFoundException, IOException  {
		 String csvFajl = "data/regruti.csv"; //Ime csv fajla
	        String linija;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje fajla sa try
	            while ((linija = br.readLine()) != null) {
	                String[] podaciRegruta = linija.split(";"); //Podela podataka
	                
	                if(podaciRegruta[0].equalsIgnoreCase(str)) { //Ako je pronadjeno korisnicko ime
	                	if(podaciRegruta[3].isEmpty()) {
	                		podaciRegruta[3]=null;
	                	}
	                	Profil p1=new Profil();
	                	Regrut r1=new Regrut(podaciRegruta[0], podaciRegruta[1], podaciRegruta[2], 
	                			p1.podaciProfila(podaciRegruta[0]), podaciRegruta[4], podaciRegruta[5], Integer.parseInt(podaciRegruta[6]));
	                	return r1; //Povratak regruta
	                }}
	            }
			return new Regrut(); //Povratak regruta
	        } 
	public boolean postojanjeRegruta(String str)throws FileNotFoundException, IOException  {
		 String csvFajl = "data/regruti.csv";//Ime csv fajla
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
	public ArrayList<String> proveraTipa(String korisnickoIme) {
		String csvFajl = "data/regruti.csv"; //Ime csv fajla
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) // Otvaranje csv fajla 
        {
            String red;
            while ((red = citac.readLine()) != null) // Citanje do kraja postojenja teksta 
            	{
            	
                String[] polja = red.split(";");//Podela podataka
                
                String postojeceKorisnickoIme = polja[0];
                String profilKorisnikaString=polja[3];
                String lozinkaKorisnika = polja[1];
                String emailKorisnika = polja[2];
                String imeKorisnika = polja[4];
                String prezimeKorisnika = polja[5];
                String telefonKorisnika = polja[6];
                ArrayList<String> podaciKorisnika = new ArrayList<String>();
                if (postojeceKorisnickoIme.equals(korisnickoIme)) {
                	podaciKorisnika.add(postojeceKorisnickoIme);
                	podaciKorisnika.add(lozinkaKorisnika);
                	podaciKorisnika.add(emailKorisnika);
                	podaciKorisnika.add(profilKorisnikaString);
                	podaciKorisnika.add(imeKorisnika);
                	podaciKorisnika.add(prezimeKorisnika);
                	podaciKorisnika.add(telefonKorisnika);
                    return podaciKorisnika; //Povratak svih podataka regruta
                   
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske

        }
		return null; //Nema regruta
	}
	public void upis(String korisnickoIme, String lozinka, String email, Profil profil, String ime, String prezime,
			int telefon) {
		String csvFajl="data/regruti.csv"; //Ime fajla
		try (FileWriter pisac = new FileWriter(csvFajl, true)) { //Otvaranje fajla sa try
			Boolean profilStr;
			if(profil==null) {
				profilStr=null;
			}else {
				profilStr=profil.isPotvrdjen();
			}
            String regrut = korisnickoIme + ";" + lozinka + ";" + email + ";" +profilStr+ ";" + ime + ";" + prezime + ";" + telefon + "\n";
            pisac.append(regrut); //Upis u fajl
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
	}
	public void upisPosle(String korisnickoIme, String lozinka, String email, Profil profil, String ime, String prezime,
			int telefon) {
		String tempCsvFilePath = "data/temp.csv";// Privremeni fajl 
        String csvFilePath="data/regruti.csv"; //Ime datokete za promenu
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {

               String linija;
               while ((linija = br.readLine()) != null) {
                   if (!linija.isEmpty()) {
                       String[] profileData = linija.split(";");//Podela podataka
                       
                       
                       if (profileData.length >= 7 && profileData[0].equalsIgnoreCase(korisnickoIme)) { //Ako je korisnik u tom redu menjaju mu se podaci
                    	   
                    	   String novaLinija = korisnickoIme + ";" + lozinka + ";" + email + ";" + true + ";" + ime + ";" + prezime + ";" +  telefon;
                           bw.write(novaLinija);
                       } else {
                           bw.write(linija); //Upis linije bez korisnika
                       }
                       bw.newLine(); 
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();//Hvatanje greske
               return;
           }

        Path csvFilePathObj = Path.of(csvFilePath); //Put do fajla sa regrutima
        Path tempCsvFilePathObj = Path.of(tempCsvFilePath); //Put do privremenog fajla
        try {
            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING);//Promena fajla
           
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
            System.out.println("Neuspesna promena.");
        }
	}
	   
	public boolean postojiProfil() {
		if(this.getProfil()==null) { //Dohvatranje profila
			return false;
		}else {
			
			return true;
		}
		// Provera da li postoji profil ili ne
	}
	public void kreirajProfil(Regrut r2) {
	    
	        Scanner sc = new Scanner(System.in); //Otvaranje scenera
	        ArrayList<Vestina> listaVestina = new ArrayList<>(); //Kreiranje prazne liste vestina
	        String krajnjiIzbor = "da"; //Krajnji  izbor za zaustavljanje dodavanja vestina

	        while (!krajnjiIzbor.equals("ne")) { //Mogucnost dodavanja vise vestina
	            Vestina.Kategorija kategorija = null;
	            Vestina.Nivo nivo = null;
	            try { 
	                System.out.println("Unesite naziv vestine: ");
	                String naziv = sc.nextLine(); //Unos naziva za vestinu

	                boolean potvrda = false;
	                boolean potvrda2 = false;

	                while (!potvrda) { //Dokle god korisnik ne unese validnu opciju radi ovo
	                    System.out.println("Unesite kategoriju vestine: ");
	                    System.out.println("1. Intelektualna");
	                    System.out.println("2. Socijalna");
	                    System.out.println("3. Komunikaciona");
	                    System.out.println("4. Fizicka");

	                    if (sc.hasNextInt()) {
	                        int izbor = sc.nextInt();
	                        sc.nextLine();

	                        switch (izbor) {
	                            case 1:
	                                kategorija = Vestina.Kategorija.INTELEKTUALNA;
	                                potvrda = true;
	                                break;
	                            case 2:
	                                kategorija = Vestina.Kategorija.SOCIJALNA;
	                                potvrda = true;
	                                break;
	                            case 3:
	                                kategorija = Vestina.Kategorija.KOMUNIKACIONA;
	                                potvrda = true;
	                                break;
	                            case 4:
	                                kategorija = Vestina.Kategorija.FIZICKA;
	                                potvrda = true;
	                                break;
	                            default:
	                                System.out.println("Niste uneli validan izbor! Pokusajte ponovo.");
	                        }
	                    } else {
	                        System.out.println("Niste uneli validan izbor! Pokusajte ponovo.");
	                        sc.nextLine(); //Ciscenje sledece linije
	                    }
	                }

	                while (!potvrda2) { //Dokle god korisnik ne unese validnu opciju radi ovo
	                    System.out.println("Unesite nivo vestine: ");
	                    System.out.println("1. Nizak");
	                    System.out.println("2. Srednji");
	                    System.out.println("3. Visok");

	                    if (sc.hasNextInt()) {
	                        int izbor2 = sc.nextInt();
	                        sc.nextLine();

	                        switch (izbor2) {
	                            case 1:
	                                nivo = Vestina.Nivo.NIZAK;
	                                potvrda2 = true;
	                                break;
	                            case 2:
	                                nivo = Vestina.Nivo.SREDNJI;
	                                potvrda2 = true;
	                                break;
	                            case 3:
	                                nivo = Vestina.Nivo.VISOK;
	                                potvrda2 = true;
	                                break;
	                            default:
	                                System.out.println("Niste uneli validan izbor! Pokusajte ponovo.");
	                        }
	                    } else {
	                        System.out.println("Niste uneli validan izbor! Pokusajte ponovo.");
	                        sc.nextLine();//Ciscenje sledece linije
	                    }
	                }

	                Vestina v1 = new Vestina(naziv, null, kategorija, nivo);//Kreiranje vestine na osnovu unosa
	                v1.upis(naziv, null, kategorija, nivo); //Upisivanje vestine u csv
	                listaVestina.add(v1); //Dodavanje vestine u listu

	                System.out.println("Da li zelite da dodate jos vestina? (da/ne)"); 

	                krajnjiIzbor = sc.nextLine().toLowerCase();//Izbor korisnika za kraj

	                while (!krajnjiIzbor.equals("da") && !krajnjiIzbor.equals("ne")) { //Provera unosa
	                    System.out.println("Unesite validan odgovor!");
	                    krajnjiIzbor = sc.nextLine().toLowerCase();
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Unesite validnu opciju!"); //Provera tipa unosa
	                sc.nextLine();  //Ciscenje sledece linije
	            }
	        }

	        ArrayList<Komentar> komentari = new ArrayList<>();//Komentari su na kreaciji prazni
	        Profil p1 = new Profil(false, listaVestina, komentari, r2);
	        p1.upis(false, listaVestina, komentari, r2);//Upis profila
	        
	    }
	
	@Override
	public String toString() {
		return "Regrut [profil=" + profil + ", ime=" + ime + ", prezime=" + prezime + ", telefon=" + telefon + "]";
	}
	
	
}
