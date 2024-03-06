package korisnici;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import opcije.Komentar;
import opcije.Ponuda;
import opcije.Profil;
import opcije.Vestina;

public class Validator extends Korisnik {
	private int identifikator;
	private String ime;
	private String prezime;
	public enum Uza_Strucna_Oblast{
		tehnickoTehnoloskoPolje,
		drustvenoHumanistickoPolje
	}
	private Uza_Strucna_Oblast uzaStrucnaOblast;
	//Getteri i seteri
	public int getIdentifikator() {
		return identifikator;
	}
	public void setIdentifikator(int identifikator) {
		this.identifikator = identifikator;
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
	public Uza_Strucna_Oblast getUzaStrucnaOblast() {
		return uzaStrucnaOblast;
	}
	public void setUzaStrucnaOblast(Uza_Strucna_Oblast uzaStrucnaOblast) {
		this.uzaStrucnaOblast = uzaStrucnaOblast;
	}
	public Validator(String korisnickoIme, String lozinka, String email, int identifikator, String ime, String prezime,
			Uza_Strucna_Oblast uzaStrucnaOblast) {
		super(korisnickoIme, lozinka, email);
		this.identifikator = identifikator;
		this.ime = ime;
		this.prezime = prezime;
		this.uzaStrucnaOblast = uzaStrucnaOblast;
		//Konstruktor sa parametrima
	}
	
	public Validator() {
		super();
		//Konstruktor bez parametara
	}
	
	public void upis(String korisnickoIme, String lozinka, String email,int identifikator, String ime, String prezime,
			Uza_Strucna_Oblast uzaStrucnaOblast) {
		String csvFajl="data/validatori.csv"; //Ime fajla za otvaranje
		try (FileWriter pisac = new FileWriter(csvFajl, true)) { //Otvaranje preko try
            String validator = korisnickoIme + "," + lozinka + "," + email + "," +identifikator+ "," + ime + "," + prezime + "," + uzaStrucnaOblast.toString() +  "\n";
            pisac.append(validator);//Upis u fajl
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public Validator pretragaValidatora(String str)throws FileNotFoundException, IOException  {
		 String csvFajl = "data/validatori.csv"; //Ime fajla za otvaranje
	        String linija;
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
	            while ((linija = br.readLine()) != null) {
	                String[] podaciValidatora = linija.split(",");
	                if(podaciValidatora[0].equalsIgnoreCase(str)) {//Poredjenje podataka
	                	//Kreiranje validatora ako je on pronadjen
	                	Validator v1=new Validator(podaciValidatora[0], podaciValidatora[1], podaciValidatora[2], Integer.parseInt(podaciValidatora[3]), podaciValidatora[4], podaciValidatora[5], Uza_Strucna_Oblast.valueOf(podaciValidatora[6]));
	                	return v1;
	                }}
	            }
			return new Validator();
	        } 
	 
	@Override
	public String toString() {
		return "Validator [identifikator=" + identifikator + ", ime=" + ime + ", prezime=" + prezime
				+ ", uzaStrucnaOblast=" + uzaStrucnaOblast + "]";
	}
	public ArrayList<Profil> pretragaProfila() throws FileNotFoundException, IOException{
		 String csvFajl = "data/profili.csv";//Ime fajla za otvaranje
		    ArrayList<Profil> profili = new ArrayList<>();

		    try (BufferedReader reader = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje preko try
		        
		        String line;
		        while ((line = reader.readLine()) != null) {
		            String[] profileData = line.split(";");//Podela podataka

		            //Dodavanje svih profila koji nisu potvrdjeni
		            boolean potvrdjen = Boolean.parseBoolean(profileData[0]);
		            if(!(potvrdjen)) {
		            	Vestina v1=new Vestina();
			            ArrayList<Vestina> vestine = v1.pretragaVestina(profileData[1]);
			            Komentar k1=new Komentar();
			            
			            ArrayList<Komentar> komentari = k1.pretragaKomentara(profileData[2]); // Assuming Komentar constructor with default values
			            Regrut regrut = new Regrut().pretragaRegruta(profileData[3]);

			            //Pravljenje profila
			            Profil profil = new Profil(potvrdjen, vestine, komentari, regrut);

			           //Dodavanje profila u listu
			            profili.add(profil);
		            }
		            
	}}return profili;//Vracanje liste
	}
	public void validacijaKomentara() throws FileNotFoundException, IOException {
		ArrayList<Komentar>komentari= new ArrayList<>();//Ucitavanje svih komentara
		ArrayList<Profil>sviProfili= new Administrator().ucitavanjeSvihProfila();
		ArrayList<Ponuda>svePonude= new Administrator().ucitavanjePonuda();
		Scanner sc = new Scanner(System.in);//Otvaranje skenera
		System.out.println("Da li hocete da validirate komentar na profilu ili ponudi?");
		String izbor = sc.nextLine();
		while(!izbor.equalsIgnoreCase("profil")&& !izbor.equalsIgnoreCase("ponuda")) {
			System.out.println("Unesite validnu opciju");
			izbor=sc.nextLine();
		}
		if(izbor.toLowerCase().equals("profil")) {
			for(Profil p1:sviProfili) {
				for(Komentar k1:p1.getKomentari()) {
					
						komentari.add(k1);
					
				}
			}
			
		}else if(izbor.toLowerCase().equals("ponuda")) {
			for(Ponuda p1:svePonude) {
				for(Komentar k1:p1.getKomentari()) {
					
						komentari.add(k1);
					
				}
			}
		}
		if(komentari.size()==0) {
			System.out.println("Nema komentara koji nisu potvrdjeni.");
			return;
		}
		System.out.println("Odaberite komentar koji ko zelite da validirate:");
        for (int i = 0; i < komentari.size(); i++) {
            System.out.println((i + 1) + ". " + komentari.get(i));
        }
        
		int profileIndex = sc.nextInt(); //Izabir korisnika

        if (profileIndex >= 1 && profileIndex <= komentari.size()) {
            Komentar izabraniKomentar = komentari.get(profileIndex - 1);//Izabrani komentar
            
            izabraniKomentar.validiraj();//Validacija komentara
            izabraniKomentar.upisPosle(izabraniKomentar.getSadrzaj(), izabraniKomentar.getDatumKreacije(), izabraniKomentar.getDatumPotvrde(), izabraniKomentar.getKreator());
            //Upis posle izmena
            
            System.out.println("Komentar uspesno validiran!");
}
       
}
	public void pretragaVestina() throws FileNotFoundException, IOException{
		
		Scanner sc = new Scanner(System.in);//Otvaranje skenera
		try {
		System.out.println("Unesite korisnicko ime:");
		String ime = sc.nextLine();//Unos korisnickog imena
		while(!new Regrut().postojanjeRegruta(ime)) {
			System.out.println("Ne postoji regrut sa tim korisnicnim imenom");
			ime = sc.nextLine();//Dok ne unese vazece 
        }
		if(!new Regrut().pretragaRegruta(ime).postojiProfil()) {
			System.out.println("Regrut nema profil!");//Ako regrut nema profil program se prekida
			return;
		}else {
			
			ArrayList<Vestina>vestine = new Profil().podaciProfila(ime).getVestine();//Ucitavanje svih vestina
			ArrayList<Vestina>nepotvrdjeneVestine = new ArrayList<>();
			for(Vestina v1:vestine) {
				if(v1.getDatumPotvrde()==null) {
					nepotvrdjeneVestine.add(v1);//Dodavanje vestina koje nisu potvrdjene
				}
			}
		if(nepotvrdjeneVestine.size()==0) {
			System.out.println("Nema nepotrvrdjenih vestina");
			return;
		}
			System.out.println("Izaberite vestinu koju hocete da validirate");
			for (int i = 0; i < nepotvrdjeneVestine.size(); i++) {
	             System.out.println((i + 1) + ". " + nepotvrdjeneVestine.get(i));
	         }
			int izbor=sc.nextInt();//Izbor korsinka
			while(izbor>nepotvrdjeneVestine.size()) {
				System.out.println("Unesite validan unos vestine");
			}
			Vestina v1 =nepotvrdjeneVestine.get(izbor-1);
			v1.validiraj();
			System.out.println("Vestina uspesno validirana!");
			//Upis posle izmena
			
			
			v1.upisPosle(v1.getNaziv(), v1.getDatumPotvrde(), v1.getKategorija(), v1.getNivoVestine());
		}}catch(InputMismatchException e) {
        	System.out.println("Unesite validnu opciju!");//Hvatanje pogresnog tipa vrednosti
            sc.nextLine(); 
        }
		
		
	}
	public ArrayList<Komentar> pretragaKomentara() throws FileNotFoundException, IOException{
        String csvFajl = "data/komentari.csv"; //Ime datoteke za otvaranje
        String linija;
        ArrayList<Komentar>komentari=new ArrayList<>();//Kreiranje liste za komentare
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
            while ((linija = br.readLine()) != null) {
                String[] podaciKomentara = linija.split(",");
                
                	if(podaciKomentara[2].equalsIgnoreCase("null")) {//Ako nema datum potvrde dodaj komentar
                		podaciKomentara[2]=null;
                		
                		
                		//Provera ko je napravio komentar
                		if(new Regrut().postojanjeRegruta(podaciKomentara[3])) {
                			Komentar v1=new Komentar(podaciKomentara[0], LocalDateTime.parse(podaciKomentara[1]), null, new Regrut().pretragaRegruta(podaciKomentara[3]));
                        	komentari.add(v1);
                		}else if(new Regruter().postojanjeRegrutera(podaciKomentara[3])) {
                			Komentar v1=new Komentar(podaciKomentara[0], LocalDateTime.parse(podaciKomentara[1]), null, new Regruter().pretragaRegrutera(podaciKomentara[3]));
                        	komentari.add(v1);
                		}else {
                			Komentar v1=new Komentar(podaciKomentara[0], LocalDateTime.parse(podaciKomentara[1]), null, new Validator().pretragaValidatora(podaciKomentara[3]));
                        	komentari.add(v1);
                		
                		}}}}
		return komentari;//Vracanje svih komentara bez datuma potvrde
            }
         
  
	public void validacijaProfila() throws FileNotFoundException, IOException {
		 ArrayList<Profil> profiles = (ArrayList<Profil>) new Validator().pretragaProfila();//Ucitavanje svih profila bez potvrde
		 Scanner sc = new Scanner(System.in); 
         System.out.println("Odaberite profil koji zelite da validirate:");
         for (int i = 0; i < profiles.size(); i++) {
             System.out.println((i + 1) + ". " + profiles.get(i).toIspis());
         }
         
		int profileIndex = sc.nextInt();//Izbor korisnika

         if (profileIndex >= 1 && profileIndex <= profiles.size()) {
             Profil izabraniProfil = profiles.get(profileIndex - 1);
             izabraniProfil.validiraj();//Validiranje izabranog profila
             //Upis posle promena
             izabraniProfil.upisPosle(izabraniProfil.isPotvrdjen(), izabraniProfil.getVestine(), izabraniProfil.getKomentari(), izabraniProfil.getRegrut());
             Regrut r1= izabraniProfil.getRegrut();
             r1.upisPosle(r1.getKorisnickoIme(), r1.getLozinka(), r1.getEmail(), r1.getProfil(), r1.getIme(), r1.getPrezime(), r1.getTelefon());
             System.out.println("Profil uspesno validiran!");
}}}
