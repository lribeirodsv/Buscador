import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import de.daslaboratorium.machinelearning.classifier.BayesClassifier;
import de.daslaboratorium.machinelearning.classifier.Classifier;

public class Main {

    static int status = 0;
    public static BTree<String, String> st = new BTree<String, String>();
    
    public static void main(String[] args) throws IOException, Exception {
        
        int controle = 13;
        int docid;
        String termo;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------MENU-------");
                
        while(controle>12 | controle<1){
        
        System.out.println("1- Inserir associacao");
        System.out.println("2- Obter lista de documentos associados a um termo");
        System.out.println("3- Buscar");
        System.out.println("4- Gerar indices");
        System.out.println("5- Ordenar");
        System.out.println("6- Gerar tabela de indice invertido e dicionario de dados");
        System.out.println("7- Testar Arvore");
        System.out.println("8- Busca utilizando modelo vetorial");
        System.out.println("9- Busca utilizando classificacao Naive Bayes");
        System.out.println("12- Sair");
        System.out.print("Opcao: ");
        controle = scanner.nextInt();
        
            if(controle==1){
                System.out.println("Digite o docId: ");
                docid = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Digite o termo: ");
                termo = scanner.nextLine();
                IndiceInvertido.put1(termo, docid);
                controle = 99;
            }
            if(controle==2){
                System.out.println("Digite o termo: ");
                scanner.nextLine();
                termo = scanner.nextLine();
                IndiceInvertido.getDocuments(termo);
                controle = 99;
            }
            if(controle==3){
                int i=1;
                List<String> list = new ArrayList<>();
                System.out.println("Digite os termos de busca: ");
                scanner.nextLine();
                termo = scanner.nextLine();
                StringTokenizer term = new StringTokenizer(termo); 
                while(term.hasMoreTokens())  
                {  
                    String n = term.nextToken();  
                    String j;
                    
                    if(n.indexOf('*') != -1){
                        //n.replace('*', '$');
                        j = Permutermos.consultaDicionario(n);
                        list.add(j);
                        //System.out.println(list);
                        StringTokenizer perm = new StringTokenizer(n,"*");
                        while(perm.hasMoreTokens()){
                            //System.out.println(perm.nextToken());
                            j = Permutermos.consultaDicionario(perm.nextToken());
                            //System.out.println(j);
                            if(!list.contains(j)){
                            list.add(j);
                            }
                            //System.out.println(list);
                        }
                        //System.out.println("Termo"+i+": "+j);
                    }else{
                        list.add(n);
                        System.out.println("Termo"+i+": "+n);
                    }
                    
                    i++;
                }  
                System.out.println("");
                System.out.println("Realizando a busca para os termos: ");
                System.out.println(list);
                System.out.println("");
                System.out.println("");
                
                IndiceInvertido.getDocuments(list);
                controle = 99;
            }
            if(controle==4){
                Tokenizer.tokenize();
                controle = 99;
            }
            if(controle==5){
                Ordenador.ordenar();
                controle = 99;
            }
            if(controle==6){
                IndiceInvertido.indiceInvertido();
                controle = 99;
            }
            if(controle==7){
                Permutermos.testeArvore();
                controle = 99;
            }
            if(controle==8){
                System.out.println("Construindo o modelo vetorial...");
                IndiceInvertido hash = new IndiceInvertido();
                BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Lucas/Documents/corpus.csv"));
                try {
                    //Pula a primeira linha
                    String a = reader.readLine();
                    while (reader.ready()) {
                        a = reader.readLine();
                        String b[] = a.split(",");
                        int guarda_id = Integer.parseInt(b[0]);
                        String aux = "";
                        for(int i = 2; i < b.length;i++){
                           aux = aux.concat(b[i]);
                           aux = aux.concat(" ");
                        }
                        aux = aux.toLowerCase();
                        aux = aux.replaceAll("[^a-z]", " ");
                        aux = aux.trim();
                        String words_tweet[] = aux.split(" ");
                        hash.put4(guarda_id, aux);
                        String tweet2 = "";
                        for (String palavra : words_tweet) {
                            if (palavra.length() > 3 && palavra.length() < 15) {
                                int tf = hash.TF(palavra, aux);
                                Match match = new Match(guarda_id, tf);
                                hash.put(palavra, match);
                                tweet2 = tweet2.concat(palavra);
                                tweet2 = tweet2.concat(" ");
                            }
                        }
                        String words_tweet2[] = tweet2.split(" ");
                        hash.put2(guarda_id, hash.tamanho_DOC(words_tweet2, tweet2));
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Modelo construido com sucesso!");
                int qtd = 0;
                String term = "";
                String ctr = "";
                int opcao = 0;
                do{
                System.out.println();
                System.out.println("Digite quantos resultados deseja mostrar: ");
                qtd = scanner.nextInt();
                System.out.println("Digite a pesquisa");
                scanner.nextLine();
                term = scanner.nextLine();
                System.out.println();
                System.out.println(" DocID\t\tTweet\t\tRelevancia");
                String[] pesquisa = term.split(" ");
                System.out.println("WHAT");
                System.out.println(pesquisa[0]+pesquisa[1]);
                hash.pontuacao_COSSENO(pesquisa, term, qtd, null);
                System.out.println("Deseja realizar outra pesquisa? ");
                ctr = scanner.nextLine();
                    if(ctr.equals("sim")){
                        opcao = 1;
                    }else{
                        opcao = 0;
                    }
                }while(opcao == 1);
                controle = 99;
            }
            if(controle==9){
                String op;
                int opi = 0;
                System.out.println("");
                System.out.println("Entrando em busca Naive Bayes...");
                System.out.println("Construindo o modelo vetorial...");
                IndiceInvertido hash = new IndiceInvertido();
                ArrayList<Integer> as = new ArrayList();
                BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Lucas/Documents/corpus.csv"));
                try {
                    //Pula a primeira linha
                    String a = reader.readLine();
                    while (reader.ready()) {
                        a = reader.readLine();
                        String b[] = a.split(",");
                        int guarda_id = Integer.parseInt(b[0]);
                        String aux = "";
                        for(int i = 2; i < b.length;i++){
                           aux = aux.concat(b[i]);
                           aux = aux.concat(" ");
                        }
                        aux = aux.toLowerCase();
                        aux = aux.replaceAll("[^a-z]", " ");
                        aux = aux.trim();
                        String words_tweet[] = aux.split(" ");
                        hash.put4(guarda_id, aux);
                        String tweet2 = "";
                        for (String palavra : words_tweet) {
                            if (palavra.length() > 3 && palavra.length() < 15) {
                                int tf = hash.TF(palavra, aux);
                                Match match = new Match(guarda_id, tf);
                                hash.put(palavra, match);
                                tweet2 = tweet2.concat(palavra);
                                tweet2 = tweet2.concat(" ");
                            }
                        }
                        String words_tweet2[] = tweet2.split(" ");
                        hash.put2(guarda_id, hash.tamanho_DOC(words_tweet2, tweet2));
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Modelo construido com sucesso!");
                int qtd = 200;
                String term = "";
                String ctr = "";
                int opcao = 0;
                System.out.println();
                System.out.println("Digite a pesquisa");
                scanner.nextLine();
                term = scanner.nextLine();
                System.out.println();
                System.out.println("Os 30 documentos mais relevantes a pesquisa: ");
                System.out.println();
                System.out.println(" DocID\t\tTweet\t\tRelevancia");
                String[] pesquisa = term.split(" ");
                List<String> pesquisados = new ArrayList<>();
                //System.out.println("#################################");
                //System.out.println(term);
                //System.out.println(pesquisa[0]);
                pesquisados = hash.pontuacao_COSSENO(pesquisa, term, qtd, as);
                //System.out.println(pesquisados);
                final Classifier<String, String> bayes = new BayesClassifier<String, String>();
                List<String> list = new ArrayList<>();
                String leitura;
                int conceitos;
                System.out.println("-----------------------");
                System.out.println("Entrando no modo TREINO");
                System.out.print("Informe quantos conceitos serao usados: ");
                conceitos = scanner.nextInt();
                conceitos++;
                for(int i=1 ; i<conceitos ; i++){
                    System.out.print("Informe o conceito "+i+": ");
                    leitura = scanner.next();
                    list.add(leitura);
                }
                //System.out.println(list);
                int i=0;
                do{
                    System.out.println("Apresentando documentos...");
                    for(int a=0;a<10;a++){
                        System.out.println("Para o documento a seguir, classifique de acordo com os conceitos abaixo");
                        System.out.println(pesquisados.get(i));
                        //System.out.println("---------------"+i+"---------------");
                            for(int j=1 ; j<conceitos ; j++){
                                System.out.println("("+j+")"+list.get(j-1));
                            }
                        System.out.print("Opcao: ");
                        int concept = scanner.nextInt();
                        concept--;
                        bayes.learn(list.get(concept), Arrays.asList(pesquisados.get(i).split("\\s")));
                        System.out.println(list.get(concept)+" "+pesquisados.get(i));
                        System.out.println("");
                        i++;
                    }
                    System.out.println("Gostaria de continuar aprendendo?");
                        op = scanner.next();
                        if(op.equals("sim")){
                            opi = 1;
                        }else{
                            opi = 0;
                        }
                }while(opi == 1);    
                System.out.println("");
                System.out.println("----------------------");
                System.out.println("Entrando no modo Teste");
                System.out.println("");
                System.out.println("A pesquisa deve obedecer o formato: 'conceito: pesquisa'");
                System.out.println("Digite a pesquisa");
                scanner.nextLine();
                term = scanner.nextLine();
                StringTokenizer perm = new StringTokenizer(term,": ");
                String conceito = perm.nextToken();
                //System.out.println("------------CONCEITITO: "+conceito);
                String term2 = perm.nextToken();
                //System.out.println("------------PESQUISITA: "+term2);
                System.out.println("");
                System.out.println("Os 30 documentos mais relevantes a pesquisa: ");
                System.out.println("");
                System.out.println(" DocID\t\tTweet\t\tRelevancia");
                String[] pesquisa2 = term2.split(" ");
                List<String> pesquisados2 = new ArrayList<>();
                System.out.println("Resultado da pesquisa normal: ");
                //System.out.println("#################################");
                //System.out.println(term2);
                //System.out.println(pesquisa2[0]);
                pesquisados2 = hash.pontuacao_COSSENO(pesquisa2, term2, qtd, as);
                
                //System.out.println("TESTEZITO");
                //System.out.println(pesquisados2.get(0));
                                
                //final String[] unknownText1 = "airplane crash".split("\\s");
                //System.out.println("TESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                //System.out.println(bayes.classify(Arrays.asList(unknownText1)).getCategory());
                System.out.println(""); 
                System.out.println(""); 
                System.out.println("Documentos relacionados com a categoria: "+conceito.toUpperCase());
                System.out.println("");
                System.out.println(" DocID\t\tTweet");
                int u = 0;
                for(int n=0 ; n<150 ; n++){
                    String[] current = pesquisados2.get(n).split("\\s");
                    String categoria = bayes.classify(Arrays.asList(current)).getCategory();
                    //System.out.println("TESTEEEE----"+pesquisados2.get(n)+" "+categoria);
                    if(categoria.equals(conceito)){
                        System.out.println(as.get(n)+" - "+pesquisados2.get(n));
                        u++;
                        if(u==30){
                            break;
                        }
                    }
                }
            }System.out.println("");
        }        
    }
}


    