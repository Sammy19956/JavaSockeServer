import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class HttpServer {
    public void routing(){
        StringBuilder contact = new StringBuilder();
       try {
           BufferedReader br = new BufferedReader(new FileReader("src/main/java/Pages/contact.html"));
           String str = "";
           while((str = br.readLine())!= null){
                 contact.append(str);
            }
       } catch(IOException e){
           System.out.println(e.getMessage());
       }

        StringBuilder home = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/Pages/Home.html"));
            String str = "";
            while((str = br.readLine())!= null){
                home.append(str);
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        StringBuilder about = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/Pages/about.html"));
            String str = "";
            while((str = br.readLine())!= null){
                about.append(str);
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }


        try{
            ServerSocket server =  new ServerSocket(9876);
            System.out.println("Waiting for connection to http://localhost:9876");
            while (true) {
                Socket client = server.accept();
                BufferedReader input =  new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                PrintWriter output =  new PrintWriter(client.getOutputStream(), true);
                output.print("HTTP/1.1 2OO \r\n");
                output.print("Content-Type: text/html \r\n");
                output.print("Connection: Keep Alive \r\n");
                output.print("\r\n");
                String line;
                List<String> lines =  new ArrayList<>();
                while((line = input.readLine())!=null){
                    if (line.length()==0){
                        break;
                    }
                    lines.add(line.split(" ")[1]);
//                    System.out.println(line);
                }
                if (lines.get(0).equals("/about")){
                    output.print(about);
                }
                else if(lines.get(0).equals("/contact")){
                        output.print(contact);
                } else{
                    output.print(home);
                }
                output.close();
                input.close();
                client.close();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
