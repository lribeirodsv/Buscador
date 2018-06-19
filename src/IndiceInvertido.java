import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndiceInvertido {
    
    Map<String, ArrayList<Match>> hash = new HashMap();
    Map<Integer, Double> hash_Tam = new HashMap();
    Map<Integer, Double> resultadoF = new HashMap();
    Map<Integer, String> docs = new HashMap();
                
    public static void indiceInvertido() throws IOException{
       
                String a,s,r;
                Integer x,status=1;
                
                //String inputFile = "C:/Users/a11011BCC021/Desktop/ORI/teste.txt";
                //String outputFile = "C:/Users/a11011BCC021/Desktop/ORI/indiceinvertido.txt";
                String inputFile = "C:/Users/Lucas/Documents/teste.txt";
                String outputFile = "C:/Users/Lucas/Documents/indiceinvertido.txt";
                                                        
        try{
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                FileWriter arq = new FileWriter(outputFile);
                PrintWriter gravarArq = new PrintWriter(arq);
                
                //System.out.println("LINHA 1");
                a = reader.readLine( );
                //System.out.println(a);
                StringTokenizer fst = new StringTokenizer(a," ﻿");
                String term1a = fst.nextToken();
                String docid1a = fst.nextToken();
                gravarArq.flush();
                gravarArq.printf(term1a+" "+docid1a);
                Permutermos.permutar(term1a.toCharArray(),term1a);
                
                if(status == 1){
                    s = a;
                    r = reader.readLine();
                    status = 0;    
                }else{
                    s = reader.readLine( );
                    r = reader.readLine( );
                }
                //System.out.println("LEITURA 1");
                //System.out.println(s);
                //System.out.println(r);
                
                while(s != null && r!= null){
                    StringTokenizer st = new StringTokenizer(s," ﻿");
                    StringTokenizer rt = new StringTokenizer(r," ﻿");
                    while(st.hasMoreTokens()&&rt.hasMoreTokens()){
                    String term1 = st.nextToken();
                    String docid1 = st.nextToken();
                    String term2 = rt.nextToken();
                    String docid2 = rt.nextToken();
                    //System.out.println("docid1:"+docid1+" termo1:"+term1);
                    //System.out.println("docid2:"+docid2+" termo2:"+term2);
                    if(term1.equals(term2)){
                        gravarArq.flush();
                        gravarArq.printf(","+docid2);
                        //System.out.println("igual");
                    }else{
                        gravarArq.printf("%n");
                        gravarArq.printf(term2+" "+docid2);
                        //System.out.println("diferente");
                        Permutermos.permutar(term2.toCharArray(),term2);
                    }
                    s = r;
                    r = reader.readLine();
                    //System.out.println("LEITURA 2");
                    //System.out.println(s);
                    //System.out.println(r);
                    }
                }
            arq.close();
        System.out.println("\nTabela de indices invertidos e dicionario de permutermos criados com sucesso!");
        System.out.println("\n");
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }  
    }
    
    public void put(String token, Match t) {
        if (!hash.containsKey(token)) {
            ArrayList<Match> matchs = new ArrayList();
            matchs.add(t);
            hash.put(token, matchs);
        } else {
            hash.get(token).add(t);
        }
    }
    
    public static void put1(String termo, Integer docId)throws Exception{
                
                String outputFile = "C:/Users/Lucas/Documents/indiceinvertido.txt";
                //String outputFile = "C:/Users/a11011BCC021/Desktop/ORI/indiceinvertido.txt";
                
        try{
                BufferedReader reader = new BufferedReader(new FileReader(outputFile));
                           
                FileWriter arq = new FileWriter(outputFile,true);
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.flush();
                gravarArq.printf(termo +" "+ docId);
                gravarArq.printf("%n");
                System.out.println("Termo: "+termo+" docID: "+docId+"  gravado com sucesso!");
                
                arq.close();
                
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
        System.out.println("\n");
    }
    
    public void put2(Integer chave, Double valor) {
        if (!hash_Tam.containsKey(chave)) {
            hash_Tam.put(chave, valor);
        }
    }
    
    public void put3(Integer chave, Double valor) {
        if (!resultadoF.containsKey(chave)) {
            resultadoF.put(chave, valor);
        } else {
            double novo = (resultadoF.get(chave) + valor);
            resultadoF.put(chave, novo);
        }
    }
    
    public void put4(Integer chave, String valor) {
        if (!docs.containsKey(chave)) {
            docs.put(chave, valor);
        }
    }
       
    public static List<Integer> getDocuments(String termo) throws IOException{
       
                String s;
                Integer x;
                String inputFile = " C:/Users/Lucas/Documents/indiceinvertido.txt";
                //String inputFile = "C:/Users/a11011BCC021/Desktop/ORI/indiceinvertido.txt";
               
                ArrayList<Integer> list = new ArrayList<>();
        
        try{
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                s = reader.readLine( );
                
                while(s != null){
                    StringTokenizer st = new StringTokenizer(s,", ﻿");
                    String term = st.nextToken();
                    System.out.println("Procurando... "+term);
                    if(termo.equals(term)){
                        while(st.hasMoreTokens()){
                        x = Integer.valueOf(st.nextToken());
                        System.out.println("Presente em: "+x);
                        list.add(x);
                        }
                    break;
                    } else {
                        s = reader.readLine();
                    }
                }
                System.out.print("O termo foi encontrado nos documentos: ");
                for(int i=0;i<list.size();i++){
                    System.out.print(list.get(i));
                    System.out.print(",");
                }
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }   
    System.out.println("\n");
    return list;
    }
    
    public static List<Integer> getDocuments(List<String> termo) throws IOException{
    
        int x,i = 0;
        int contr = 0;
        int contador = 0;
        int stats = 0;
        int distancia = 0;
        int menordistancia = 0;
        String s,ctr,dic;
        String sugestao = null;
        String termoerrado = null;
        String sugestao2 = null;
        String termoerrado2 = null;
        
        //String inputFile = "C:/Users/Lucas/Documents/indiceinvertido.txt";
        String inputFile = "C:/Users/Lucas/Documents/teste.txt";
        //String inputFile = "C:/Users/a11011BCC021/Desktop/ORI/teste.txt";
        String dicionario = "C:/Users/Lucas/Documents/corncob_lowercase.txt";
        //String dicionario = "C:/Users/a11011BCC021/Desktop/ORI/corncob_lowercase.txt";
        
        
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> lista = new ArrayList<>();
        ArrayList<Integer> dici = new ArrayList<>();
        ArrayList<String> dici2 = new ArrayList<>();
         ArrayList<String> dici3 = new ArrayList<>();
                
        try{
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                s = reader.readLine( );
                while(s != null){
                    StringTokenizer st = new StringTokenizer(s,", ﻿");
                    String term = st.nextToken();
                    
                    System.out.println("Procurando... "+term);
                    for(i=0;i<termo.size();i++){
                        if(termo.get(i).equals(term)){
                            stats++;                   
                            //System.out.println("TERMOS"+stats);
                            while(st.hasMoreTokens()){
                                x = Integer.valueOf(st.nextToken());
                                System.out.println("Termo "+ termo.get(i) +" presente em: "+x);
                                list.add(x);
                                contador++;
                                //System.out.println("CONT:"+contador);
                                //System.out.println("STATS:"+Main.status);
                                System.out.println(list);
                            }
                        }
                    
                    }
                s = reader.readLine();
                if(contador<5&&contador>0){
                    Main.status = 1;
                }
                //System.out.println("CONT:"+contador);
                //System.out.println("STATS:"+Main.status);
                contador = 0;
                }
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
        if(stats>1){
            int count = 1;
            int valor,valor2;
                for(int a=0;a<list.size();a++){
                    valor = list.get(a);
                    for(int b=a+1;b<list.size();b++){
                        valor2 = list.get(b);
                        //System.out.println(valor+"||"+valor2);
                        if(valor==valor2){
                            //System.out.println(list.get(a)+" "+list.get(a));
                            count++;
                            //System.out.println("valor: "+valor+", count:"+count);
                            //System.out.println(a +"-"+ b);
                        }   
                    }
                    if(count==i){
                        if(lista.contains(valor)){
                        } else {
                        lista.add(valor);
                        count = 1;
                        } 
                    }
                }
        }else{
            lista = list;
        }
        
        //System.out.println(i);
        System.out.println("Os termos buscados estao presentes nos documentos: ");
        System.out.println(lista);
        if(Main.status == 1||lista.isEmpty()){
            if(Main.status == 1){
            System.out.println("Sua busca resultou em poucos resultados, gostaria de consultar o dicionário?");
            }else{
                System.out.println("Sua busca nao obteve resultados, gostaria de consultar o dicionário?");
            }
            Scanner scanner = new Scanner(System.in);
            ctr = scanner.nextLine();
            if(ctr.equals("sim")){
                System.out.println("Consultando...");
                try{
                BufferedReader consulta = new BufferedReader(new FileReader(dicionario));
                dic = consulta.readLine( );
                
                //System.out.println("TESTES");
                //System.out.println(dic);
                //System.out.println(termo.get(0));
                
                //menordistancia = Levenshtein.distance(dic, termo.get(0));
                menordistancia = 99;
                for(int k = 0;k<termo.size();k++){
                dici.add(k, 99);
                dici2.add(k, "QQQQ");
                dici3.add(k, "QQQQ");
                }
                System.out.println(dici);
                //System.out.println(menordistancia);
                while(dic != null){
                    for(int b=0;b<termo.size();b++){
                        if(termo.get(b).equals(dic)){
                            System.out.println(termo.get(b)+" igual a "+dic);
                        }                                               
                        distancia = Levenshtein.distance(dic, termo.get(b));
                        
                        //System.out.println("Disntancia:"+distancia+" entre "+dic+" e "+termo.get(b));
                        if(distancia < menordistancia){
                        //System.out.println(menordistancia);    
                        dici.remove(b);
                        dici2.remove(b);
                        dici3.remove(b);
                        dici.add(b,distancia);
                        dici2.add(b,dic);
                        dici3.add(b,termo.get(b));
                        menordistancia = distancia;
                        sugestao = dic;
                        termoerrado = termo.get(b);
                        System.out.println("Match: "+menordistancia+" termo: "+sugestao+" do termo: "+termoerrado);
                        }
                    }
                dic = consulta.readLine();
                
                System.out.println(dici);
                System.out.println(dici2);
                System.out.println(dici3);
                }
                    
                }catch(FileNotFoundException fnfe){
                System.out.println("File not found");
                }
                
                for(int h = 0; h < termo.size() ; h++){
                    if(dici.get(h) != 0){
                        sugestao = dici2.get(h);
                        menordistancia = dici.get(h);
                        termoerrado = dici3.get(h);
                    }
                }
                
                if(menordistancia!=0){
                System.out.println("Voce quis dizer: "+sugestao+" Quando pesquisou: "+termoerrado+"?");
                ctr = scanner.nextLine();
                    if(ctr.equals("sim")){
                        for(int c=0;c<termo.size();c++){
                            if(termo.get(c).equals(termoerrado)){
                                termo.set(c, sugestao);
                            }
                        }
                    getDocuments(termo);
                    }
                }else{
                    System.out.println("Seu termo pesquisado está correto(esta no dicionario!)");
                }   
            }
        }
        return lista;    
    }

    public ArrayList<Match> getDocumentos(String termo) {
        if (this.hash.containsKey(termo)) {
            return this.hash.get(termo);
        } else {
            return null;
        }
    }
    
    public ArrayList<Match> getDocumentos(String[] pesquisa) {
        ArrayList<Match> resultado = null;
        ArrayList<Match> lista;
        String aux;
        boolean troca = true;
        try {
            while (troca) {
                troca = false;
                for (int i = 0; i < pesquisa.length - 1; i++) {
                    if (hash.get(pesquisa[i]).size() > hash.get(pesquisa[i + 1]).size()) {
                        aux = pesquisa[i];
                        pesquisa[i] = pesquisa[i + 1];
                        pesquisa[i + 1] = aux;
                        troca = true;
                    }
                }
            }
            resultado = hash.get(pesquisa[0]);
            int i = 0;
            while (i < pesquisa.length && !resultado.isEmpty()) {
                lista = hash.get(pesquisa[i]);
                resultado = intersecao(resultado, lista);
                i++;
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return resultado;
    }
    
    public ArrayList<Match> intersecao(ArrayList<Match> l1, ArrayList<Match> l2) {
        ArrayList<Match> resultado = new ArrayList();
        int i = 0, j = 0;

        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i).equals(l2.get(j))) {
                resultado.add(l1.get(i));
                i++;
                j++;
            } else if (l1.get(i).getDocID() < l2.get(j).getDocID()) {
                i++;
            } else if (l1.get(i).getDocID() > l2.get(j).getDocID()) {
                j++;
            }
        }
        return resultado;
    }
    
    public Double getTamanho(Integer chave) {
        return hash_Tam.get(chave);
    }
    
    public Double getResultado(Integer chave){
        return resultadoF.get(chave);
    }

    public int verifica_termo(String[] termos) {
        for (String termo : termos) {
            if (!this.hash.containsKey(termo)) {
                return 0;
            }
        }
        return 1;
    }

    public int TF (String termo, String documento){
        int tf = 0;
        Pattern pattern = Pattern.compile(termo);
        Matcher matcher = pattern.matcher(documento);
        while(matcher.find()){
            tf++;
        }
        return tf;
    }
    
    public int DF (String termo){
        return hash.get(termo).size();
    }
    
    public double tamanho_DOC (String[] words_tweet, String tweet){
        double tam = 0;
        for (String word : words_tweet) {
            tam = tam + Math.pow(this.TF(word, tweet),2);
        }
        return Math.sqrt(tam);
    }
    
    public double peso_TERMO (int tf, double tam){
        double peso = ((1 + Math.log(tf))/tam);
        return peso;
    }
    
    public double peso_CONSULTA (String termo, String consulta){
        int n = hash_Tam.size();
        double peso = ((1 + Math.log(this.TF(termo, consulta))) * Math.log(n/this.DF(termo)));
        return peso;
    }

    public List<String> pontuacao_COSSENO (String[] consulta, String consultaS, int qtd, ArrayList<Integer> docid){
        double pontuacao;
        int i = 0;
        PriorityQueue<Documento> pontos = new PriorityQueue();
        ArrayList<Integer> res = new ArrayList();
        List<String> list = new ArrayList<>();
        for (String termo : consulta){
            ArrayList<Match> matchs = this.getDocumentos(termo);
            for (Match match : matchs){
                pontuacao = this.peso_CONSULTA(termo, consultaS) * this.peso_TERMO(match.getTf(), this.getTamanho(match.getDocID()));
                this.put3(match.getDocID(), pontuacao);
            }
        }
        for (Integer key : resultadoF.keySet()){
            pontuacao = resultadoF.get(key)/this.getTamanho(key);
            resultadoF.put(key, pontuacao);
        }
        for (Integer key : resultadoF.keySet()) {
            pontos.offer(new Documento(key, resultadoF.get(key)));
        }
        while (!pontos.isEmpty()){
            res.add(pontos.poll().getDocID());
        }
        /*while (i < qtd && i < res.size()){
            System.out.println(res.get(i)+ " - "+docs.get(res.get(i))+ " - "+resultadoF.get(res.get(i)));
            list.add(docs.get(res.get(i)));
            i++;
        }*/
        while (i < qtd && i < res.size()){
            list.add(docs.get(res.get(i)));
            docid.add(res.get(i));
            i++;
        }
        
        for(int j=0;j<30;j++){
            System.out.println(res.get(j)+ " - "+docs.get(res.get(j))+ " - "+resultadoF.get(res.get(j)));
        }
        
    return list;
    }
    
    
}