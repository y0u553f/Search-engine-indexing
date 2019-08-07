package net.codejava.swing;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {

  String titre;
  String rawText;
  String textSansmotVide;
  String porterText;
  List<Integer>textOcc=  new ArrayList<Integer>(); 
  List<String>tokenText= new ArrayList<String>();
  List<Double>poids = new ArrayList <Double>();
  public  final static String fichierStopW="./src/stopwords.txt";
  
  public static String supprimerMotVid(String text,String liste){
	    String textCorrige="";
	    Pattern patternStop = Pattern.compile("\\b(?:i|"+liste+")\\b\\s*", Pattern.CASE_INSENSITIVE);
	    Matcher matchStop = patternStop.matcher(text);
	    textCorrige = matchStop.replaceAll(" ");
	    return textCorrige;
	    }
  
  public static String lireFichierStopWord(String chemin){
      String liste=""; // contient l'ensemble des mots vides
      String chaineStopW="";
       // lecture du fichier stopwords//
                try{  InputStream ipps=new FileInputStream(chemin);
			InputStreamReader ippsr=new InputStreamReader(ipps);
			BufferedReader br=new BufferedReader(ippsr);
                      String ligne;
			while ((ligne=br.readLine())!=null){
			chaineStopW+=ligne+"|";
			} //fin while
			br.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
                
      liste=chaineStopW.substring(0, chaineStopW.length()-2); // pour enlever le dernier | ajouté
      return liste;
  }
  
  public  Document(String rawText) {
	  
	  textSansmotVide=supprimerMotVid(rawText,lireFichierStopWord(fichierStopW));
	  this.rawText=rawText;

     Porter algo= new Porter();
	  
	  StringTokenizer st = new StringTokenizer(textSansmotVide);
	  
      Boolean first=true;
      
      String mot="";
             while (st.hasMoreTokens())
                    {
          	         mot=algo.stemWord(st.nextToken());
  	                 porterText+= mot+" ";
  	                 if(first) {
  	                	 tokenText.add(mot);
  	                	 textOcc.add(1);
  	                	 first=false;
  	                 }else {
  	                	 if (tokenText.indexOf(mot)==-1) {
  	                		 tokenText.add(mot);
      	                	 textOcc.add(1);
  	                	 }else {
  	                		 textOcc.set(tokenText.indexOf(mot),textOcc.get(tokenText.indexOf(mot))+1);
  	                	 }
  	                 }
  	                	 
                    }
	  
	 
	
  }
  
  
  
}
