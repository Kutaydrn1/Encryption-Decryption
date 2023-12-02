package sifrelemeodev;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class Sifrelemeodev extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sifrelemeodev frame = new Sifrelemeodev();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sifrelemeodev() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(735, 390, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Panel panel = new Panel();
		panel.setBackground(SystemColor.activeCaption);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTextArea inputArea = new JTextArea();
		inputArea.setText("Encrypt Me");
		inputArea.setBounds(81, 28, 333, 76);
		panel.add(inputArea);
		
		JTextArea outputArea = new JTextArea();
		outputArea.setBounds(81, 115, 333, 76);
		panel.add(outputArea);
		
		JLabel lblInputText = new JLabel("Input Text");
		lblInputText.setBounds(10, 57, 61, 14);
		panel.add(lblInputText);
		
		JLabel lblOutputText = new JLabel("Output Text");
		lblOutputText.setBounds(10, 145, 71, 14);
		panel.add(lblOutputText);
		
		Button getMacButton = new Button("Get MAC");
		getMacButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.setText(getMacAddressByNetworkInterface()); //MAC adresini inputArea'ya al
			}
		});
		getMacButton.setActionCommand("Get mac");
		getMacButton.setBounds(22, 215, 70, 22);
		panel.add(getMacButton);
		
		Button reverseButton = new Button("Reverse");
		reverseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.setText(outputArea.getText());//outputtaki texti inputa koy
				outputArea.setText("");//outputu temizle
			}
		});
		reverseButton.setActionCommand("Reverse button");
		reverseButton.setBounds(98, 215, 70, 22);
		panel.add(reverseButton);
		
		Button encryptButton = new Button("Encyrpt");
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sifrelenecekString = inputArea.getText();//şifrelenecek metni inputArea'dan al
				Encoder encoderBase64 = Base64.getEncoder();//Base64 şifreleme yöntemiiyle encoder olustur
				byte[] sifreliMetin = encoderBase64.encode(sifrelenecekString.getBytes());//base64 ile sifrele
				outputArea.setText(new String(sifreliMetin));//sifrelenmiş metni outputa yaz
			}
		});
		encryptButton.setActionCommand("Encrypt button");
		encryptButton.setBounds(174, 215, 70, 22);
		panel.add(encryptButton);
		
		Button decryptButton = new Button("Decrypt");
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cozulecekString = inputArea.getText();//şifresi çözülecek stringi al
				Decoder decoderBase64 = Base64.getDecoder();//base64 tipinde decoder oluştur
				byte[] cozulenMetin = decoderBase64.decode(cozulecekString.getBytes());//base64ile decode et
				outputArea.setText(new String(cozulenMetin));//output areaya yaz
			}
		});
		decryptButton.setActionCommand("Decrypt button");
		decryptButton.setBounds(250, 215, 70, 22);
		panel.add(decryptButton);
		
		Button clearButton = new Button("Clear All");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.setText("");//inputu temizle
				outputArea.setText("");//outputu temizle
			}
		});
		clearButton.setActionCommand("Clear button");
		clearButton.setBounds(326, 215, 70, 22);
		panel.add(clearButton);
	}
	
	private String getMacAddressByNetworkInterface() {//Mac adresinin bulup hafızada sakla
	    try {
	        List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());//network interfaceleri oluşturulan listeye aktar
	        for (NetworkInterface ni : nis) {
	            if (!ni.getName().equalsIgnoreCase("wlan0")) continue;//oluşturulan listede wlan0 ı bulana kadar devam et
	            byte[] macBytes = ni.getHardwareAddress();//mac adresini byte array tipinde al
	            if (macBytes != null && macBytes.length > 0) {
	                StringBuilder res1 = new StringBuilder();
	                for (byte b : macBytes) {
	                    res1.append(String.format("%02x:", b));//MAC adresini string tipine donustur [(%02:x)> 2 karakter yaz : koy]
	                }
	                return res1.deleteCharAt(res1.length() - 1).toString();//sonda kalan iki nokta karakterini silip geri dondur.
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); //hata sebebini yazdır
	    }
	    return "Cannot find MAC address.";//MAC adresi bulunamadıysa donen metin
	}
}

