import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ordenador {

	public static void ordenar() throws Exception {

		String inputFile = "C:/Users/a11011BCC021/Desktop/ORI/indices.txt";
		String outputFile = "C:/Users/a11011BCC021/Desktop/ORI/indicesordenado.txt";
                
                
		FileReader fileReader = new FileReader("C:/Users/a11011BCC021/Desktop/ORI/indices.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String inputLine;
		List<String> lineList = new ArrayList<String>();
		while ((inputLine = bufferedReader.readLine()) != null) {
			lineList.add(inputLine);
                        System.out.println(inputLine);
                }
		fileReader.close();

		Collections.sort(lineList);

		FileWriter fileWriter = new FileWriter(outputFile);
		PrintWriter out = new PrintWriter(fileWriter);
		for (String outputLine : lineList) {
			out.println(outputLine);
		}
		out.flush();
		out.close();
		fileWriter.close();
                System.out.println("\nLista de indices ordenada!");
                System.out.println("\n");
	}
}