import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;


public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<String> words = new ArrayList<String>();
    public static void main(String[] args) throws IOException{
        boolean edited = false;
        boolean loaded = false;
        boolean saved = false;
        String fileName ="";

        boolean done = true;
        while(done){
            String temp = (InputHelper.getRegExString(scan,"Would you like to \nAdd an item [A] \nDelete an item [D]\nView a list [V]\nQuit [Q]\n" +
                    "Open a list [O]\nSave a list [S]\nClear the list [C]", "[AaDdVvQqOoSsCc]"));
            if (temp.equalsIgnoreCase("A")){
                addMethod();
                if(loaded) {
                    edited = true;
                    saved = false;
                }
            } else if (temp.equalsIgnoreCase("D")){
                deleteMethod();
                if(loaded) {
                    edited = true;
                    saved = false;
                }
            } else if(temp.equalsIgnoreCase("V")){
                printMethod();
            } else if(temp.equalsIgnoreCase("Q")){
                if(saved){
                    quitMethod();
                } else{
                    if(InputHelper.getYNConfirm(scan,"Would you like to save your progress")){
                        if(loaded){
                            IOHelper.writeFile(words, fileName);
                        } else{
                            IOHelper.writeFile(words, InputHelper.getNonZeroLenString(scan,"What name would you like to save it under?"));
                        }
                    } else{
                        quitMethod();
                    }
                }
                quitMethod();
            } else if(temp.equalsIgnoreCase("O")){
                if(edited){
                    if(loaded){
                        IOHelper.writeFile(words, fileName);
                    } else{
                        if(InputHelper.getYNConfirm(scan, "Do you want to save the current file before opening another list?")){
                            IOHelper.writeFile(words, InputHelper.getNonZeroLenString(scan,"What name would you like to save it under?"));
                        }
                    }
                }
                words.clear();
                fileName = IOHelper.openFile(words);
                loaded = true;
                edited = false;
                saved = false;
            } else if(temp.equalsIgnoreCase("S")){
                if (loaded){
                    IOHelper.writeFile(words, fileName);
                } else{
                    IOHelper.writeFile(words, InputHelper.getNonZeroLenString(scan,"What name would you like to save it under?"));
                }
                saved = true;
                edited = false;
                if (!InputHelper.getYNConfirm(scan,"Do you want to continue working on the same file?[Y/N]")){
                    clearMethod();
                    loaded = false;
                }

            } else if(temp.equalsIgnoreCase("C")){
                clearMethod();
                if(loaded) {
                    edited = true;
                    saved = false;
                }
            }
        }
    }
    public static void  addMethod(){
        System.out.println("What item would you like to add to the list?");
        words.add(scan.nextLine());
    }
    public static void  deleteMethod(){
        words.remove(InputHelper.getRangedInt(scan,"What Item spot would you like to remove (List starts at 0)", 1,words.size()-1));
    }
    public static void  printMethod(){
        for(int i = 0; i < words.size(); i++){
            System.out.print(i + " - " + words.get(i) + ", ");
        }
        System.out.println();
    }

    public static void  quitMethod(){
        if(InputHelper.getRegExString(scan,"Are you sure you want to quit? [Y/N]", "[YyNn]").equalsIgnoreCase("Y")){
            System.exit(0);
        }
    }
    public static void  clearMethod(){
        words.clear();
    }
}