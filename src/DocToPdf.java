import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;

import org.docx4j.Docx4J;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.PPrBase.Ind;



public class DocToPdf {

	private File docx4jTempDirectory;
	
	
	
	public static void main(String[] args) {
			
				try {
					generatePdf();
				} catch ( Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
	
	public static void generatePdf() throws Exception {
		
		StringBuilder sbNormal = new StringBuilder();
		sbNormal.append("Nargis Dutt (1 June 1929 – 3 May 1981), born Fatima Rashid but known by her screen name, Nargis,[1] was an Indian film actress working in the Hindi cinema. She made her screen debut as a child artist in Talash-E-Haq in 1935, but her acting career began in 1942 with Tamanna. During a career from the 1940s to the 1960s, Nargis appeared in numerous commercially successful as well as critically appreciated films, many of which featured her alongside actor and filmmaker Raj Kapoor .");
		sbNormal.append("One of the best-known roles of Nargis was that of Radha in the Academy Award-nominated film Mother India (1957), a performance that won her Best Actress trophy at the Filmfare Awards. In 1958, she married her Mother India co-actor Sunil Dutt, and left the film industry. She would appear infrequently in films during the 1960s. Some of her films of this period include the drama Raat Aur Din (1967), for which she was given the inaugural National Film Award for Best Actress.");
		
		String docPath = "/home/adeel/";
		FileOutputStream fos = new FileOutputStream(new File(docPath+"doctopdf" +'_' +new Date().toString()+ ".pdf"));
		WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.createPackage();
		
		wmlPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "docx4j");
		wmlPackage.getMainDocumentPart().addParagraphOfText(sbNormal.toString());
		File file = new File("plantData.png");
		InputStream is = new FileInputStream(file );
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
		
		 String filenameHint = "Microscope";
	        String altText = "Microscope";
	        int id1 = 0;
	        int id2 = 1;
	        
		P p = newImage(wmlPackage, bytes, filenameHint, altText, id1, id2);
		wmlPackage.getMainDocumentPart().addObject(p);
		
		PPr ppr = new PPr();
		Ind ind = new Ind();
		ind.setLeft(new BigInteger("567"));
		ppr.setInd(ind);
		p.setPPr(ppr);

		
		// #To create html content 
		//Docx4J.toHTML(wmlPackage, "/home/adeel/", "/home/adeel/", fos);

		
		Docx4J.toPDF(wmlPackage, fos);
	}
	
	public void testCreateADocx() {
	    WordprocessingMLPackage wordMLPackage = null;
	    try {
	      wordMLPackage = WordprocessingMLPackage.createPackage();
	    } catch (InvalidFormatException e) {
	     
	    }

	    wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Testing Title");
	    wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle", "Testing Subtitle");
	    wordMLPackage.getMainDocumentPart().addParagraphOfText("Test create a docx with docx4j");

	    try {
	      File docx = new File(docx4jTempDirectory, "testCreateADocx.docx");
	      wordMLPackage.save(docx);
	    } catch (Docx4JException e) {
	      e.printStackTrace();
	    }

	  }
	
	@SuppressWarnings("deprecation")
	public static P newImage( WordprocessingMLPackage wordMLPackage, byte[] bytes, String filenameHint,
			String altText, int id1, int id2) throws Exception {
		
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);
        inline.setDistL(1050L);
        // Now add the inline in w:p/w:r/w:drawing
		ObjectFactory factory = new ObjectFactory();
		P  p = factory.createP();
		R  run = factory.createR();
		p.getContent().add(run);  
		Drawing drawing = factory.createDrawing();		
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);
		
		return p;
		
	}	
}
