package utils;

import crytography.FileEncyDecy;
import crytography.InitialPasswordEncy;
import crytography.PhasePasswordEncy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Output {
    long startTime,endTime,seconds;
    FileEncyDecy enc=new FileEncyDecy();
    InitialPasswordEncy crypto = new InitialPasswordEncy();
    PhasePasswordEncy phase=new PhasePasswordEncy();

    public void outPutMethod() {
        List<String> pwd = new ArrayList<>();
        pwd.add("Aenkdfn@");
        pwd.add("a5dfE5dg@");
        pwd.add("gg545hGH56");
        pwd.add("DDFFffgg@@3");
        pwd.add("2D2g3D5h#$1");
        pwd.add("ghRRh@dg$$$d");
        pwd.add("EESs334Fyu9df");
        pwd.add("g$fkgjWlh#5g4GH");
        pwd.add("!@#jjewreEERRR%ghg");
        pwd.add("!@#bsdfkj&&&%klklhj");
        List<String> phasePwd = new ArrayList<>();
        phasePwd.add("Aenkdfn@");
        phasePwd.add("a5dfE5dg@");
        phasePwd.add("gg545hGH56");
        phasePwd.add("DDFFffgg@@3");
        phasePwd.add("2D2g3D5h#$1");
        phasePwd.add("ghRRh@dg$$$d");
        phasePwd.add("EESs334Fyu9df");
        phasePwd.add("g$fkgjWlh#5g4GH");
        phasePwd.add("!@#jjewreEERRR%ghg");
        phasePwd.add("!@#bsdfkj&&&%klklhj");
        for(String password:pwd){
            long s=System.nanoTime();
            System.out.println(password+" Password length "+password.length());

            System.out.println(password+" Password size "+getMemSize(password.getBytes()) );
            String cipher=crypto.encrypt(password);
            System.out.println(password+" cipher length "+cipher.length());
            System.out.println(password+" cipher size "+getMemSize(cipher.getBytes()) );
            crypto.decrypt(cipher);
            long e=System.nanoTime();
            float timeSec=e-s;
            System.out.printf("total execution time "+String.format("%.3f", (timeSec/1000000)/1000)+"\n");
            System.out.println("------------------------------------\n");
        }


        System.out.println("------------------------------------\n");


        for(String password:phasePwd){
            long s=System.nanoTime();
            System.out.println(password+" Password length "+password.length());

            System.out.println(password+" Password size "+getMemSize(password.getBytes()) );
            String cipher=phase.encrypt(password);
            System.out.println(password+" cipher length "+cipher.length());
            System.out.println(password+" cipher size "+getMemSize(cipher.getBytes()) );
            phase.decrypt(cipher);
            long e=System.nanoTime();
            float timeSec=e-s;
            System.out.printf("total execution time "+String.format("%.3f", (timeSec/1000000)/1000)+"\n");
            System.out.println("------------------------------------\n");
        }




    }

    public String getMemSize(byte[] bytes) {

        int size=(bytes.length*2 +45)/8;
        return ""+size*8;





    }


}
