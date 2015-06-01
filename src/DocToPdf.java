import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;



public class DocToPdf {

	public static void main(String[] args) {
		try {
			generateDoc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generateDoc() throws IOException {
		
		ByteArrayOutputStream ba=new ByteArrayOutputStream();
		InputStream in = DocToPdf.class.getResourceAsStream("/template.docx");
		XWPFDocument document = null;
		try {
			document = new XWPFDocument(in);
		} catch(Exception e) {
			e.printStackTrace();
		}
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		StringBuilder sbNormal = new StringBuilder();
		sbNormal.append("Nargis Dutt (1 June 1929 – 3 May 1981), born Fatima Rashid but known by her screen name, Nargis,[1] was an Indian film actress working in the Hindi cinema. She made her screen debut as a child artist in Talash-E-Haq in 1935, but her acting career began in 1942 with Tamanna. During a career from the 1940s to the 1960s, Nargis appeared in numerous commercially successful as well as critically appreciated films, many of which featured her alongside actor and filmmaker Raj Kapoor .");
		sbNormal.append("One of the best-known roles of Nargis was that of Radha in the Academy Award-nominated film Mother India (1957), a performance that won her Best Actress trophy at the Filmfare Awards. In 1958, she married her Mother India co-actor Sunil Dutt, and left the film industry. She would appear infrequently in films during the 1960s. Some of her films of this period include the drama Raat Aur Din (1967), for which she was given the inaugural National Film Award for Best Actress.");
		run.setText(sbNormal.toString());
		
		document.write(ba);
		String docPath = "/home/adeel/";
		FileOutputStream fos = new FileOutputStream(new File(docPath+"anuroop" +'_' +new Date().toString()+ ".docx"));
		ba.writeTo(fos);
		fos.close();
	}
}
