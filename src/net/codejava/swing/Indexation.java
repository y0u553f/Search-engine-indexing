package net.codejava.swing;
import java.io.*;
import java.util.regex.*;

import net.codejava.swing.Document;

import java.util.*;

public class Indexation {
    
	 List<Document>corpus= new ArrayList<Document>();
     List<String>motdist=new ArrayList<String>();
	 List<Double>idf=new ArrayList<Double>();
	 public   final String directory="./src/corpus";
     public  String[] columnNames;
     public   Object[][] data;
	 public String resultatAff; 
     
	 public  void CreateDocuments(String directory) {
		 File f=new File(directory);
		 File[] allSubFiles=f.listFiles();
		 String rawtexte="";
		 for (File file : allSubFiles) {
			 try{  InputStream ipps=new FileInputStream(file);
				InputStreamReader ippsr=new InputStreamReader(ipps);
				BufferedReader br=new BufferedReader(ippsr);
	                      String ligne;
				while ((ligne=br.readLine())!=null){
				rawtexte+=ligne;
				} //fin while
				br.close();
			}
			catch (Exception e){
				System.out.println(e.toString());
			}
			 Document doc = new Document(rawtexte);
			
			 corpus.add(doc);
			 doc.titre=file.getName();
			 rawtexte="";
		 }
	 }
	 
	 public void IndexerIdf() {
		 for(int i=0;i<corpus.get(0).tokenText.size();i++) {
			 
		 
			 motdist.add(corpus.get(0).tokenText.get(i));
			 idf.add(1.00);
		 }
		 for(int i=1;i<corpus.size();i++) {
			 Document doc=corpus.get(i);
			 for(int k=0;k<doc.tokenText.size();k++) {
				 int positionOcc=motdist.indexOf(doc.tokenText.get(k));
				 if(positionOcc==-1) {
					 motdist.add(doc.tokenText.get(k));
					 idf.add(1.00);
				 }else {
					 idf.set(positionOcc, idf.get(positionOcc)+1);
				 }
			 }
		 }
		 for(int i=0;i<idf.size();i++) {
			 idf.set(i, Math.log10(corpus.size()/idf.get(i)));
		 }
	 }
	 
	 public void Indexer() {
		 columnNames=new String[corpus.size()+1];
		 data=new Object [motdist.size()][corpus.size()+1];
		 columnNames[0]="mots";
		 
		 for (int i=1; i<=corpus.size();i++) {
			 columnNames[i]=corpus.get(i-1).titre;
			 System.out.println(columnNames[i]);
		 }
		 
		 for (int i=0; i<motdist.size();i++) {
			 data[i][0]=motdist.get(i);
			 System.out.println(data[i][0]);
		 }
		 
		 for ( int i=0; i<motdist.size() ; i++ )
		 {
			 String mot=motdist.get(i);
					 for(int k=1 ; k<=corpus.size();k++) {
						 double poid=0;
						 if(corpus.get(k-1).tokenText.indexOf(mot)==-1)
							 poid=0*idf.get(i);					     
						 else
					       poid=corpus.get(k-1).textOcc.get(corpus.get(k-1).tokenText.indexOf(mot))*idf.get(i);
						 data[i][k]=poid;
						 corpus.get(k-1).poids.add(poid)	 ;
						 System.out.println(corpus.get(k-1).poids.get(i));
		 }			 
}
		 System.out.println("----");
		 for (int i=0;i<columnNames.length;i++) {
			 System.out.println(columnNames[i]);
		 }
	 }
	 
 public Integer Recherche(String requette) {
		
	     List<Double>requetteList=new ArrayList<Double>();
		 Double[][]simDoc= new Double [corpus.size()][corpus.size()];
		 Porter algo = new Porter();
		 		 for (int i=0 ; i<motdist.size() ; i++) {
			     requetteList.add(0.00);
		 }	 		 
		 StringTokenizer st = new StringTokenizer(requette);
		 String mot="";
		 while(st.hasMoreTokens()) {
			 mot=algo.stemWord(st.nextToken());
			 int position=motdist.indexOf(mot);
			 if(position !=-1)
				 requetteList.set(position, 0.5);
			 }
		 Double sommPoidReq=0.0;
		 for(int i=0 ; i<motdist.size();i++) {
			 sommPoidReq+=requetteList.get(i)*requetteList.get(i);
		 }
         if(sommPoidReq==0.0) {
        	 
         }
         int nbrDocPerti=0;
		 for (int i=0 ; i<corpus.size(); i++) {
			 Double resultat=0.0, sommePoidDoc=0.0;
			
			 for(int k=0;k<motdist.size();k++) {
				resultat+= corpus.get(i).poids.get(k)*requetteList.get(k);
				sommePoidDoc+= corpus.get(i).poids.get(k)* corpus.get(i).poids.get(k);
			 }
			 simDoc[i][0]=resultat/Math.sqrt(sommePoidDoc*sommPoidReq);
			 if(simDoc[i][0]>0)
				 nbrDocPerti++;
			 simDoc[i][1]=(double) i;
		 }
		 if(nbrDocPerti>0) {
			 Tri(simDoc);
		 }
		 return nbrDocPerti;
		 }
 
         public void Tri(Double[][]tab) {
        	 
        	 int j;
             boolean flag = true;   
             Double temp=0.0;  
             Double position;
             String affichage="<html>";
             while ( flag )
             {
                    flag= false;  
                    for( j=0;  j < tab.length -1;  j++ )
                    {
                           if ( tab[ j ][0] < tab[j+1][0] )  { 
                                   temp = tab[ j ][0]; 
                                   position = (tab[j][1]);
                                   tab[ j ][0] = tab[j+1][0];
                                   tab[j][1]=tab[j+1][1];
                                   tab[ j+1 ] [0]= temp;
                                   tab[j+1][1]=position;
                                  flag = true;            
                          } 
                    } 
              } 
              for (int i=0;i<tab.length;i++)
            	  if(tab[i][0]>0)
            	  affichage+=corpus.get(tab[i][1].intValue()).titre+" Sim= "+((Double)(tab[i][0]*100)).intValue()+"%"+"<br/>"+corpus.get(tab[i][1].intValue()).rawText+"<br/>";
                  affichage+="</html>";
                  resultatAff=affichage;
                  
        	      
         }
         
}		 
	 
	 

	
    