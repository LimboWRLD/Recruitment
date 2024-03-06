package korisnici;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import korisnici.Validator.Uza_Strucna_Oblast;
import opcije.Komentar;
import opcije.Ponuda;
import opcije.Profil;
import opcije.Vestina;
import opcije.Ponuda.Tip_Ponude;

public class Administrator extends Korisnik {
	
	public Administrator(String korisnickoIme, String lozinka, String email) {
		super(korisnickoIme, lozinka, email);
		//Konstruktor sa parametrima
	}

	public Administrator() {
		//Konstruktor bez parametara
	}
	public  ArrayList<Ponuda> ucitavanjePonuda() {
	    ArrayList<Ponuda> ponude = new ArrayList<>();
	    String csvFajl="data/ponude.csv";//Ime datoteke
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje sa try
	        String linija;
	        while ((linija = br.readLine()) != null) {
	        	
	            String[] podaciPonude = linija.split(";");//Podela podataka
	            if(podaciPonude[5].length()==2) {
	            	continue;
	            }
	            Regruter regruter =  new Regruter().pretragaRegrutera(podaciPonude[0]); //Vracanje regruta
	            String naziv = podaciPonude[1];
	            String opis = podaciPonude[2];
	            String tipString = podaciPonude[3];
	            Tip_Ponude tip = Tip_Ponude.valueOf(tipString);
	            ArrayList<Vestina> potrebneVestine = new Vestina().pretragaVestina(podaciPonude[4]); //Vracanje vestina
                ArrayList<Komentar> komentari = new Komentar().pretragaKomentara(podaciPonude[5]); //Vracanje komentara
	            Ponuda ponuda = new Ponuda(regruter, naziv, opis, tip, potrebneVestine, komentari);
	            ponude.add(ponuda); // Dodavanje ponude
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return ponude; //Vracanje ponuda
	}
	public  boolean proveraKorisnickogImena(String korisnickoIme) {
		String csvFajl = "data/korisnici.csv"; //Ime datoteke koju otvaramo
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) // Otvaranje csv fajla sa try kako ne bi morali da zatvaramo citac
        {
            String red;
            while ((red = citac.readLine()) != null) // Citanje do kraja postojenja teksta 
            	{
                String[] polja = red.split(","); //Odvajanje reci u redu
                String postojeceKorisnickoIme = polja[0]; //Korisnicko ime se nalazi na poziciji 0 
                if (postojeceKorisnickoIme.equals(korisnickoIme)) { //Provera jednakosti stingova
                    return true; // Korisnicko ime zauzeto
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); //U slucaju pojave greske
        }
        return false; // Korisnicko ime nije zauzeto
    }
	
	public  boolean proveraEmaila(String email) {
		String csvFajl = "data/korisnici.csv"; //Ime datoteke koju otvaramo
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) { // Otvaranje csv fajla sa try kako ne bi morali da zatvaramo citac
            String red; 
            while ((red = citac.readLine()) != null) {
                String[] polja = red.split(","); // Citanje svakog polja nakog otklanjanja ;
                String postojeciEmail = polja[2];  //Email se nalazi na poziciji 0 
                if (postojeciEmail.equals(email)) { //Provera jednakosti stingova
                    return true; // Email je zauzet
                }
            }
        } catch (IOException e) {
            e.printStackTrace();//U slucaju pojave greske
        }
        return false; // Email nije zauzet 
    }
	public Administrator pretragaAdministratora(String str)throws FileNotFoundException, IOException  {
		 String csvFile = "data/administratori.csv"; //Ime datoteke koju otvaramo
	        String line;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) { // Otvaranje csv fajla sa try kako ne bi morali da zatvaramo citac
	            while ((line = br.readLine()) != null) {
	                String[] podaciAdministratora = line.split(",");
	                if(podaciAdministratora[0].equalsIgnoreCase(str)) { //Provera postojanja korisnickog imena u fajlu
	          
	                	Administrator a1=new Administrator(podaciAdministratora[0], podaciAdministratora[1], podaciAdministratora[2]);
	                	// Kreiranje postojeceg administratora
	                	return a1;
	                }}
	            }
			return new Administrator(); //U slucaju da ne postoji administrator
	        } 
	public  void upis(String korisnickoIme, String sifra, String email,String tipKorisnika) {
		String csvFajl = "data/korisnici.csv"; //Ime datoteke koju otvaramo
        try (FileWriter pisac = new FileWriter(csvFajl, true)) { // Otvaranje csv fajla sa try kako ne bi morali da zatvaramo pisac
            String korisnik = korisnickoIme + "," + sifra + "," + email + "," +tipKorisnika+ "\n";
            pisac.append(korisnik); // Pisanje podataka u fajl
        } catch (IOException e) {
            e.printStackTrace(); //U slucaju pojave greske
        }
    }
	
	
	public void RegistrujKorisnika() {
		Scanner sce = new Scanner(System.in);//Otvaranje skenera
		try {
	    
	    System.out.println("Unesite korisnicko ime:");
	    String korisnickoIme = sce.nextLine(); //Unos korisnickog imena
	    while (proveraKorisnickogImena(korisnickoIme)) { //Provera postojenja korisnickog imena
	        System.out.println("Korisnicko ime je vec zauzeto, unesite nesto drugo: ");
	        korisnickoIme = sce.nextLine();	// Ako postoji korisnik unosi sve dok ne unese ne postojoce
	    }
	    System.out.println("Unesite lozinku:"); 
	    String lozinka = sce.nextLine(); //Unos za lozinku
	    System.out.println("Unesite email:");
	    String email = sce.nextLine(); // Unos za email
	    while (proveraEmaila(email)) { //Provera postojanja emaila
	        System.out.println("Email je vec zauzet, unesite drugi email:");
	        email = sce.nextLine(); //Ako postoji korisnik unosi sve dok ne unese ne postojoce
	    }
	    System.out.println("Unesite tip korisnika: ");
	    String tipKorisnika = sce.nextLine().toLowerCase(); //Unos tipa korisnika
	    while (!(tipKorisnika.equals("regrut") || tipKorisnika.equals("regruter") || tipKorisnika.equals("validator"))) {
	        System.out.println("Unesite validni tip (regrut/regruter/validator)");
	        tipKorisnika = sce.nextLine(); //Korisnik unosi sve dok ne unese nesto od vazeceg
	    }
	    if (tipKorisnika.equals("regrut")) {
	        System.out.println("Unesite ime:");
	        String ime = sce.nextLine(); //Unos za ime regruta
	        System.out.println("Unesite prezime:");
	        String prezime = sce.nextLine(); //Unos za prezime regruta
	        System.out.println("Unesite telefon:");
	        int telefon = sce.nextInt(); //Unos za telefon regruta
	        Regrut r1 = new Regrut();
	        r1.upis(korisnickoIme, lozinka, email, null, ime, prezime, telefon); //Upis regruta u fajl za regrute
	        upis(korisnickoIme, lozinka, email, tipKorisnika); //Upis korisnika u fajl za korisnike
	    } else if (tipKorisnika.equals("regruter")) {
	        System.out.println("Unesite naziv:");
	        String naziv = sce.nextLine(); //Unos za nazivi regrutera
	        System.out.println("Unesite adresu:");
	        String adresa = sce.nextLine(); //Unos za adresu regrutera
	        System.out.println("Unesite zastupnika:");
	        String zastupnik = sce.nextLine();//Unos za zastupnika regrutera
	        System.out.println("Unesite telefon:");
	        int telefon = sce.nextInt(); //Unos za telefon regrutera
	        System.out.println("Unesite da li je nalog premium:");
	        boolean premium = sce.nextBoolean(); //Unos za premium nalog regrutera
	        Regruter r1 = new Regruter();
	        r1.upis(korisnickoIme, lozinka, email, naziv, adresa, zastupnik, telefon, premium, null);//Upis regrutera u fajl za regrutere
	        upis(korisnickoIme, lozinka, email, tipKorisnika); //Upis korisnika u fajl za korisnike
	    } else if (tipKorisnika.equals("validator")) {
	        System.out.println("Unesite ime:");
	        String ime = sce.nextLine();//Unos za ime validatora
	        System.out.println("Unesite prezime:");
	        String prezime = sce.nextLine();//Unos za prezime validatora
	        Integer identifikator = new Random().nextInt(); //Identifikator validatora
	        boolean stanje = false;
	        Uza_Strucna_Oblast polje = null;
	        while (!stanje) {
	            System.out.println("Unesite uzu strucnu oblast:");
	            System.out.println("1.Tehnicko-Tehnolosko polje");
	            System.out.println("2.Drustveno-Humanisticko polje");
	            int izbor = sce.nextInt(); //Unos za uzu strucnu oblast
	            switch (izbor) {
	                case 1: //Slucaj broj jedan isto kao i if(izbor==1)
	                    polje = Uza_Strucna_Oblast.tehnickoTehnoloskoPolje;
	                    stanje = true; // Ne trazi se vise od korisnika da unese
	                    break;
	                case 2:
	                    polje = Uza_Strucna_Oblast.drustvenoHumanistickoPolje;
	                    stanje = true;// Ne trazi se vise od korisnika da unese
	                    break;
	                default:
	                    System.out.println("Unesite validnu opciju!"); // Greska pri unosu korisnika
	            }
	        }
	        Validator v1 = new Validator();
	        v1.upis(korisnickoIme, lozinka, email, identifikator, ime, prezime, polje); // Upis validatora u fajl za validatore
	        upis(korisnickoIme, lozinka, email, tipKorisnika);  //Upis korisnika u fajl za korisnike
	    }
	    System.out.println("Korisnik uspesno registrovan!"); //Potvrda o registraciji
		}catch(InputMismatchException e) { //Greska pri unosu odnosno unesen drugi tip promenljive od one koja se trazi
        	System.out.println("Unesite validnu opciju!");
            sce.nextLine();  // Izbegavanje beskonacne petlje
        }
		}
	public static ArrayList<Profil> ucitavanjeSvihProfila() {
	    String csvFile = "data/profili.csv";//Ime datoteke
	    ArrayList<Profil> profili = new ArrayList<>(); //Prazna lista za profile

	    try (BufferedReader citac = new BufferedReader(new FileReader(csvFile))) { //Otvaranje sa try kako ne bi zatvarali citac licno
	        
	        String linija;
	        while ((linija = citac.readLine()) != null) { //Citanje do kraja fajla
	            String[] podaciProfila = linija.split(";"); //Podala linije na podatke
	           
	            if( podaciProfila[2].length()==2) {//Nema komentara tako da ne ucitavamo profil
	            	
	            	continue;
	            }else {
	            
	            boolean potvrdjen = Boolean.parseBoolean(podaciProfila[0]);
	            
	            
	            Vestina v1=new Vestina();
	            ArrayList<Vestina> vestine = v1.pretragaVestina(podaciProfila[1]);//Dobavka vestina
	            Komentar k1=new Komentar();

	            ArrayList<Komentar> komentari = k1.pretragaKomentara(podaciProfila[2]);//Dobavka komentara
	            Regrut regrut = new Regrut().pretragaRegruta(podaciProfila[3]);//Dobavka regruta

	            // Kreiranje profila preko podataka
	            Profil profil = new Profil(potvrdjen, vestine, komentari, regrut);

	            // dodavanje profila u listu
	            profili.add(profil);
	        }}

	       
	    } catch (IOException e) {
	       e.getMessage();//U slucaju greske
	    }

	    return profili;
	}

	public  void brisanjeKomentara() throws FileNotFoundException, IOException {
	    Scanner sc = new Scanner(System.in); //Otvaranje skenera
	    
	    System.out.println("----Brisanje komentara----");
        System.out.println("1.Brisanje komentara sa profila");
        System.out.println("2.Brisanje komentara sa ponude");
        Integer izbor3 = sc.nextInt(); //Unos korisnika

        if (izbor3 == 1) {//Izabir opcije za profile
            System.out.println("----Brisanje komentara sa profila----");
            ArrayList<Profil> profili =  ucitavanjeSvihProfila();
            if(profili.size()<1) {
            	System.out.println("Nema profila sa komentarima!");
            	return;
            }
            for (int i = 0; i < profili.size(); i++) {
            	
                System.out.println((i + 1) + ". " + profili.get(i).toIspis()); //Ipis svih profila sa komentarima
            }
            int profilIndeks = sc.nextInt();

            if (profilIndeks >= 1 && profilIndeks <= profili.size()) {
                Profil izabraniProfil = profili.get(profilIndeks - 1);
                System.out.println("Unesite idenks komentara:");// Izabir komentara
                Integer indeks = sc.nextInt();
                ArrayList<Komentar>svi= new Komentar().sviKomentari();
                ArrayList<Komentar>k2 = izabraniProfil.getKomentari();
                k2.remove(indeks-1);
                izabraniProfil.setKomentari(k2);
                svi.remove(indeks-1);
                new Komentar().upisPosleBrisanja(svi);//Upis promena
                System.out.println("Komentar je uspešno brisan sa profila.");
                
                izabraniProfil.upisPosle(izabraniProfil.isPotvrdjen(), izabraniProfil.getVestine(), izabraniProfil.getKomentari(),izabraniProfil.getRegrut()); //Upis promena
            } else {
                System.out.println("Nepoznat izbor. Molimo unesite validnu opciju.");
            }
        } else if (izbor3 == 2) {//Izabir opcije za ponude
            System.out.println("----Brisanje komentara sa ponude----");
            ArrayList<Ponuda> ponude = ucitavanjePonuda();//Ucitavanje ponuda sa komentarima
            if(ponude.size()<1) {
            	System.out.println("Nema ponuda sa komentarima!");
            	return;
            }
            System.out.println("Odaberite ponudu sa koje zelite obrisati komentar:");
            for (int i = 0; i < ponude.size(); i++) {
                System.out.println((i + 1) + ". " + ponude.get(i).toIspis()); //Ispis ponuda sa komentarima
            }
            int ponudaIndeks = sc.nextInt(); // Izabir ponude
            
            
            if (ponudaIndeks >= 1 && ponudaIndeks <= ponude.size()) {
                Ponuda izabranaPonuda = ponude.get(ponudaIndeks - 1);
                System.out.println("Broj komentara:"); //Izabir komentara
                Integer indeks = sc.nextInt();
                
                ArrayList<Komentar>komentari=izabranaPonuda.getKomentari();
                while(indeks>komentari.size()) {
                	System.out.println("Promasili ste indeks komentara, pokusajte ponovo:");
                	indeks = sc.nextInt();
                }
                komentari.remove(indeks-1);
                ArrayList<Komentar>svi= new Komentar().sviKomentari();
                svi.remove(indeks-1);
                izabranaPonuda.setKomentari(komentari);
                new Komentar().upisPosleBrisanja(svi);//Upis promena
                izabranaPonuda.upisPosle(izabranaPonuda.getRegruter(), izabranaPonuda.getNaziv(), izabranaPonuda.getOpis(), izabranaPonuda.getTip(), izabranaPonuda.getPotrebneVestine(), komentari); //Upis promena
                System.out.println("Komentar je uspešno brisan sa ponude.");
                
                
            } else {
                System.out.println("Nepoznat izbor. Molimo unesite validnu opciju.");
            }
        } else {
            System.out.println("Nepoznat izbor. Molimo unesite validnu opciju.");
            izbor3 = sc.nextInt();
        }
          }
	
	

	public  boolean proveraPrijave(String korisnickoIme, String lozinka) {
		String csvFajl =  "data/korisnici.csv"; //Ime datoteke
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) {  //Otvaranje sa try kako ne bi zatvarali citac licno
            String red;
            
            while ((red = citac.readLine()) != null) {
                String[] polja = red.split(",");
                String postojeceKorisnickoIme = polja[0];//Korsisnicka imena se nalaze na ovoj lokaciji u fajlu
                String postojecaLozinka = polja[1];//Lozinke se nalaze na ovoj lokaciji u fajlu

                if (postojeceKorisnickoIme.equals(korisnickoIme) && postojecaLozinka.equals(lozinka)) {
                    return true; // Poklapanje korisnickog imena i lozinke
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Korisnicko ime i lozinka se ne poklapaju
    }
	public ArrayList<String> proveraTipa(String korisnickoIme) {
		String csvFajl = "data/korisnici.csv";
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) // Otvaranje csv fajla 
        {
            String red;
            while ((red = citac.readLine()) != null) // Citanje do kraja postojenja teksta 
            	{
                String[] polja = red.split(",");
                String postojeceKorisnickoIme = polja[0];
                String tipKorisnika=polja[3];
                String lozinkaKorisnika = polja[1];
                String emailKorisnika = polja[2];
                ArrayList<String> podaciKorisnika = new ArrayList<String>();
                if (postojeceKorisnickoIme.equals(korisnickoIme)) {
                	podaciKorisnika.add(postojeceKorisnickoIme);
                	podaciKorisnika.add(lozinkaKorisnika);
                	podaciKorisnika.add(emailKorisnika);
                	podaciKorisnika.add(tipKorisnika);
                    return podaciKorisnika;
                    // Vracanje svih podataka korisnika
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public  boolean postijiKorisnickoIme(String korisnickoIme) {
		String csvFajl =  "data/korisnici.csv"; //Otvaranje fajl
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) { //Otvaranje sa try
            String red;
            while ((red = citac.readLine()) != null) {
                String[] polja = red.split(","); // Podela podataka
                String postojeceKorisnickoIme = polja[0];
                

                if (postojeceKorisnickoIme.equals(korisnickoIme)) {
                    return true; // Pronadjeno korisnicko ime
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Korisnicko ime nije pronadjeno
    }
	public  boolean postijiEmail(String email) {
		String csvFajl =  "data/korisnici.csv"; //Ime datoteke
        try (BufferedReader citac = new BufferedReader(new FileReader(csvFajl))) { //Otvaranje sa try
        	
            String red;
            while ((red = citac.readLine()) != null) {
                String[] polja = red.split(",");// Podela podataka
                String postojeciEmail = polja[2];
                

                if (postojeciEmail.equals(email)) {
                    return true; // Pronadjen email
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Email nije pronadjen
	}
}
