
public class Match {
    private int docID;
    private int tf;

    public Match(int docID, int tf) {
        this.docID = docID;
        this.tf = tf;
    }

    public int getDocID() {
        return docID;
    }

    public int getTf() {
        return tf;
    } 
}
