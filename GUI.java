import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class GUI extends JFrame {

    // Atribut
    private Admin admin;
    private ProdukDao produkDao;
    
    // Komponen GUI
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTable produkTable;
    private DefaultTableModel tableModel;
    
    public GUI() {
        admin = new Admin(); // Objek admin untuk login
        produkDao = new ProdukDao(); // Objek produkDao untuk akses data produk

        // Menyiapkan antarmuka pengguna (GUI)
        initUI();
    }

    private void initUI() {
        // Login Screen
        setTitle("Login");
        setLayout(null);
        setSize(400, 300);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 200, 30);
        add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdminScreen();
            }
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method untuk menampilkan login screen
    public void showLoginScreen() {
        setTitle("Login");
        setVisible(true);
    }

    // Method untuk menampilkan Admin screen setelah login berhasil
    public void showAdminScreen() {
        // Verifikasi login (contoh sederhana)
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (admin.authenticate(username, password)) {
            setTitle("Admin Dashboard");
            setLayout(null);
            setSize(800, 600);
            
            // Menampilkan tabel produk
            tableModel = new DefaultTableModel(new String[]{"ID", "Nama Produk", "Harga"}, 0);
            produkTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(produkTable);
            scrollPane.setBounds(50, 50, 700, 300);
            add(scrollPane);
            
            displayProduk(); // Menampilkan data produk
            
            // Tombol add produk
            addButton = new JButton("Add Product");
            addButton.setBounds(50, 400, 150, 30);
            add(addButton);
            
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addProduk(101); // Contoh menambahkan produk dengan id 101
                }
            });
            
            // Tombol edit produk
            editButton = new JButton("Edit Product");
            editButton.setBounds(220, 400, 150, 30);
            add(editButton);
            
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editProduk(); // Fungsi edit produk
                }
            });
            
            // Tombol delete produk
            deleteButton = new JButton("Delete Product");
            deleteButton.setBounds(400, 400, 150, 30);
            add(deleteButton);
            
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = produkTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int id = (int) tableModel.getValueAt(selectedRow, 0);
                        deleteProduct(id);
                    } else {
                        JOptionPane.showMessageDialog(null, "Pilih produk yang ingin dihapus.");
                    }
                }
            });

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal, silakan coba lagi.");
        }
    }

    // Menampilkan daftar produk di tabel
    public void displayProduk() {
        List<Produk> produkList = produkDao.getAllProduk();
        for (Produk produk : produkList) {
            tableModel.addRow(new Object[]{produk.getId(), produk.getNama(), produk.getHarga()});
        }
    }

    // Menambahkan produk baru
    public void addProduk(int id) {
        Produk produkBaru = new Produk(id, "Produk Baru", 100000);
        produkDao.addProduk(produkBaru);
        tableModel.addRow(new Object[]{produkBaru.getId(), produkBaru.getNama(), produkBaru.getHarga()});
    }

    // Mengedit produk
    public void editProduk() {
        int selectedRow = produkTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Produk produk = produkDao.getProdukById(id);
            produk.setNama("Produk Edit");
            produk.setHarga(150000);
            produkDao.updateProduk(produk);
            tableModel.setValueAt(produk.getNama(), selectedRow, 1);
            tableModel.setValueAt(produk.getHarga(), selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(null, "Pilih produk yang ingin diedit.");
        }
    }

    // Menghapus produk
    public void deleteProduct(int id) {
        produkDao.deleteProduk(id);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(id)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().showLoginScreen();
            }
        });
    }
}
