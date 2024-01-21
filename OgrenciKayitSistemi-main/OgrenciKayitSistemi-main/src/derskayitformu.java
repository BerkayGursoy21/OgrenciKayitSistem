import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class derskayitformu extends JFrame {

    private JPanel JPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JButton kaydetButton;

    public derskayitformu() {

        setTitle("Ders Kayıt");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JPanel);


        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bilgi1 = textField1.getText();
                String bilgi2 = textField2.getText();
                String bilgi3 = textField3.getText();


                kaydetDersCSV(bilgi1, bilgi2, bilgi3);


                JOptionPane.showMessageDialog(null, "Ders bilgileri başarıyla kaydedildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);


                anasayfa anasayfa = new anasayfa();
                anasayfa.setVisible(true);


                dispose();
            }
        });
    }

    private void kaydetDersCSV(String bilgi1, String bilgi2, String bilgi3) {
        String dosyaYolu = "ders.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(dosyaYolu, true))) {

            writer.println(bilgi1 + "," + bilgi2 + "," + bilgi3);
            writer.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Dosya yazma hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new derskayitformu();
    }
}
