
public class EnumOpr {

	
	public enum QuestionTypeEnum 
	{
		FB, 
		QA1L,
		QASH,
		QALG,
		QA,
		COMP,
		TBL,
		QA1W,
		TF;

	}

	static QuestionTypeEnum questionType = QuestionTypeEnum.FB;
	
	private static final String displayViewName = questionType.toString() ;
	
	public static void main(String[] args) {
		System.out.println(displayViewName);
	}
}
