import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimuladorBanco {

    private static Map<String, Double> cuentas = new HashMap<>();
    private static Map<String, Integer> nipCuentas = new HashMap<>();
    private static Map<String, StringBuilder> historialTransacciones = new HashMap<>();
    private static Map<String, StringBuilder> historialAcceso = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBienvenido al Simulador de Banco");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearCuenta(scanner);
                    break;
                case 2:
                    iniciarSesion(scanner);
                    break;
                case 3:
                    System.out.println("Gracias por usar el Simulador de Banco. ¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void crearCuenta(Scanner scanner) {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        // Generar número de cuenta aleatorio
        String numeroCuenta = "ACC" + (1000 + (int) (Math.random() * 9000));

        // Generar NIP aleatorio de 4 dígitos
        int nip = 1000 + (int) (Math.random() * 9000);

        cuentas.put(numeroCuenta, 0.0);
        nipCuentas.put(numeroCuenta, nip);
        historialTransacciones.put(numeroCuenta, new StringBuilder());
        historialAcceso.put(numeroCuenta, new StringBuilder());

        System.out.println("¡Cuenta creada con éxito!");
        System.out.println("Su número de cuenta es: " + numeroCuenta);
        System.out.println("Su NIP es: " + nip);
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.print("Ingrese su número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        if (cuentas.containsKey(numeroCuenta)) {
            System.out.print("Ingrese su NIP: ");
            int nipIngresado = scanner.nextInt();
            scanner.nextLine();

            if (nipCuentas.get(numeroCuenta) == nipIngresado) {
                // Registrar acceso en el historial
                historialAcceso.get(numeroCuenta).append("Acceso: ")
                        .append(java.time.LocalDateTime.now())
                        .append("\n");

                mostrarMenuPrincipal(scanner, numeroCuenta);
            } else {
                System.out.println("NIP incorrecto. Inténtelo de nuevo.");
            }
        } else {
            System.out.println("Número de cuenta no encontrado. Verifique e inténtelo de nuevo.");
        }
    }

    private static void mostrarMenuPrincipal(Scanner scanner, String numeroCuenta) {
        while (true) {
            System.out.println("\nMenú Principal");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Realizar depósito");
            System.out.println("3. Realizar retiro");
            System.out.println("4. Cambiar NIP");
            System.out.println("5. Ver historial de transacciones");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    consultarSaldo(numeroCuenta);
                    break;
                case 2:
                    realizarDeposito(scanner, numeroCuenta);
                    break;
                case 3:
                    realizarRetiro(scanner, numeroCuenta);
                    break;
                case 4:
                    cambiarNIP(scanner, numeroCuenta);
                    break;
                case 5:
                    verHistorialTransacciones(numeroCuenta);
                    break;
                case 6:
                    // Registrar cierre de sesión en el historial
                    historialAcceso.get(numeroCuenta).append("Cierre de sesión: ")
                            .append(java.time.LocalDateTime.now())
                            .append("\n");

                    System.out.println("Sesión cerrada. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void consultarSaldo(String numeroCuenta) {
        double saldo = cuentas.get(numeroCuenta);
        System.out.println("Su saldo actual es: $" + String.format("%.2f", saldo));
    }

    private static void realizarDeposito(Scanner scanner, String numeroCuenta) {
        System.out.print("Ingrese la cantidad a depositar: $");
        double cantidad = scanner.nextDouble();
        scanner.nextLine();

        double saldoActual = cuentas.get(numeroCuenta);
        cuentas.put(numeroCuenta, saldoActual + cantidad);

        historialTransacciones.get(numeroCuenta).append("Depósito de $")
                .append(String.format("%.2f", cantidad))
                .append("\n");

        System.out.println("Depósito realizado con éxito. Nuevo saldo: $" + String.format("%.2f", cuentas.get(numeroCuenta)));
    }

    private static void realizarRetiro(Scanner scanner, String numeroCuenta) {
        System.out.print("Ingrese la cantidad a retirar: $");
        double cantidad = scanner.nextDouble();
        scanner.nextLine();

        double saldoActual = cuentas.get(numeroCuenta);

        if (saldoActual >= cantidad) {
            cuentas.put(numeroCuenta, saldoActual - cantidad);

            historialTransacciones.get(numeroCuenta).append("Retiro de $")
                    .append(String.format("%.2f", cantidad))
                    .append("\n");

            System.out.println("Retiro realizado con éxito. Nuevo saldo: $" + String.format("%.2f", cuentas.get(numeroCuenta)));
        } else {
            System.out.println("Saldo insuficiente para realizar el retiro.");
        }
    }

    private static void cambiarNIP(Scanner scanner, String numeroCuenta) {
        System.out.print("Ingrese su nuevo NIP (4 dígitos): ");
        int nuevoNIP = scanner.nextInt();
        scanner.nextLine();

        // Actualizar el NIP
        nipCuentas.put(numeroCuenta, nuevoNIP);

        System.out.println("NIP cambiado con éxito.");
    }

    private static void verHistorialTransacciones(String numeroCuenta) {
        System.out.println("Historial de transacciones:");
        System.out.println(historialTransacciones.get(numeroCuenta).toString());
    }
}
