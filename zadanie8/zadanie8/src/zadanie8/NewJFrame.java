/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zadanie8;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.table.DefaultTableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;


public class NewJFrame extends javax.swing.JFrame {

	public static final int x = 100; 
	public static ArrayList<Object[]> rowList = new ArrayList<>();
	public static DefaultTableModel model;
//	
//	
//	 public static Document renderPage(String url) {
//	        System.setProperty("phantomjs.binary.path", 'libs/phantomjs') // path to bin file. NOTE: platform dependent
//	        WebDriver ghostDriver = new PhantomJSDriver();
//	        try {
//	            ghostDriver.get(url);
//	            return Jsoup.parse(ghostDriver.getPageSource());
//	        } finally {
//	            ghostDriver.quit();
//	        }
//	    }
//
//	    public static Document renderPage(Document doc) {
//	        String tmpFileName = "$filePath${Calendar.getInstance().timeInMillis}.html";
//	        FileUtils.writeToFile(tmpFileName, doc.toString());
//	        return renderPage(tmpFileName);
//	    }


	public static void searchForTME(String query) throws IOException {
		String url = "http://www.tme.eu/pl/katalog/#search=" + query.replaceAll(" ", "+")
				+ "&s_field=accuracy&s_order=DESC";
		Document doc = Jsoup.connect(url).ignoreContentType(true)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				.followRedirects(true).get();
		System.out.println(url);
		try (PrintWriter out = new PrintWriter("C:\\Users\\Daniel\\Desktop\\zadanie8\\zadanie8\\filename.txt")) {
			out.println(doc);
		}
		// #products > tbody > tr:nth-child(1)
		Elements checkExist = doc.select("#products > tbody > tr");
		System.out.println(checkExist.size());
		int i = 0;
		for (Element e : checkExist) {
			String price = e.select("#C_63138_ > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > nobr > span")
					.first().text();
			String name = e.select("#products > tbody > tr:nth-child(1) > td.product > div > span").text();
			String availability = e.select(".content_price .availability").first().text();
			String link = e.select("a").first().attr("href");
			rowList.add((new Object[] { name, link, price, availability }));
		}
	}

	public static void searchForBOTland(String query) throws IOException {

		String url = "https://botland.com.pl/szukaj?controller=search&orderby=position&orderway=desc&search_query="
				+ query.replaceAll(" ", "+") + "&submit_search=";
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();
		Elements checkExist = doc.select("#product_list > li");
		System.out.println(checkExist.size());
		for (Element e : checkExist) {
			String price = e.select(".content_price .price").first().text().replace(" z³", "").replace(",", ".");
			float price2 = Float.parseFloat(price);
			String name = e.select("a").text();
			String availability = e.select(".content_price .availability").first().text();
			String link = e.select("a").first().attr("href");
			if(name.contains(query)){
				rowList.add((new Object[] { name, link, price2, availability }));
			}
		}
	}

	public static void searchForKamami(String query) throws IOException {
		String url = "https://kamami.pl/szukaj?controller=search&orderby=position&orderway=desc&search_query="
				+ query.replaceAll(" ", "+") + "&submit_search=";
		System.out.println(url);

		Document doc = Jsoup.connect(url).get();
		Elements checkExist = doc.select(".product_list > li");
		System.out.println(checkExist.size());
		for (Element e : checkExist) {
			String price = e.select(".product-container .content_price .price").text();
			price = price.split(" ", 2)[0].replace(",", ".");
			if (price.isEmpty()) {
				price = "0";
			}
			float price2 = Float.parseFloat(price);

			String name = e.select(".product-container .product-name").text();
			String availability = "brak informacji na stronie";
			String link = e.select(".product-container .product-name").first().attr("href");
//			if(name.contains(query)){
//				rowList.add((new Object[] { name, link, price2, availability }));
//			}
			rowList.add((new Object[] { name, link, price2, availability }));

		}
	}
	public static void saveToFile(String string) throws FileNotFoundException, UnsupportedEncodingException{
	    PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
	    writer.println(string);
	    writer.close();
	}

	public static void searchForAllegro(String query) throws IOException, ParseException {
		// https://allegro.pl/listing?string=samsung%20galaxy%20s5&order=m&

		//String html = Jsoup.connect("https://allegro.pl/listing?string=samsung%20galaxy%20s7&order=m&bmatch=base-relevance-floki-5-nga-ele-1-2-0511").get().html();
		
//		URL akt=new URL("https://allegro.pl/listing?string=samsung%20galaxy%20s7&order=m&bmatch=base-relevance-floki-5-nga-ele-1-2-0511");
//		URLConnection hc=akt.openConnection();
//		
//		BufferedReader in = new BufferedReader(new InputStreamReader(akt.openStream(), "utf-8"));
//		String linia;
//		String source = "";
//		while ((linia = in.readLine()) != null) {
//			source += linia+"\n";
//		}
//		in.close();
//		saveToFile(source);
//		WebDriver driver = new ChromeDriver();
//		ChromeOptions options = new ChromeOptions();
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Daniel\\Desktop\\EDwI\\chromedriver.exe");
//		  driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		

		outerloop: for(int i=1;i<2;i++){
			String url = "https://allegro.pl/listing?string=" + query.replaceAll(" ", "%20")
			//+ "&order=m&bmatch=base-relevance-floki-5-nga-ele-1-2-0511";
			+ "&order=m&bmatch=base-relevance-floki-5-nga-ele-1-2-0614&p="+i;
			Document doc= Jsoup.connect(url)
				    .header("Accept-Encoding", "gzip, deflate")
				    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
				    .maxBodySize(0)
				    .timeout(600000)
				    .get();
			
			saveToFile(doc.toString());
//			
//			  driver.navigate().to(url);
//		        String html = driver.getPageSource();
//				saveToFile(html);
//
//				driver.close();
//				driver.quit();
			        
		Elements checkExist = doc.select(".fa72b28");
		System.out.println(url);
		System.out.println(checkExist.size());
		for (Element e : checkExist) {

			// String price = e.select(".fa72b28 ._7765cc3 .exp-sa .e4865f5
			// span// strong").text();
			// System.out.println(price);
			// String price = e.select("._7765cc3 .ae47445 .d881929
			// .e82f23a").toString();
			//stare*************************************************************
			String name = e.select(".fa72b28 ._433675f h2").text();
			System.out.println(name);
			String availability = "dostêpny";
			String link = e.select("._433675f > h2 > a").first().attr("href");
			
//			String name = e.select(".c33f1ee > section:nth-child(2) .fa72b28 ._433675f h2").text();
//			String availability = "dostêpny";
//			String link = e.select(".c33f1ee > section:nth-child(2) ._61aa5c3 > h2 > a").first().attr("href");

			Document doc2 = Jsoup.connect(link).get();
			String price = doc2.select("#purchase-form > div.col-sm-7.col-xs-12 > div:nth-child(1) > div.price").text()
					.replaceAll("&nbsp;", "").trim();

			if (price.isEmpty()) {
				price = doc2.select("#bidding-form > div.col-xm-6.col-ss-7.col-sm-7.col-md-7 > div:nth-child(1)").text()
						.replaceAll("&nbsp;", "").trim();
			}
			price = regex1(price);

			float price2 = Float.parseFloat(price);
			if((name.toLowerCase()).contains(query)){
				rowList.add((new Object[] { name, link, price2, availability }));
			}
			if(rowList.size()>5)
				break outerloop;
			System.out.println(name);
		}
		}
	}

	public static String regex1(String s) {
		String s2 = "";
		s2 = s2.trim();
		s2 = s.replace(",", ".");
		s2 = s2.replaceAll("\\s+", "");
		s2 = s2.replaceAll("\u00a0", "");
		s2 = s2.replaceAll("z³", "");
		return s2;
	}

	//
	// public static void searchForAllegro(String query) throws IOException {
	// String url = "https://allegro.pl/listing?string=" + query.replaceAll(" ",
	// "%20");
	// Document doc = Jsoup.connect(url).get();
	// Elements checkExist = doc.select(".fa72b28");
	// System.out.println(url);
	// System.out.println(checkExist.size());
	// for (Element e : checkExist) {
	//
	// // String price = e.select(".fa72b28 ._7765cc3 .exp-sa .e4865f5 span
	// // strong").text();
	// // System.out.println(price);
	// // String price = e.select("._7765cc3 .ae47445 .d881929
	// // .e82f23a").toString();
	// String name = e.select(".fa72b28 ._433675f h2").text();
	// String availability = "dostêpny";
	// String link = e.select("._433675f > h2 > a").first().attr("href");
	// Document doc2 = Jsoup.connect(link).get();
	// String price = doc2.select("#purchase-form > div.col-sm-7.col-xs-12 >
	// div:nth-child(1) > div.price").text().replace(" z³", "").replace(",",
	// ".");
	//
	// if (price.isEmpty()) {
	// price = doc2
	// .select("#bidding-form > div.col-xm-6.col-ss-7.col-sm-7.col-md-7 >
	// div:nth-child(1) > div.price.below")
	// .text().replace(" z³", "").replace(",", ".");
	//
	// }
	// float price2=Float.parseFloat(price);
	// rowList.add((new Object[] { name, link, price2, availability }));
	// }
	// }

	private static ArrayList<Object[]> sort(ArrayList<Object[]> tab) {
		Object[] temp;
		int temp2 = 1;
		while (temp2 > 0) {
			temp2 = 0;
			for (int i = 0; i < tab.size() - 1; i++) {
				if ((float) tab.get(i)[2] > (float) tab.get(i + 1)[2]) {
					temp = tab.get(i + 1);
					tab.set(i + 1, tab.get(i));
					tab.set(i, temp);
					temp2++;
				}
			}
		}
		for (int i = 0; i < tab.size(); i++) {
			System.out.println(tab.get(i)[2]);
		}
		return tab;
	}

	/**
	 * Creates new form NewJFrame
	 */
	public NewJFrame() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jCheckBox1 = new javax.swing.JCheckBox();
		jCheckBox2 = new javax.swing.JCheckBox();
		jCheckBox3 = new javax.swing.JCheckBox();
		jCheckBox4 = new javax.swing.JCheckBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jButton1.setText("Szukaj");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton1ActionPerformed(evt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Nazwa", "Adres URL", "Cena", "Dostêpnoœæ" }));
		jScrollPane1.setViewportView(jTable1);

		jCheckBox1.setText("Allegro");

		jCheckBox2.setText("Kamami");
		jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox2ActionPerformed(evt);
			}
		});

		jCheckBox3.setText("TME");

		jCheckBox4.setText("Botland");
		model = (DefaultTableModel) jTable1.getModel();

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 654,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(13, 13, 13).addComponent(jCheckBox2)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jCheckBox1).addGap(4, 4, 4).addComponent(jCheckBox4)
										.addGap(18, 18, 18).addComponent(jCheckBox3)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(43, 43, 43)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton1).addComponent(jCheckBox1).addComponent(jCheckBox4)
								.addComponent(jCheckBox2).addComponent(jCheckBox3))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(33, Short.MAX_VALUE)));


		jTable1.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
					openWebpage(rowList.get(jTable1.getSelectedRow())[1].toString());
			}
		});
		
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	

	private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBox2ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jCheckBox2ActionPerformed

	// button search
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {
		System.out.println(jTextField1.getText());

		model.setRowCount(0);
		rowList.clear();
		if (jCheckBox1.isSelected()) {
			try {
				searchForAllegro(jTextField1.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (jCheckBox2.isSelected()) {
			try {
				searchForKamami(jTextField1.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (jCheckBox3.isSelected()) {
			try {
				searchForTME(jTextField1.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (jCheckBox4.isSelected()) {
			try {
				searchForBOTland(jTextField1.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sort(rowList);
		for (Object[] row : rowList) {
			model.addRow(row);
		}
	}// GEN-LAST:event_jButton1ActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	
	public static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewJFrame().setVisible(true);
			}
		});

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JCheckBox jCheckBox3;
	private javax.swing.JCheckBox jCheckBox4;
	private javax.swing.JScrollPane jScrollPane1;
	private static javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;
	// End of variables declaration//GEN-END:variables

}
