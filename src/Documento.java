
public class Documento implements Comparable<Documento>{
    private int docID;
    private double pontuacao;

    public Documento(int docID, double pontuacao) {
        this.docID = docID;
        this.pontuacao = pontuacao;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public int compareTo(Documento o) {
        if (pontuacao == o.getPontuacao()){
            return 0;
        } else if (pontuacao < o.getPontuacao()){
            return 1;
        } else {
            return -1;
        }
    }
}
