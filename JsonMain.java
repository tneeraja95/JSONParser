import java.io.*;
class JsonParser{
	int i;
	String s;
	void eliminateSpace(){
		System.out.println("in eliminate space i="+i+" length="+s.length());
		
		while( i<s.length() && s.charAt(i)==' '){
			System.out.println("in while i="+i);
			i++;
		}	
	}
	boolean Match(char c){
		if(s.charAt(i)==c){
			i++;
			return true;
		}
		return false;
	}
	boolean Object(){
		if(s.charAt(i)=='{'){
			if(Match('{')){
				eliminateSpace();
				if(s.charAt(i)=='}'){
					Match('}');
					return true;
				}		
				else{
					System.out.println("inside else");
					if(Members()){
						System.out.println("inside Object members");
						return(Match('}'));
					}
				}			
			}	 	
		}
		return false;
	}
	
	boolean Members(){
					System.out.println("inside members");
		if(Pair()){
			eliminateSpace();
			if(s.charAt(i)==',') {
				Match(',');
				return Members(); 	
			}
			else
				return true;
		}
		return false;
	}
	
	boolean Pair(){
					System.out.println("inside pair");
		if(string()){
					System.out.println("inside pair string"+s.charAt(i));
			eliminateSpace();		
			if(s.charAt(i)==':'){
					System.out.println("inside pair :");
				if(Match(':'))
					return Value();
			}			
		}	
		
		return false;
	}
	boolean Value(){
		eliminateSpace();
		if(s.charAt(i)=='"')
			return string();
		else if(s.charAt(i)>='0' && s.charAt(i)<='9'){
			return Digits();
		}
		else if(s.charAt(i)=='t' || s.charAt(i)=='f'){
			return Bool();
		}
		else if(s.charAt(i)=='[')
			return Array();
		else if(s.charAt(i)=='{')
			return Object();
		else if(s.charAt(i)=='n')
			return Null();				
		return false;
	}
	boolean string(){
		System.out.println("Inside Strings");
		if(Match('"')){
			if(Chars())
				return Match('"');
			}		
		return false;
	}
	
	boolean Chars(){
				System.out.println("inside chars");
		eliminateSpace();
		if(s.charAt(i)=='"')
			return true;
		if(Character())
			return Chars();
		return false;	
	}
	boolean Character(){
			i++;
			return true;	
	}
	
	boolean Digits(){
	System.out.println("Inside digits");
	eliminateSpace();
		if(s.charAt(i)=='}'|| s.charAt(i)==',')
			return true;
		if(Digit())
			return Digits();
		return false;		
	}
	boolean Digit(){
		System.out.println("Inside digit");
		if(s.charAt(i)>='0' && s.charAt(i)<='9'){
				i++;
				return true;
		}
		return false;
	}
	
	boolean Bool(){
		System.out.println("Inside bool");

		if(s.charAt(i)=='t'){
			i++;
			if(s.charAt(i)!='r')
				return false;
			i++;	
			if(s.charAt(i)!='u')
				return false;
			i++;				
			if(s.charAt(i)!='e')
				return false;
			i++;	
			return true;			
		}
		if(s.charAt(i)=='f'){
			i++;
			if(s.charAt(i)!='a')
				return false;
			i++;
			if(s.charAt(i)!='l')
				return false;
			i++;
			if(s.charAt(i)!='s')
				return false;
			i++;
			if(s.charAt(i)!='e')
				return false;
			i++;
			return true;			
		}
		return false;
	}

	boolean Null(){
	
		if(s.charAt(i)=='n'){
				i++;
			if(s.charAt(i)!='u')
				return false;
			i++;	
			if(s.charAt(i)!='l')
				return false;
			i++;				
			if(s.charAt(i)!='l')
				return false;
			i++;	
			return true;			
		}
		return false;
	}
	boolean Array(){
		if(s.charAt(i)=='['){
			System.out.println("Inside array if");
			Match('[');
			if(s.charAt(i)==']')
				return(Match(']'));
			else
				if(Elements())
					return Match(']');	
		}
		return false;
	}
	
	boolean Elements(){
		boolean flag;
		flag=Value();
		if(flag==false)
			return flag;
		eliminateSpace();	
		if(s.charAt(i)==','){
			Match(',');
			return Elements();
		}
		return flag;	
	}
	public void Validator(String s){
		i=0;
		this.s=s;
		boolean flag=false;
		eliminateSpace();
		if(s.charAt(i)=='{'){
			if(Object())
				flag=true;
			}
		else if(s.charAt(i)=='['){
			if(Array())
				flag=true;
		}	
		if(flag==true)
		System.out.println("returned true i="+i+" length="+s.length());
		eliminateSpace();
		if(i<s.length() || flag==false)
			System.out.println("String is not valid");	
		else 
			System.out.println("String is valid");
	}	
}
class JsonMain{
	public static void main(String[] args){
			JsonParser JP = new JsonParser();
		//	JP.Validator(" {\"glossary\": {\"title\": \"example glossary\",\"GlossDiv\": {\"title\": \"S\",\"GlossList\": {\"GlossEntry\": { \"ID\": \"SGML\",\"SortAs\": \"SGML\",\"GlossTerm\": \"Standard Generalized Markup Language\",\"Acronym\": \"SGML\",\"Abbrev\": \"ISO 8879:1986\",\"GlossDef\": {\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\": [\"GML\", \"XML\"]},\"GlossSee\": \"markup\"}}}}}");		
	JP.Validator("{    \"AAA\": []}     ");
	}	
}
