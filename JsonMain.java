import java.io.*;
class JsonParser{
	int i;
	String s;
	boolean Match(char c){
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
			if(Match('{')){
				if(s.charAt(i)=='}' || s.charAt(i)==' '){
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
			if(s.charAt(i)==',' || s.charAt(i)==' ') {
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
			if(s.charAt(i)==':'|| s.charAt(i)==' '){
					System.out.println("inside pair :");
				if(Match(':'))
					return Value();
			}			
		}	
		
		return false;
	}
	boolean Value(){
		return string();
	}
	boolean string(){
		if(s.charAt(i)=='"'|| s.charAt(i)==' '){
			if(Match('"')){
				if(Chars())
					return Match('"');
			}
		}	
		return false;
	}
	
	boolean Chars(){
				System.out.println("inside chars");
		if(s.charAt(i)=='"'|| s.charAt(i)==' ')
			return true;
		if(Character())
			return Chars();
		return false;	
	}
	boolean Character(){
		if(s.charAt(i)>='A'&& s.charAt(i)<='Z'){
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
		if(s.charAt(i)==',' || s.charAt(i)==' '){
			Match(',');
			return Elements();
		}
		return flag;	
	}
	public void Validator(String s){
		i=0;
		this.s=s;
		boolean flag=false;
		if(s.charAt(i)==' ')
			i++;
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
		if(i<s.length() || flag==false)
			System.out.println("String is not valid");	
		else 
			System.out.println("String is valid");
	}	
}
class JsonMain{
	public static void main(String[] args){
			JsonParser JP = new JsonParser();
			JP.Validator(" {\"AAA\":\"QQQ\" , \"WW\":\"WW\" , \"\":\"QQ\"}");		
	}	
}
