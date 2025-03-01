import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Serial {
    public static String serialize(Survey surveyObject, String location, String extra, String extension){
        // We're saving it to the local directory without any sub directories

        String path = location + File.separator + surveyObject.getName() + extra + extension;
        File directory = new File(location);
        directory.mkdir();

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(surveyObject);
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(2);
        }

        finally{
            try{
                if(fos != null)
                    fos.close();
                if(oos != null)
                    oos.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return path;
    }

    public static String serialize(Survey surveyObject, String location) {
        return serialize(surveyObject, location, "", ".ser");
    }

    public static Survey deserialize(String path) throws IOException, ClassNotFoundException {
        Survey deserializedObject = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
//        try{
        fis = new FileInputStream(path);
        ois = new ObjectInputStream(fis);
        deserializedObject = (Survey) ois.readObject();
//        }
//        catch(IOException | ClassNotFoundException e){
//            e.printStackTrace();
//            System.exit(2);

//        }


//        finally{
//            try{
//                if(ois != null)
//                    ois.close();
//                if(fis != null)
//                    fis.close();
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
        return deserializedObject;
    }

    public static List<File> getFilesWithExtension(String extension, String path, String prefix) {
        File directory = new File(path);

        List<File> files = new ArrayList<>();

        for(File file : directory.listFiles()) {
            if(file.getName().startsWith(prefix) && file.getName().endsWith(extension)) {
                files.add(file);
            }
        }

        return files;
    }

    public static Survey load(String surveyPath) {
        Survey survey = null;
        boolean gottenFile=false;

        File directory = new File(surveyPath);
        List<File> contents = Arrays.stream(directory.listFiles()).filter(file -> file.getName().endsWith(".ser")).collect(Collectors.toList());

        if(contents.size() == 0) {
            System.out.println("Nothing saved yet.\n");
            return null;
        }
        while (!gottenFile) {
            try {
                int counter = 1;
                for (File file : contents) {
                    System.out.printf("%d) %s\n", counter, file.getName());
                    counter += 1;
                }
                int convertedSurveyChoice = 0;
                String surveyChoice = (UserInput.getUserInput("Type the number of the survey you wish to load: "));
                if (InputValidator.validateThisMenuChoice(surveyChoice, counter-1)) {
                    convertedSurveyChoice = Integer.parseInt(surveyChoice);
                    surveyPath = "" + contents.get(convertedSurveyChoice - 1);

                    System.out.println(surveyPath);

                    survey = Serial.deserialize(surveyPath);
                    gottenFile = true;
                }
                else {
                    System.out.println("That was not part of the available menu list.\n");
                }

            } catch (IOException | ClassNotFoundException loadingError) {
                System.out.println("File name incorrect. Please put in a valid name.");
                loadingError.printStackTrace();
                System.exit(1);
            }

        }
        System.out.println("Loaded: " + survey.getName() + "\n");
        return survey;
    }

}
