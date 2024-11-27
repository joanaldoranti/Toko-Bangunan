import java.util.Scanner;

public class Main {
    private static Admin admin = new Admin("admin", "1234");
    private static ProdukDAO produkDAO = new ProdukDAO();

    public static void main(String[] args) {
        KoneksiDatabase koneksiDatabase = new KoneksiDatabase();
        koneksiDatabase.connect();

        showLoginScreen();

        koneksiDatabase.disconnect();
    }

    private static void showLoginScreen() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n=== Login Menu ===");
                System.out.println("1. Login sebagai Admin");
                System.out.println("2. Masuk sebagai User");
                System.out.println("3. Keluar");
                System.out.print("Pilih opsi: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminLogin(scanner);
                        break;
                    case 2:
                        showUserScreen();
                        break;
                    case 3:
                        System.out.println("Keluar dari aplikasi.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid. Silakan coba lagi.");
                scanner.nextLine();
            }
        }
    }

    private static void adminLogin(Scanner scanner) {
        System.out.print("\nMasukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        if (admin.login(username, password)) {
            System.out.println("Login berhasil!");
            showAdminScreen(scanner);
        } else {
            System.out.println("Login gagal. Kembali ke menu utama.");
        }
    }

    private static void showUserScreen() {
        displayProduk();
        System.out.println("\nTerima kasih atas kunjungan Anda.");
    }

    private static void showAdminScreen(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\n=== Selamat Datang di Toko Bangunan ===");
                System.out.println("1. Tampilkan Produk");
                System.out.println("2. Tambah Produk");
                System.out.println("3. Edit Produk");
                System.out.println("4. Hapus Produk");
                System.out.println("5. Logout");
                System.out.print("Pilih opsi: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayProduk();
                        break;
                    case 2:
                        addProduk(scanner);
                        break;
                    case 3:
                        editProduk(scanner);
                        break;
                    case 4:
                        deleteProduk(scanner);
                        break;
                    case 5:
                        System.out.println("Logout berhasil.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid. Silakan coba lagi.");
                scanner.nextLine();
            }
        }
    }

    private static void displayProduk() {
        System.out.println("\n============================ Daftar Produk ===========================");
        System.out.println("| ID   | Nama                 | Kategori        | Stok  | Harga      |");
        System.out.println("|------|----------------------|-----------------|-------|------------|");
        for (Produk produk : produkDAO.readAll()) {
            System.out.println(produk.getInfo());
        }
    }

    private static void addProduk(Scanner scanner) {
        try {
            System.out.print("\nMasukkan nama produk: ");
            String nama = scanner.nextLine();
            System.out.print("Masukkan kategori: ");
            String kategori = scanner.nextLine();
            System.out.print("Masukkan stok: ");
            int stok = scanner.nextInt();
            System.out.print("Masukkan harga: ");
            double harga = scanner.nextDouble();
            produkDAO.create(nama, kategori, stok, harga);
            System.out.println("Produk berhasil ditambahkan.");
            displayProduk();
        } catch (Exception e) {
            System.out.println("Input tidak valid. Silakan coba lagi.");
            scanner.nextLine();
        }
    }

    private static void editProduk(Scanner scanner) {
        try {
            displayProduk();
            System.out.print("\nMasukkan ID produk yang akan diedit: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Produk produk = produkDAO.readById(id);
            if (produk == null) {
                System.out.println("Produk dengan ID tersebut tidak ditemukan.");
                return;
            }

            while (true) {
                System.out.println("\n=== Edit Produk ===");
                System.out.println("1. Edit Nama");
                System.out.println("2. Edit Kategori");
                System.out.println("3. Edit Stok");
                System.out.println("4. Edit Harga");
                System.out.println("5. Selesai");
                System.out.print("Pilih opsi: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("\nMasukkan nama baru: ");
                        String nama = scanner.nextLine();
                        produk.setNama(nama);
                        System.out.println("Nama berhasil diubah.");
                        break;
                    case 2:
                        System.out.print("\nMasukkan kategori baru: ");
                        String kategori = scanner.nextLine();
                        produk.setKategori(kategori);
                        System.out.println("Kategori berhasil diubah.");
                        break;
                    case 3:
                        System.out.print("\nMasukkan stok baru: ");
                        int stok = scanner.nextInt();
                        produk.setStok(stok);
                        System.out.println("Stok berhasil diubah.");
                        break;
                    case 4:
                        System.out.print("M\nasukkan harga baru: ");
                        double harga = scanner.nextDouble();
                        produk.setHarga(harga);
                        System.out.println("Harga berhasil diubah.");
                        break;
                    case 5:
                        produkDAO.update(id, produk.getNama(), produk.getKategori(), produk.getStok(), produk.getHarga());
                        System.out.println("Perubahan berhasil disimpan.");
                        displayProduk();
                        return;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            }
        } catch (Exception e) {
            System.out.println("Input tidak valid. Silakan coba lagi.");
            scanner.nextLine();
        }
    }

    private static void deleteProduk(Scanner scanner) {
        try {
            displayProduk();
            System.out.print("\nMasukkan ID produk yang akan dihapus: ");
            int id = scanner.nextInt();
            produkDAO.delete(id);
        } catch (Exception e) {
            System.out.println("Input tidak valid. Silakan coba lagi.");
            scanner.nextLine();
        }
    }
}
