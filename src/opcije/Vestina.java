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
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Interfejsi.Validirajuce;



public class Vestina implements Validirajuce{
	private String naziv;
	private LocalDate datumPotvrde;
	public enum Kategorija{
		INTELEKTUALNA,
		SOCIJALNA,
		KOMUNIKACIONA,
		FIZICKA
	}
	private Kategorija kategorija;
	public enum Nivo{
		VISOK,
		SREDNJI,
		NIZAK
	}
	private Nivo nivoVestine;
	//Geteri i seteri
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public LocalDate getDatumPotvrde() {
		return datumPotvrde;
	}
	public void setDatumPotvrde(LocalDate datumPotvrde) {
		this.datumPotvrde = datumPotvrde;
	}
	public Kategorija getKategorija() {
		return kategorija;
	}
	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}
	public Nivo getNivoVestine() {
		return nivoVestine;
	}
	public void setNivoVestine(Nivo nivoVestine) {
		this.nivoVestine = nivoVestine;
	}
	//Konstruktor sa parametrima
	public Vestina(String naziv, LocalDate datumPotvrde, Kategorija kategorija, Nivo nivoVestine) {
		this.naziv = naziv;
		this.datumPotvrde = datumPotvrde;
		this.kategorija = kategorija;
		this.nivoVestine = nivoVestine;
	}
	//Konstruktor bez parametara
	public Vestina() {
		
	}
	
	public void upisPosle(String naziv, LocalDate datumPotvrde, Kategorija kategorija, Nivo nivoVestine) {
		String tempCsvFilePath = "data/temp.csv";//Lokacija privremenog fajla
        String csvFilePath="data/vestine.csv";//Lokacija csv fajla
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempCsvFilePath))) {//Otvaranje preko try

               String linija;
               while ((linija = br.readLine()) != null) {//Citanje do kraja fajla
                   if (!linija.isEmpty()) {
                       String[] podaciVestine = linija.split(",");//Podela podataka
                       
                       
                       if (podaciVestine.length >= 4 && podaciVestine[0].equalsIgnoreCase(naziv) && podaciVestine[2].equalsIgnoreCase(kategorija.toString())) {//Ako se pronadje vestina sa istim nazivom i kategorijom
                    	   //Menjaju se njeni podaci
                    	   String novaLinija = naziv + "," + datumPotvrde + "," + kategorija + "," + nivoVestine;
                           bw.write(novaLinija);
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

        Path csvFilePathObj = Path.of(csvFilePath);//Putanja do csv fajla
        Path tempCsvFilePathObj = Path.of(tempCsvFilePath);//Putanja do privremenog fajla
        try {
            Files.move(tempCsvFilePathObj, csvFilePathObj, StandardCopyOption.REPLACE_EXISTING);//Zamena fajlova
           
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
            System.out.println("Neuspesna promena fajlova.");
        }
	}
	public void upis(String naziv, LocalDate datumPotvrde, Kategorija kategorija, Nivo nivoVestine) {
		String csvFajl="data/vestine.csv";//Lokacija datoteke
		try (FileWriter pisac = new FileWriter(csvFajl, true)) {//Pisanje preko try, true znaci ako postoji tekst da se ne prepise preko njega
			if(postojiVestina(naziv)) {//Ako vestina sa istim nazivom postoji, nece se upisati
				return;
			}
		   
            String profil = naziv + "," + datumPotvrde + "," + kategorija.toString() + ","+ nivoVestine.toString() + "," + "\n";
            pisac.append(profil);//Upis vestine
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
	}
	public Boolean postojiVestina(String naziv) throws FileNotFoundException, IOException {
		 String csvFajl = "data/vestine.csv";//Ime datoteke
	        String linija;
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {
	            while ((linija = br.readLine()) != null) {
	                String[] podaciVestina = linija.split(",");//Podela podataka
	                if(podaciVestina[0].equalsIgnoreCase(naziv)) {
	                	return true;//Postoji vestina sa tim nazivom
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();//Hvatanje greske
	        }
	        
	        return false;//Nepostoji vestina sa tim nazivom
	    }
	public  ArrayList<Vestina> dodavanjeVestina(){
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
            }}
		return listaVestina;
        }
	public  ArrayList<Vestina> pretragaVestina(String naziv) {
        String csvFajl = "data/vestine.csv";//Lokacija datoteke
        
        String bezZgrada = naziv.substring(1, naziv.length() - 1);//Otklanjanje zagrada []

        String [] nazivi=bezZgrada.split(",");//Podela po zarezu
        
        String linija;
        ArrayList<Vestina>vestine=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFajl))) {//Otvaranje sa try
            while ((linija = br.readLine()) != null) {
                String[] podaciVestina = linija.split(",");
                for(int i=0;i<nazivi.length;i++) {
                if(podaciVestina[0].equalsIgnoreCase(nazivi[i].trim())) {//Provera da li se poklapaju nazivi
                	LocalDate datum = null;
                    if (!podaciVestina[1].equalsIgnoreCase("null")) {//Ako datum nije null parsiraj ga
                        datum = LocalDate.parse(podaciVestina[1]);
                    }
                    //Ako se poklapaju dodaj vestinu
                    Vestina v1 = new Vestina(podaciVestina[0], datum, Kategorija.valueOf(podaciVestina[2]), Nivo.valueOf(podaciVestina[3]));
                    vestine.add(v1);//Dodavanje vestine u listu
                
                }}
            }
        } catch (IOException e) {
            e.printStackTrace();//Hvatanje greske
        }
        
        return vestine;//Vracanje vestina
    }
	@Override
	public String toString() {
		String datumPotvrdeStr = datumPotvrde != null ? datumPotvrde.toString() : "";//Ako je datumPotvrde nije null pretvori ga u string ako jeste nemoj
        return naziv + ";" + datumPotvrdeStr + ";" + kategorija + ";" + nivoVestine;
    }

	@Override
	public void validiraj() {
		LocalDate datum = LocalDate.now();
		this.setDatumPotvrde(datum);//Promena vrednosti za datumPotvrde
	}
	

}
