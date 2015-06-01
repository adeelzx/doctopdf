import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.model.ConvertAnchor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.docx4j.Docx4J;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;



public class DocToPdf {

	private File docx4jTempDirectory;
	
	
	
	public static void main(String[] args) {
		
			
				try {
					generateDoc();
				} catch ( Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
	
	public static void generateDoc() throws Exception {
		
		StringBuilder sbNormal = new StringBuilder();
		sbNormal.append("Nargis Dutt (1 June 1929 – 3 May 1981), born Fatima Rashid but known by her screen name, Nargis,[1] was an Indian film actress working in the Hindi cinema. She made her screen debut as a child artist in Talash-E-Haq in 1935, but her acting career began in 1942 with Tamanna. During a career from the 1940s to the 1960s, Nargis appeared in numerous commercially successful as well as critically appreciated films, many of which featured her alongside actor and filmmaker Raj Kapoor .");
		sbNormal.append("One of the best-known roles of Nargis was that of Radha in the Academy Award-nominated film Mother India (1957), a performance that won her Best Actress trophy at the Filmfare Awards. In 1958, she married her Mother India co-actor Sunil Dutt, and left the film industry. She would appear infrequently in films during the 1960s. Some of her films of this period include the drama Raat Aur Din (1967), for which she was given the inaugural National Film Award for Best Actress.");
		
		String docPath = "/home/adeel/";
		FileOutputStream fos = new FileOutputStream(new File(docPath+"anuroop" +'_' +new Date().toString()+ ".pdf"));
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		
		
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "docx4j");
		wordMLPackage.getMainDocumentPart().addParagraphOfText(sbNormal.toString());
		File file = new File("plantData.png");
		java.io.InputStream is = new java.io.FileInputStream(file );
		long length = file.length(); 
		byte[] bytes = new byte[(int)length];
		
		 int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length
	               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	        // Ensure all the bytes have been read in
	        if (offset < bytes.length) {
	            System.out.println("Could not completely read file "+file.getName());
	        }
	        is.close();
		
		 String filenameHint = null;
	        String altText = null;
	        int id1 = 0;
	        int id2 = 1;
	        
		org.docx4j.wml.P p = newImage(wordMLPackage, bytes, filenameHint, altText, id1, id2);
		wordMLPackage.getMainDocumentPart().addObject(p);
		Docx4J.toPDF(wordMLPackage, fos);
	}
	
	public void testCreateADocx() {
	    WordprocessingMLPackage wordMLPackage = null;
	    try {
	      wordMLPackage = WordprocessingMLPackage.createPackage();
	    } catch (InvalidFormatException e) {
	      //fail(e.getMessage());
	    }

	    wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "docx4j");

	    wordMLPackage.getMainDocumentPart().addParagraphOfText("Test create a docx with docx4j");

	    try {
	      File docx = new File(docx4jTempDirectory, "testCreateADocx.docx");
	      wordMLPackage.save(docx);
	    } catch (Docx4JException e) {
	      e.printStackTrace();
	    }

	  }
	
	public static org.docx4j.wml.P newImage( WordprocessingMLPackage wordMLPackage,
			byte[] bytes,
			String filenameHint, String altText, 
			int id1, int id2) throws Exception {
		
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
	
        Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2 );
        
        // Now add the inline in w:p/w:r/w:drawing
		org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
		org.docx4j.wml.P  p = factory.createP();
		org.docx4j.wml.R  run = factory.createR();		
		p.getParagraphContent().add(run);        
		org.docx4j.wml.Drawing drawing = factory.createDrawing();		
		run.getRunContent().add(drawing);		
		drawing.getAnchorOrInline().add(inline);
		
		return p;
		
	}	

}
