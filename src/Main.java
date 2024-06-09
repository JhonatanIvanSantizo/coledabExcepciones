import java.io.*;
import java.util.Scanner;

public class Main {

    // Excepción personalizada para manejar archivos no encontrados
    public static class ArchivoNoEncontradoException extends Exception {
        public ArchivoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    // Excepción personalizada para manejar la creación de un archivo que ya existe
    public static class ArchivoYaExisteException extends Exception {
        public ArchivoYaExisteException(String mensaje) {
            super(mensaje);
        }
    }

    // Método para verificar la existencia de un archivo
    public static void verificarArchivo(String nombreArchivo) throws ArchivoNoEncontradoException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            throw new ArchivoNoEncontradoException("El archivo no existe.");
        }
    }

    // Método para crear un archivo
    public static void crearArchivo(String nombreArchivo) throws ArchivoYaExisteException {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            throw new ArchivoYaExisteException("El archivo ya existe.");
        } else {
            try {
                archivo.createNewFile();
                System.out.println("Archivo creado: " + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para agregar una nueva línea de texto a un archivo existente
    public static void agregarLinea(String nombreArchivo, String linea) throws ArchivoNoEncontradoException {
        verificarArchivo(nombreArchivo);
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(linea);
            System.out.println("Línea agregada al archivo: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer y mostrar el contenido de un archivo
    public static void leerArchivo(String nombreArchivo) throws ArchivoNoEncontradoException {
        verificarArchivo(nombreArchivo);
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para leer y mostrar una línea específica de un archivo
    public static void leerLineaEspecifica(String nombreArchivo, int numeroLinea) throws ArchivoNoEncontradoException {
        verificarArchivo(nombreArchivo);
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int contador = 1;
            while ((linea = br.readLine()) != null) {
                if (contador == numeroLinea) {
                    System.out.println(linea);
                    return;
                }
                contador++;
            }
            System.out.println("La línea " + numeroLinea + " no existe en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Probar las funcionalidades
        System.out.println("Pruebas de FileManager:");

        try {
            // Intentar crear un archivo
            System.out.print("Ingrese la ruta del archivo a crear: ");
            String rutaCrear = scanner.nextLine();
            crearArchivo(rutaCrear);

            // Intentar agregar una línea al archivo
            System.out.print("Ingrese la línea a agregar: ");
            String linea = scanner.nextLine();
            agregarLinea(rutaCrear, linea);

            // Leer y mostrar el contenido del archivo
            System.out.println("Contenido del archivo:");
            leerArchivo(rutaCrear);

            // Leer y mostrar una línea específica del archivo
            System.out.print("Ingrese el número de línea a leer: ");
            int numeroLinea = scanner.nextInt();
            leerLineaEspecifica(rutaCrear, numeroLinea);

        } catch (ArchivoNoEncontradoException | ArchivoYaExisteException e) {
            System.err.println(e.getMessage());
        }

        scanner.close();
    }
}
