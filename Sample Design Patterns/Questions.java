import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Questions implements Serializable {

    private ArrayList<ResponsesCorrectAnswer> responseList;
    private static final long serialVersionUID = 987654322;
    protected String questionPrompt;
    protected int neededResponses = 1;

    public Questions(String questionPrompt) {
        this.questionPrompt = questionPrompt;
        this.responseList = new ArrayList<>();

    }
    public void create() {

    }

    public void edit(){

    }

    public void display() {
        System.out.println(this.questionPrompt);
        for (ResponsesCorrectAnswer response: responseList) {
            response.display();
        }
    }

    public void take(){
    }

    public abstract void displayTabulated(HashMap<String, Integer> tabulatedValues);

    public List<ResponsesCorrectAnswer> getResponseList() {
        return responseList;
    }


}
