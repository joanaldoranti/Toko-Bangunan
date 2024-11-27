import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// SEMENTARA //
public class KoneksiDatabase {
    private Connection koneksi;

    public void connect() {
        try {
            koneksi = DriverManager.getConnection("????", "username", "password");
            System.out.println("Koneksi berhasil.");
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (koneksi != null) {
                koneksi.close();
                System.out.println("Koneksi ditutup.");
            }
        } catch (Exception e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = koneksi.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Gagal menjalankan query: " + e.getMessage());
            return null;
        }
    }

    public int executeUpdate(String query) {
        try {
            Statement statement = koneksi.createStatement();
            return statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Gagal menjalankan update: " + e.getMessage());
            return 0;
        }
    }
}
