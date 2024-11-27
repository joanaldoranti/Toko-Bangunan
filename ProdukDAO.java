import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {
    private List<Produk> produkList = new ArrayList<>();
    private int nextId = 1;

    public void create(String nama, String kategori, int stok, double harga) {
        Produk produk = new Produk();
        produk.setNama(nama);
        produk.setKategori(kategori);
        produk.setStok(stok);
        produk.setHarga(harga);
        produkList.add(produk);
        produk.setId(nextId++);
        System.out.println("Produk berhasil ditambahkan.");
    }

    public List<Produk> readAll() {
        return produkList;
    }

    public void update(int id, String nama, String kategori, int stok, double harga) {
        for (Produk produk : produkList) {
            if (produk.getId() == id) {
                produk.setNama(nama);
                produk.setKategori(kategori);
                produk.setStok(stok);
                produk.setHarga(harga);
                System.out.println("Produk berhasil diperbarui.");
                return;
            }
        }
        System.out.println("Produk tidak ditemukan.");
    }

    public void delete(int id) {
        boolean removed = produkList.removeIf(produk -> produk.getId() == id);
        if (removed) {
            System.out.println("Produk berhasil dihapus.");
        } else {
            System.out.println("Produk tidak ditemukan.");
        }
    }

    public Produk readById(int id) {
        for (Produk produk : produkList) {
            if (produk.getId() == id) {
                return produk;
            }
        }
        return null;
    }

}