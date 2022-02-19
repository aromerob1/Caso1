package caso1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Config {

    private int Amessages;
    private int Bmessages;
    private int Cmessages;
    private int Dmessages;
    private String[] p1 = new String[2];
    private String[] p2 = new String[2];
    private String[] p3 = new String[2];
    private String[] p4 = new String[2];

    public Config() {

        this.Amessages = 0;
        this.Bmessages = 0;
        this.Cmessages = 0;
        this.Dmessages = 0;
        for(int i = 0; i < 3 ; i++ ){
            this.p1[i] = "0";
            this.p2[i] = "0";
            this.p3[i] = "0";
            this.p4[i] = "0";
        }
    
    }

    public void loadData() throws FileNotFoundException{
        File file = new File("Configuracion.txt");
        Scanner scanner =  new Scanner(file);

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] info = line.split(" ");

            if (info[0].equals("A")){
                Amessages = Integer.valueOf(info[1]);
            }
            else if(info[0].equals("B")){ 
                Bmessages = Integer.valueOf(info[1]);
            }
            else if (info[0].equals("C")){
                Cmessages = Integer.valueOf(info[1]);
            }
            else if (info[0].equals("D")){
                Dmessages = Integer.valueOf(info[1]);
            }
            else if (info[0].equals("1")){
                for(int i = 1; i < 4 ; i++ ){
                    this.p1[i] = info[i];
                } 
            }
            else if (info[0].equals("2")){
                for(int i = 1; i < 4 ; i++ ){
                    this.p2[i] = info[i];
                } 
            }
            else if (info[0].equals("3")){
                for(int i = 1; i < 4 ; i++ ){
                    this.p3[i] = info[i];
                } 
            }
            else if (info[0].equals("4")){
                for(int i = 1; i < 4 ; i++ ){
                    this.p4[i] = info[i];
                } 
            }
        }
        scanner.close();
    }

    public int getAmessages(){
        return Amessages;
    }
    public int getBmessages(){
        return Bmessages;
    }
    public int getCmessages(){
        return Cmessages;
    }
    public int getDmessages(){
        return Dmessages;
    }
    public String[] getP1(){
        return p1;
    }
    public String[] getP2(){
        return p2;
    }
    public String[] getP3(){
        return p3;
    }
    public String[] getP4(){
        return p1;
    }
}
