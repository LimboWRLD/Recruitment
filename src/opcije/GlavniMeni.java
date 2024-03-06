package opcije;




import java.io.IOException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import korisnici.Administrator;

import korisnici.Regrut;
import korisnici.Regruter;
import korisnici.Validator;


public class GlavniMeni {
	
	public void glavniMeni() throws IOException {
	    Scanner sc = new Scanner(System.in);//Kreiranje skenera
	    boolean stanje = false;
	    
	    while (!stanje) {//Dok stanje nije true radi ovo
	        System.out.println("----Glavni meni----");
	        System.out.println("1.Prijava");
	        System.out.println("2.Izlaz iz aplikacije");
	        try {
	        	 int izbor = sc.nextInt();//Izbor korisnika
	        	 sc.nextLine();//Sklanjanje entera u slucaju da ostane
	        
	        switch (izbor) {
	        
	            case 1:
	            	int brojac=0;//Provera pokusaja
	        	    boolean stanje2 = false;//Za stalno ucitavanje menia korisnika
	                Administrator a1 = new Administrator();
	                System.out.println("Unesite korisnicko ime ");
	                String korisnickoIme = sc.nextLine();//Unos korisnickog imena
	                while(!a1.postijiKorisnickoIme(korisnickoIme)) {//Provera postojanja imena i unos sve dok se ne unese postojece
	                	System.out.println("Ne postoji uneto korisnicko ime!");
	                	korisnickoIme=sc.nextLine();
	                }
	                System.out.println("Unesite lozinku ");
	                String lozinka = sc.nextLine();//Unos za loznku
	                while(!a1.proveraPrijave(korisnickoIme, lozinka)) {
	                	brojac++;
	                	System.out.println("Pogresili ste loznku!");
	                	System.out.println("Pokusajte ponovo:");
	                	lozinka = sc.nextLine();
	                	
	                	if(brojac==2) {
	                		System.out.println("Pogresili ste lozinku 3 puta! ");
	                		System.out.println("Unesite email kako bi dobili jos jedan pokusaj!");
	                		String email = sc.nextLine();
	                		if(!a1.postijiEmail(email)) {
	                			System.out.println("Email ne postoji!");
	                			System.out.println("Dovidjenja!");
	                			return;
	                		}
	                	}else if(brojac>=3 && !a1.proveraPrijave(korisnickoIme, lozinka)) {
	                		System.out.println("Pogresili ste 4 puta!!!");
	                		System.out.println("Dovidjenja!");
	                		return;
	                	}
	                }//Korisnik ima 3 pokusaja da ukuca korektnu lozinku, posle toga unosi email, ako ne unese dobar email ili unese pogresnu loznku jos jednom program se zavrsava
	                
	                    
	                    ArrayList<String> podaciKorisnika = a1.proveraTipa(korisnickoIme);
	                    String tipKorisnika = podaciKorisnika.get(3);//Dobavka tipa korisnika

	                    while (!stanje2) {
	                        
	                        switch (tipKorisnika) {//Ispis za odredjene korisnike
	                            case "regrut":
	                                // Regrut menu
	                            	Regrut r1 = new Regrut();
		                            ArrayList<String> podaciRegruta = r1.proveraTipa(korisnickoIme);//Dobavljanje podataka
		                            Integer telefon = Integer.parseInt(podaciRegruta.get(6));
		                            
		                            Regrut r2 = new Regrut(
		                                podaciRegruta.get(0),
		                                podaciRegruta.get(1),
		                                podaciRegruta.get(2),
		                                null,
		                                podaciRegruta.get(4),
		                                podaciRegruta.get(5),
		                                telefon
		                            );//Kreiranje regruta preko dobavljenjih podataka
	                                System.out.println("----Dobrodosao regrute---- ");
	                                System.out.println("1.Pravljenje profila ");
	                                System.out.println("2.Dodavanje komentara ");
	                                System.out.println("3.Pretraga ponuda ");
	                                System.out.println("4.Odjava ");
	                                System.out.println("5.Izlaz iz aplikacije ");
	                                try {
	                                int izbor2 = sc.nextInt();//Izbor iz menia
	                                sc.nextLine();
	                                switch (izbor2) {
	                                case 1:
	                                    if (new Profil().pretragaProfila(podaciRegruta.get(0))) {//Provera da li korisnik vec ima profil
	                                        System.out.println("Vec imate profil!");//Ispis ako ima
	                                    } else {
	                                        r2.kreirajProfil(r2);//Ako nema moze da kreira profil
	                                    }
	                                    break;
	                                case 2:
	                                    r2.dodavanjeKomentara();//Dodavanje komentara
	                                    break;
	                                case 3:
	                                    r2.ispisPonuda();//Ispis ponuda koje se poklapaju sa vestinama korisnika
	                                    break;
	                                case 4:
	                                	stanje2=true;//Odjava korisnika
	                                	break;
	                                case 5:
	                                	System.out.println("Dovidjenja!");//Izlaz iz aplikacije
	                                	stanje=true;
	                                	break;
	                                default:
	                                    System.out.println("Unesite validnu opciju!");//Ukoliko unese neki broj veci od 5 ili manji od 1
	                            }
	                                }catch(InputMismatchException e) {
	                    	        	System.out.println("Unesite validnu opciju!");//Ukoliko korisnik unese drugaciji tip nego ocekivano
	                    	            sc.nextLine(); 
	                    	        }break;
	                            case "regruter":
	                                // Regruter menu
	                            	Regruter r23 = new Regruter().pretragaRegrutera(podaciKorisnika.get(0));//Kreiranje regrutera 
	                                System.out.println("----Dobrodosao regruteru----");
	                                System.out.println("1.Kreiranje ponude ");
	                                System.out.println("2.Pretraga regruta ");
	                                System.out.println("3.Dodavanje komentara ");
	                                System.out.println("4.Odjava ");
	                                System.out.println("5.Izlaz iz aplikacije");
	                                try {
		                                int izbor3 = sc.nextInt();
		                                sc.nextLine();
		                            switch (izbor3) {
		                                case 1:
		                                	r23.kreiranjePonude();//Kreacija ponude
		                                    break;
		                                case 2:
		                                	if(!r23.isPremiumNalog()) {//Provera naloga
		                                	System.out.println("Ovu opciju imaju samo premium korisnici!");
		                                	}else {
		                                	r23.pretraga();//Odobrenje pretrage
		                                	}
		                                    break;
		                                case 3:
		                                	r23.dodavanjeKomentara();//Dodavanje komentara
		                                    break;
		                                case 4:
		                                	stanje2 = true;//Odjava
		                                	break;
		                                case 5:
		                                	System.out.println("Dovidjenja!");//Izlaz iz aplikacije
		                                	stanje =  true;
		                                default:
		                                    System.out.println("Unesite validnu opciju!");//Ukoliko unese neki broj veci od 5 ili manji od 1
		                            }}catch(InputMismatchException e) {//Ukoliko korisnik unese drugaciji tip nego ocekivano
	                    	        	System.out.println("Unesite validnu opciju!");
	                    	            sc.nextLine(); 
	                    	        }
	                                break;
	                            case "validator":
	                                // Validator menu
	                            	Validator v1 = new Validator().pretragaValidatora(podaciKorisnika.get(0));//Kreiranje validatora
	                                System.out.println("----Dobrodosao validatoru----");
	                                System.out.println("1.Dodavanje komentara ");
	                                System.out.println("2.Validacija komentara ");
	                                System.out.println("3.Validacija profila ");
	                                System.out.println("4.Validacija vestine ");
	                                System.out.println("5.Odjava ");
	                                System.out.println("6.Izlaz iz aplikacije");
	                                try {
		                                int izbor4 = sc.nextInt();
		                                sc.nextLine();
		                            switch (izbor4) {
		                                case 1:
		                                    v1.dodavanjeKomentara();//Dodavanje komentara
		                                    break;
		                                case 2:
		                                	v1.validacijaKomentara();//Validacija komentara
		                                	break;
		                                case 3:
		                                	v1.validacijaProfila();//Validacija profila
		                                	break;
		                                case 4:
		                                	v1.pretragaVestina();//Pretraga profila kako bi im se validirale vestine
		                                	break;
		                                case 5:
		                                	stanje2=true;//Odjava
		                                	break;
		                                case 6:
		                                	System.out.println("Dovidjenja");//Izlaz iz aplikacije
		                                	stanje = true;
		                                	break;
		                                default:
		                                    System.out.println("Unesite validnue opciju!");//Ukoliko unese neki broj veci od 5 ili manji od 1
		                            }
	                                }catch(InputMismatchException e) {
	                    	        	System.out.println("Unesite validnu opciju!");//Ukoliko korisnik unese drugaciji tip nego ocekivano
	                    	            sc.nextLine();  
	                    	        }break;
	                            case "administrator":
	                            	Administrator a2 = new Administrator().pretragaAdministratora(podaciKorisnika.get(0));//Kreiranje administratora
	                                
	                                    System.out.println("----Dobrodosao administratoru----");
	                                    System.out.println("1.Registracija korisnika ");
	                                    System.out.println("2.Brisanje komentara ");
	                                    System.out.println("3.Odjava");
	                                    System.out.println("4.Izlaz iz aplikacije");
	                                    try {
			                                int izbor5 = sc.nextInt();
			                                sc.nextLine();
		                                switch (izbor5) {
		                                    case 1:
		                                    	a1.RegistrujKorisnika();//Registracija novih korisnika
		                                        break;
		                                    case 2:
		                                        a2.brisanjeKomentara();//Brisanje komentara
		                                        break;
		                                    case 3:
		                                        stanje2=true;//Odjava
		                                        break;
		                                    case 4:
		                                    	System.out.println("Dovidjenja");
		                                    	stanje = true;//Izlaz iz aplikacije
		                                    	break;
		                                    default:
		                                        System.out.println("Unesite validnu opciju!");//Ukoliko unese neki broj veci od 5 ili manji od 1
		                                }}catch(InputMismatchException e) {
		                    	        	System.out.println("Unesite validnu opciju!");
		                    	            sc.nextLine();  //Ukoliko korisnik unese drugaciji tip nego ocekivano
		                    	        }

	                                    
	                                
	                                break;
	                            default:
	                                System.out.println("Nepoznat tip korisnika!");//Ukoliko tip korisnika nije poznat
	                        }

	                        if (stanje) {
	                            break;
	                        }
	                    }

	                 
	                break;

	            case 2:
	                
	                stanje = true;//Izlaz iz aplikacije
	                break;

	            default:
	                System.out.println("Unesite validnu opciju!!!");
	        }}catch(InputMismatchException e) {
	        	System.out.println("Unesite validnu opciju!");//Ukoliko unese nevalidan tip unosa
	            sc.nextLine();  
	        }
	    }

	    sc.close();
	}}
	


