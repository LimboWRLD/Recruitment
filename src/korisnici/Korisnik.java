package korisnici;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import opcije.Komentar;
import opcije.Ponuda;
import opcije.Profil;

public abstract class Korisnik {
	protected String korisnickoIme;
	protected String lozinka;
	protected String email;
	// Getteri i seteri
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}
	public Korisnik(String korisnickoIme, String lozinka, String email) { //Konstruktor za korisnika sa atributima
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
	}
	public Korisnik() {
		//Konstruktor za korisnika bez atributa
	}
	public void dodavanjeKomentara() throws FileNotFoundException {
	    Scanner sc = new Scanner(System.in);

	    
	    System.out.println("----Dodavanje komentara----");
	    System.out.println("1.Dodavanje komentara na profil");
	    System.out.println("2.Dodavanje komentara na ponudu");
	    try {
	    Integer izbor3 = sc.nextInt();// Izbor korisnika
	    sc.nextLine();
	    while(izbor3!=1 && izbor3!=2) {
	    	System.out.println("Unestite validnu opciju");
	    	 izbor3 = sc.nextInt();// Izbor korisnika
	 	    sc.nextLine();
	    }
	    if (izbor3 == 1) {
	        System.out.println("----Dodavanje komentara na profil----");
	        ArrayList<Profil> profili = (ArrayList<Profil>) new Profil().ucitavanjeSvihProfila(); //Ucitavanje profila
	        System.out.println("Odaberite profil na koji zelite dodati komentar:");
	        for (int i = 0; i < profili.size(); i++) {
	            System.out.println((i + 1) + ". " + profili.get(i).toIspis()); //Ispis profila
	        }
	        int profileIndex = sc.nextInt();  //Izobor korisnika
	        sc.nextLine();

	        if (profileIndex >= 1 && profileIndex <= profili.size()) {
	            Profil izbraniProfil = profili.get(profileIndex - 1); //Izabir profila
	            System.out.println("Unesite detalje komentara:");
	            String sadrzaj = sc.nextLine(); //Upis sadrzaja
	            LocalDateTime datumKreacije = LocalDateTime.now();
	            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	            LocalDateTime formatiraniDatum = LocalDateTime.parse(datumKreacije.format(formater), formater);

	            Komentar comment = new Komentar(sadrzaj, formatiraniDatum, null, this);//Kreiranje komentara
	            izbraniProfil.dodajKomentar(comment);//Dodavanje komentara u listu
	            comment.upis(sadrzaj, formatiraniDatum, null, this);//Upis komentara
	            System.out.println("Komentar je uspešno dodat na profil.");//Potvrda korisniku
	            izbraniProfil.upisPosle(izbraniProfil.isPotvrdjen(), izbraniProfil.getVestine(), izbraniProfil.getKomentari(), izbraniProfil.getRegrut()); //Upis posle promena
	        } else {
	            System.out.println("Nepoznat izbor. Molimo unesite validnu opciju.");
	        }
	    } else if (izbor3 == 2) {
	        System.out.println("----Dodavanje komentara na ponudu----");
	        ArrayList<Ponuda> ponude = new Ponuda().ucitavanjePonuda();// Ucitavanje ponuda
	        System.out.println("Odaberite ponudu na koju zelite dodati komentar:");
	        for (int i = 0; i < ponude.size(); i++) {
	            System.out.println((i + 1) + ". " + ponude.get(i).toIspis());//Ispis ponuda
	        }
	        int offerIndex = sc.nextInt();//Izbor korisnika
	        sc.nextLine();

	        if (offerIndex >= 1 && offerIndex <= ponude.size()) {
	            Ponuda izabranaPonuda = ponude.get(offerIndex - 1); //Izabrana ponuda
	            System.out.println("Unesite sadrzaj komentara:");
	            String sadrzaj = sc.nextLine();//Upis sadrzaja
	            LocalDateTime datumKreacije = LocalDateTime.now();
	            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	            LocalDateTime formatiraniDatum = LocalDateTime.parse(datumKreacije.format(formater), formater);
	            Komentar comment = new Komentar(sadrzaj, formatiraniDatum, null, this);//Kreiranje komentara
	            comment.upis(sadrzaj, formatiraniDatum, null, this);//Upis u komentare
	            izabranaPonuda.dodajKomentar(comment); //Dodavanje komentara
	            izabranaPonuda.upisPosle(izabranaPonuda.getRegruter(), izabranaPonuda.getNaziv(), izabranaPonuda.getOpis(), izabranaPonuda.getTip(), izabranaPonuda.getPotrebneVestine(), izabranaPonuda.getKomentari()); //Upis u ponude
	            System.out.println("Komentar je uspešno dodat na ponudu.");
	           
	        } else {
	            System.out.println("Nepoznat izbor. Molimo unesite validnu opciju.");
	        }
	    }}catch(InputMismatchException e) {
        	System.out.println("Unesite validan tip opcije!");//Ukoliko korisnik unese drugaciji tip nego ocekivano
            sc.nextLine(); 
        }
	   
	    
	    
	}

	@Override
	public String toString() {
		return "Korisnik [korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", email=" + email + "]";
	}
	

}
