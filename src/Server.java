import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by abed on 5/8/17.
 */
public class Server {
    static ServerSocket serverSocket;
    static Socket socket;
    static int servePort = 25000;
    static int[][] matrix;

    public static void main(String args[]) {
        create_socket();
    }

    private static void create_socket() {
        /*
        create a server socket
                @param  1 port is port number
                @param  2 backlog is the number of clients that can connect
         */
        try {
            serverSocket = new ServerSocket(servePort, 1);
            System.out.println("Server listening to port 4444");

            /*
            get connection from the client
             */
            socket = serverSocket.accept();
            System.out.println("Client is in " + socket.getInetAddress().getHostName());


            /*
            server is running always
             */
            while (true) {
                /*
                read the message from the client
                 */

                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int s = dataInputStream.readInt();

                matrix = new int[s][s];
                for (int i = 0; i < s; i++) {
                    for (int j = 0; j < s; j++) {
                        matrix[i][j] = dataInputStream.readInt();
                    }
                }
                System.out.println("\n--: Matrix from Client is :--");
                for (int i1 = 0; i1 < s; i1++) {
                    for (int j1 = 0; j1 < s; j1++) {
                        System.out.print(" " + matrix[i1][j1]);
                    }
                    System.out.println("");
                }
                System.out.println("-------------------------------------------");

                int det = calculateDeterminant(matrix, s);

                /*
                sending response to the client
                 */
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeInt(det);
                System.out.println("Determinant is: " + det);
            }
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

    private static int calculateDeterminant(int[][] matrix, int size) {
        int det = 0;
        if (size == 1) {
            det = matrix[0][0];
        } else if (size == 2) {
            det = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        } else {
            for (int j1=0; j1<size; j1++)
            {
               int [][]m = generateSubArray (matrix, size, j1);
                det += Math.pow(-1.0, 1.0+j1+1.0) * matrix[0][j1] * calculateDeterminant(m, size-1);
            }
        }
        return det;
    }

    private static int[][] generateSubArray(int[][] matrix, int size, int j1) {
        int [][]matrixx = new int[size-1][];
        for (int k=0; k<(size-1); k++)
            matrixx[k] = new int[size-1];

        for (int i=1; i<size; i++)
        {
            int j2=0;
            for (int j=0; j<size; j++)
            {
                if(j == j1)
                    continue;
                matrixx[i-1][j2] = matrix[i][j];
                j2++;
            }
        }
        return matrix;
    }
}
