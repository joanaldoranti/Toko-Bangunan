public class Produk {
    private int id;
    private String nama;
    private String kategori;
    private int stok;
    private double harga;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getInfo() {
        return String.format("| %-4d | %-20s | %-15s | %-5d | %-10.2f |", id, nama, kategori, stok, harga);
    }
}
