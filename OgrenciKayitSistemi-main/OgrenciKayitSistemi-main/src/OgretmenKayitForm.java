import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class OgretmenKayitForm extends JFrame {
    private JPanel JPanel;
    private JTextField getOgretmenAdiTextField;
    private JTextField getOgretmenSoyadiTextField;
    private JTextField ogretmenTelefonTextField;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JButton geriGelButton;
    private JTextField textField2;
    private JButton kaydetButton;


    private DefaultTableModel tableModel;

    public OgretmenKayitForm() {
        setTitle("Öğretmen Kayıt Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JPanel);
        JPanel.setBackground(new Color(173, 216, 230));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Öğretmen Adı");
        tableModel.addColumn("Öğretmen Soyadı");
        tableModel.addColumn("Telefon");
        tableModel.addColumn("Adres");
        tableModel.addColumn("Verdiği Ders");


        ogrtablo.setModel(tableModel);


        ogretmenBilgileriniTabloyaCek();


        dersBilgileriniGetir();


        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kaydetButonuActionPerformed();
            }
        });




        gerigel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                anasayfa anasayfa = new anasayfa();
                anasayfa.setVisible(true);


                dispose();
            }
        });
    }

    private void ogretmenBilgileriniTabloyaCek() {
        String dosyaYolu = "ogretmen.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] ogretmenBilgileri = line.split(",");
                if (ogretmenBilgileri.length == 6) {
                    tableModel.addRow(ogretmenBilgileri);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Dosya okuma hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dersBilgileriniGetir() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ders.csv"))) {
            List<String> dersListesi = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dersBilgileri = line.split(",");

                if (dersBilgileri.length >= 2) {
                    dersListesi.add(dersBilgileri[1].trim());
                }
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dersListesi.toArray(new String[0]));
            comboBox2.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bir hata oluştu. Ders bilgileri alınamadı.");
        }
    }

    private void kaydetButonuActionPerformed() {
        String ogretmenAdi = öğretmenAdıTextField.getText();
        String ogretmenSoyadi = öğretmenSoyadıTextField.getText();
        String telNo = textField1.getText();
        String adres = textField2.getText();
        String okulBolum = (String) comboBox1.getSelectedItem();
        String verdigiDers = (String) comboBox2.getSelectedItem();

        try (PrintWriter writer = new PrintWriter(new FileWriter("ogretmen.csv", true))) {
            writer.println(ogretmenAdi + "," + ogretmenSoyadi + "," + telNo + "," + adres + "," + okulBolum + "," + verdigiDers);
            JOptionPane.showMessageDialog(this, "Öğretmen bilgileri başarıyla kaydedildi.");

            // Yeni öğretmeni tabloya ekle
            tableModel.addRow(new String[]{ogretmenAdi, ogretmenSoyadi, telNo, adres, okulBolum, verdigiDers});
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bir hata oluştu. Öğretmen bilgileri kaydedilemedi.");
        }
    }

    private void filterTableByOgretmenAdi(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        ogrtablo.setRowSorter(sorter);

        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0));
        }
    }

    public static void main(String[] args) {
        new OgretmenKayitForm();
    }
}
