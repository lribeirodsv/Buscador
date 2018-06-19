
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Tokenizer {
    
    public static void tokenize() throws Exception {
        
        String inputFile = "C:/Users/a11011BCC021/Desktop/ORI/corpus.csv";
	String outputFile = "C:/Users/a11011BCC021/Desktop/ORI/tokens.txt";
        String outputFile2 = "C:/Users/a11011BCC021/Desktop/ORI/indices.txt";
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            
            FileWriter arq = new FileWriter(outputFile);
            FileWriter arq2 = new FileWriter(outputFile2);
            PrintWriter gravarArq = new PrintWriter(arq);
            PrintWriter gravarArq2 = new PrintWriter(arq2);
            
            String s;
            String token;
            StringTokenizer st;
            s = reader.readLine( );
            s = reader.readLine( );
            int counter = 1;
            
            while(s != null){
                st = new StringTokenizer(s, " ,.'!?-;:|/*�#)%^+$(\"");
                while(st.hasMoreTokens()){
                    token = st.nextToken();
                    if(token.matches("[-+]?\\d*\\.?\\d+")){
                    System.out.print("Numero:\t\t\t\t\t" + token + " Filtrado\n");
                    }else if(token.startsWith("&")){
                        System.out.print("Emotion:\t\t\t\t\t" + token + " Filtrado\n");
                    } else {
                    gravarArq.printf(counter + " %s", token.toLowerCase());
                    gravarArq2.printf("%s "+ counter, token.toLowerCase());
                    gravarArq2.printf("%n");
                    gravarArq.printf("%n");
                    
                    System.out.print(token);
                    System.out.print("\t\t\t\t\t");
		    System.out.println("Numero de tokens restantes na linha " + counter + ": " + st.countTokens());
                    }
                }
                System.out.println("-----------------------------");
                s = reader.readLine( );
                counter++;
            }
        gravarArq.printf("%n"); 
        gravarArq.printf("");    
        arq.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
    }
}

