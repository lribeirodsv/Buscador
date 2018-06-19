
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Permutermos {
    
    public static BTree<String, String> st = new BTree<String, String>();  
    
    
    public static void permutar(char[] termo,String term) throws IOException{
        
        //String outputFile = "C:/Users/a11011BCC021/Desktop/ORI/permutermos.txt";
        String outputFile = "C:/Users/Lucas/Documents/permutermos.txt";
               
        try{
            FileWriter arq = new FileWriter(outputFile,true);
            PrintWriter gravarArq = new PrintWriter(arq);
            
            
            char[] buf = termo;
            char[] obuf = new char[termo.length + 1];
            for (int i = 0; i < obuf.length - 1; i++) {
                obuf[i] = buf[i];
            }
            obuf[obuf.length-1] = '*';
            for (int i = 0; i < obuf.length; i++) {
                char[] permuterm = getPermutermAt(obuf, i);
                System.out.println(permuterm);
                gravarArq.flush();
                gravarArq.println(permuterm);
                
                StringTokenizer fst = new StringTokenizer(String.valueOf(permuterm),"*");
                while(fst.hasMoreTokens()){
                    st.put(fst.nextToken(), term);
                    //System.out.println(fst.nextToken()+" termo: "+term);
                }

                //adiciona a arvore
                st.put(String.valueOf(permuterm), term);
            }
       
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }  
        
        
      
        //System.out.println("");
        
    }
    
    public static char[] getPermutermAt(char[] obuf, int pos) {
        char[] pbuf = new char[obuf.length];
        int curr = pos;
        for (int i = 0; i < pbuf.length; i++) {
            pbuf[i] = obuf[curr];
            curr++;
            if (curr == obuf.length) {
                curr = 0;
            }
        }
    return pbuf;
    }
    
    public static void testeArvore(){
    
    String teste1 = "igo*am";
    String teste2 = "dio*au";
    String teste3 = "dicao*au";
    String teste4 = "stronauta*a";
    String teste5 = "itona*aze";
    String teste6 = "a*bund";
    String teste7 = "migo*a";
    String teste8 = "a";
    String teste9 = "migo";
    String teste10 = "bund";
    
        
    //st.put(teste1,teste2);
    System.out.println("Teste("+teste1+"):"+st.get(teste1));
    System.out.println("Teste("+teste2+"):"+st.get(teste2));
    System.out.println("Teste("+teste3+"):"+st.get(teste3));
    System.out.println("Teste("+teste4+"):"+st.get(teste4));
    System.out.println("Teste("+teste5+"):"+st.get(teste5));
    System.out.println("Teste("+teste6+"):"+st.get(teste6));
    System.out.println("Teste("+teste7+"):"+st.get(teste7));
    System.out.println("Teste("+teste8+"):"+st.get(teste8));
    System.out.println("Teste("+teste9+"):"+st.get(teste9));
    System.out.println("Teste("+teste10+"):"+st.get(teste10));
    }
    
    public static String consultaDicionario(String a){
        return st.get(a);
    }
    
}
