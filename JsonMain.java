class JsonParser{
	int i;
	String s;
	boolean match(char c){
		if(s.charAt(i)==' ')
			i++;
		if(s.charAt(i)==c){
			i++;
			return true;
		}
		return false;
	}
	boolean Object(){
		if(s.charAt(i)=='{' || s.charAt(i)==' '){
			if(match('{')){
				if(s.charAt(i)=='}' || s.charAt(i)==' '){
					match('}');
					return true;
				}		
				else{
					System.out.println("inside else");
					if(Members()){
						System.out.println("inside Object members");
						return(match('}'));
					}
				}			
			}	 	
		}
		return false;
	}
	
	boolean Members(){
					System.out.println("inside members");
		return Pair();
	}
	
	boolean Pair(){
					System.out.println("inside pair");
		if(string()){
					System.out.println("inside pair string"+s.charAt(i));
			if(s.charAt(i)==':'|| s.charAt(i)==' '){
					System.out.println("inside pair :");
				if(match(':'))
					return value();
			}			
		}	
		
		return false;
	}
	boolean value(){
		return string();
	}
	boolean string(){
		if(s.charAt(i)=='"'|| s.charAt(i)==' '){
			if(match('"')){
				if(chars())
					return match('"');
			}
		}	
		return false;
	}
	
	boolean chars(){
				System.out.println("inside chars");
		if(s.charAt(i)=='"'|| s.charAt(i)==' ')
			return true;
		if(character())
			return chars();
		return false;	
	}
	boolean character(){
		if(s.charAt(i)>='A'&& s.charAt(i)<='Z'){
			i++;
			return true;
		}	
		return false;	
	}
	public void validator(String s){
		i=0;
		this.s=s;
		if(Object())
			System.out.println("String is valid");
		else
			System.out.println("String is not valid");
	}	
}
class JsonMain{
	public static void main(String[] args){
			JsonParser JP = new JsonParser();
			JP.validator("{\" AAA \" :\"SQQQ\"}");		
	}	
}
