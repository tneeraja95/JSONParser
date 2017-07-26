import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
class JsonParser{
	int i;
	String s;

	void eliminateSpace(){
		while( i<s.length() && s.charAt(i)==' '){
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
				if(i<s.length() && s.charAt(i)=='}'){
					Match('}');
					eliminateSpace();
					return true;
				}		
				else{
					if(i<s.length() && Members()){
						eliminateSpace();
						return(Match('}'));
					}
				}			
			}	 	
		}
		return false;
	}
	
	boolean Members(){
		if(i<s.length() && Pair()){
			eliminateSpace();
			if(i<s.length() && s.charAt(i)==',') {
				if(Match(',')){
					eliminateSpace();
					return Members();
				} 	
			}
			else
				return true;
		}
		return false;
	}
	
	boolean Pair(){
		if(string()){
			eliminateSpace();		
			if(i<s.length() && s.charAt(i)==':'){
				if(Match(':'))
					return Value();
			}			
		}	
		
		return false;
	}

	boolean Value(){
		eliminateSpace();
		if(i<s.length() && s.charAt(i)=='"')
			return string();
		else if(i<s.length() && s.charAt(i)>='0' && s.charAt(i)<='9'){
			return Digits();
		}
		else if(i<s.length() && s.charAt(i)=='t' || s.charAt(i)=='f'){
			return Bool();
		}
		else if(i<s.length() && s.charAt(i)=='[')
			return Array();
		else if(i<s.length() && s.charAt(i)=='{')
			return Object();
		else if(i<s.length() && s.charAt(i)=='n')
			return Null();				
		return false;
	}

	boolean string(){
		if(Match('"')){
			if(Chars())
				return Match('"');
			}		
		return false;
	}
	
	boolean Chars(){
		eliminateSpace();
		if(i<s.length() && s.charAt(i)=='"')
			return true;
		if(i<s.length() && Character())
			return Chars();
		return false;	
	}

	boolean Character(){
			i++;
			return true;	
	}
	
	boolean Digits(){
	eliminateSpace();
		if(i<s.length() && (s.charAt(i)=='}'|| s.charAt(i)==','))
			return true;
		if(i<s.length() && Digit())
			return Digits();
		return false;		
	}

	boolean Digit(){
		if(s.charAt(i)>='0' && s.charAt(i)<='9'){
				i++;
				return true;
		}
		return false;
	}
	
	boolean Bool(){
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
		if(i<s.length() && s.charAt(i)=='['){
			Match('[');
			eliminateSpace();
			if(i<s.length() && s.charAt(i)==']')
				return(Match(']'));
			else
				if(Elements())
					return Match(']');	
		}
		return false;
	}
	
	boolean Elements(){
		if(i<s.length() && Value()){
			eliminateSpace();	
			if(i<s.length() && s.charAt(i)==','){
				Match(',');
				eliminateSpace();
				return Elements();
			}
			else
				return true;	
			
		}	
		return false;	
	}

	public void Validator(String s){
		i=0;
		this.s=s;
		boolean flag=false;
		if(s==null){
			System.out.println("Not a valid JSON Object");
		}	
		else{	
			eliminateSpace();
			if(i<s.length() && s.charAt(i)=='{'){
				if(Object())
					flag=true;
				}
			else if(i<s.length() && s.charAt(i)=='['){
				if(Array())
						flag=true;
			}		
			if(flag==true)
				eliminateSpace();
			if(i<s.length() || flag==false)
				System.out.println("Not a Valid JSON Object");	
			else 
				System.out.println("Valid JSON Object");
		}
	}	
}

class JsonMain{

	public static void main(String[] args) throws IOException{
			JsonParser JP = new JsonParser();
			BufferedReader br = null;
			try{
			br = new BufferedReader(new FileReader(args[0]));;
			String input = br.readLine();
			JP.Validator(input);
			}catch(IOException e){
				e.printStackTrace();	
			}finally {
				if(br != null)
					br.close();
			}
	}		
		
}
