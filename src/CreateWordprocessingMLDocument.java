import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.docx4j.convert.out.flatOpcXml.FlatOpcXmlCreator;
import org.docx4j.jaxb.Context;
import org.docx4j.jaxb.NamespacePrefixMapperUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.samples.AbstractSample;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

public class CreateWordprocessingMLDocument extends AbstractSample {

	public static void main(String[] args) throws Exception {
		
		
		try {
			getInputFilePath(args);
		} catch (IllegalArgumentException e) {
	    	inputfilepath ="/home/adeel/CreateWordprocessingMLDocument_out.docx";	    	
		}
		
		boolean save = (inputfilepath == null ? false : true);
			
		
		System.out.println( "Creating package..");
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		
		wordMLPackage.getMainDocumentPart()
			.addStyledParagraphOfText("Title", "Hello world");

		wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");
		
		
		ObjectFactory factory = new ObjectFactory();
		P  p = factory.createP();
		PPr ppr = new PPr();
		Ind ind = new Ind();
		ind.setLeft(new BigInteger("567"));
		ppr.setInd(ind);
		p.setPPr(ppr);
		Text  t = factory.createText();
		t.setValue("text");

		org.docx4j.wml.R  run = factory.createR();
		run.getContent().add(t);		
		
		p.getContent().add(run);
		RPr rpr = factory.createRPr();		
		BooleanDefaultTrue b = new BooleanDefaultTrue();
	    b.setVal(true);	    
	    rpr.setB(b);
	    
		run.setRPr(rpr);
		
	            
	    wordMLPackage.getMainDocumentPart().addObject(p);

	    // Now save it
		if (save) {
			wordMLPackage.save(new java.io.File(inputfilepath) );
			System.out.println("Saved " + inputfilepath);
		} else {
		   	// Create a org.docx4j.wml.Package object
			FlatOpcXmlCreator worker = new FlatOpcXmlCreator(wordMLPackage);
			org.docx4j.xmlPackage.Package pkg = worker.get();
	    	
	    	// Now marshall it
			JAXBContext jc = Context.jcXmlPackage;
			Marshaller marshaller=jc.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			NamespacePrefixMapperUtils.setProperty(marshaller, 
					NamespacePrefixMapperUtils.getPrefixMapper());			
			System.out.println( "\n\n OUTPUT " );
			System.out.println( "====== \n\n " );	
			marshaller.marshal(pkg, System.out);				
			
		}
		
		System.out.println("Done.");
				
	}
	
	
}