import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by abed on 5/8/17.
 */
public class Client {
    static Socket socket;
    static String host = "localhost";
    static int port = 25000;
    static Scanner scanner;
    static int sizeOfArray = 0;
    static int elm[][];

    public static void main(String args[]) throws IOException {

        createClient();
    }

    private static void createClient() throws IOException {
        try {
            /*
            get the local  ip
             */
            InetAddress inetAddress = InetAddress.getByName(host);
            socket = new Socket(inetAddress, port);

            /*
            send a message to the serve
             */
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);


            System.out.print("Enter size of array : ");
            scanner = new Scanner(System.in);
            sizeOfArray = scanner.nextInt();
            /*
            send the size of array to server
             */
            dataOutputStream.writeInt(sizeOfArray);
            /*
            clear for the stream for the next input
             */
            dataOutputStream.flush();

            /*
            initialize the array with the size that is entered by user
             */
            elm = new int[sizeOfArray][sizeOfArray];

            /*
            get the values from the user into the array
             */
            System.out.println("--: Enter the values of array :--");
            for (int i = 0; i < sizeOfArray; i++) {
                for (int j = 0; j < sizeOfArray; j++) {
                    System.out.print("[" + i + "]" + "[" + j + "]=");
                    elm[i][j] = scanner.nextInt();
                }
            }

            /*
            print the array
             */
            System.out.println("\n--: Matrix  is :--");
            for(int i1=0;i1<sizeOfArray;i1++){
                for(int j1=0;j1<sizeOfArray;j1++){
                    System.out.print(" "+elm[i1][j1]);
                }
                System.out.println("");
            }
            System.out.println("-------------------------------------------");

            /*
            send data to the serve
             */
            for (int i1 = 0; i1 < sizeOfArray; i1++) {
                for (int j1 = 0; j1 < sizeOfArray; j1++) {
                    dataOutputStream.writeInt(elm[i1][j1]);
                }
                System.out.println("");
            }



            while (true) {
            /*
            getting the determinant from the server
             */
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int s1 = dataInputStream.readInt();
                System.out.println("Determinant : " + s1);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
