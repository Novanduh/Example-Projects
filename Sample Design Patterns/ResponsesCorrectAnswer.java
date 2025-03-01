import java.io.Serializable;
import java.util.ArrayList;

public class ResponsesCorrectAnswer implements Serializable {
    private static final long serialVersionUID = 987654323;
    private ArrayList<String> responses = new ArrayList<>();

    public ResponsesCorrectAnswer() {
    }
    public void add(String responseInput) {
        this.responses.add(responseInput);
    }

    public void display() {
        for(String response : this.getResponses()) {
            System.out.println(response);
        }
    }

    public ArrayList<String> getResponses() {
        return this.responses;
    }

    public boolean isEqual(ResponsesCorrectAnswer responsesCorrectAnswer) {
        for(String response : responsesCorrectAnswer.getResponses()) {
            if(!this.getResponses().contains(response)) {
                return false;
            }
        }
        return true;
    }
}


