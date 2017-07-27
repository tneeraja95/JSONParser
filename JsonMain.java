import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
enum enumException{
	Undefined_Symbol,
	Matching_Curly_Bracket_Not_Found,
	Not_a_Key,
	Missing_Colon,
	Not_a_Value,
	Matching_Box_Bracket_Not_Found,
	Unexpected_End_of_Line,
	Digit_Expected,
	Starting_Bracket_Not_Found
}

class customException extends Exception{
	customException(enumException e, int i){
	i=i+1;
	switch(e){
		case Matching_Curly_Bracket_Not_Found:System.out.println("Error : Matching Bracket '}' not found at Position "+i);
												break;
		case Not_a_Key:System.out.println("Error : Not a Valid Key at Position "+i);
												break;
		case Missing_Colon:System.out.println("Error : Missing Colon ':' at Position "+i);
												break;
		case Not_a_Value:System.out.println("Error : Not a Valid Value at Position "+i);
												break;
		case Matching_Box_Bracket_Not_Found:System.out.println("Error : Matching Bracket ']' not found at Position "+i );
												break;
		case Undefined_Symbol:System.out.println("Error : Undefined Symbol at Position "+i);
												break;
		case Unexpected_End_of_Line:System.out.println("Error: Unexpected end of file");
												break;
		case Digit_Expected:System.out.println("Error: Digit Expected "+i);										
												break;
		case Starting_Bracket_Not_Found:System.out.println("Error: Starting Bracket not found '[' or '{' expected  at Position "+i);
		}
	}
}
class JsonParser{
	int i;
	String s;

	void eliminateSpace(){
		while( i<s.length() && s.charAt(i)==' '){
			i++;
		}	
	}

	boolean Match(char c){
		if(i<s.length() && (s.charAt(i)==c)){
			i++;
			return true;
		}
		
		return false;
	}

	boolean Object() throws customException {
		enumException e = enumException.Matching_Curly_Bracket_Not_Found;
		if(s.charAt(i)=='{'){
			if(Match('{')){
				eliminateSpace();
				if(i==s.length())
					throw new customException(e,i);
				if(i<s.length() && s.charAt(i)=='}'){
					Match('}');
					eliminateSpace();
					return true;
				}		
				else{
					if(i<s.length() && Members()){
						eliminateSpace();
						if(Match('}'))
							return true;
						else{
							
							throw new customException(e,i);
						}
					}
				}
				throw new customException(e,i);
							
			}	 	
		}
		
		return false;
	}
	
	boolean Members()throws customException{
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
	
	boolean Pair()throws customException{
		if(string()){
			eliminateSpace();		
			if(i<s.length() && s.charAt(i)==':'){
				if(Match(':'))
					return Value();
				}	
			else{
				enumException e = enumException.Missing_Colon;	
				throw new customException(e,i);
			}	
		}
		enumException e = enumException.Not_a_Key;	
		throw new customException(e,i);		
	}

	boolean Value()throws customException{
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
		enumException e = enumException.Not_a_Value;
		throw new customException(e,i); 	
		
	}

	boolean string()throws customException{
		if(Match('"')){
			if(Chars()){	
				if( Match('"'))
					return true;				
			}		
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
	
	boolean Digits()throws customException{
	eliminateSpace();
		if(i<s.length() && (s.charAt(i)=='}'|| s.charAt(i)==','))
			return true;
		if(i<s.length() && Digit())
			return Digits();
		return false;		
	}

	boolean Digit()throws customException{
		if(s.charAt(i)>='0' && s.charAt(i)<='9'){
				i++;
				return true;
		}
		enumException e = enumException.Digit_Expected;
		throw new customException(e,i);
		
	}
	
	boolean Bool() throws customException {
		if(s.charAt(i)=='t'){
			i++;
			if(s.charAt(i)=='r'){
								i++;	
								if(s.charAt(i)=='u'){
													i++;				
													if(s.charAt(i)=='e'){
																		i++;	
																		return true;			
													}
								}
			}
		enumException e = enumException.Undefined_Symbol;
		throw new customException(e,i);
		}

		if(s.charAt(i)=='f'){
							i++;
							if(s.charAt(i)=='a'){
												i++;
												if(s.charAt(i)=='l'){
																	i++;
																	if(s.charAt(i)=='s'){
																						i++;
																						if(s.charAt(i)=='e'){
																											i++;
																										return true;			
																						}
																	}
												}
							}
		}
		enumException e = enumException.Undefined_Symbol;
		throw new customException(e,i);		
	}

	boolean Null() throws customException {	
		if(s.charAt(i)=='n'){
				i++;
				if(s.charAt(i)=='u'){
									i++;	
									if(s.charAt(i)=='l'){
														i++;				
														if(s.charAt(i)=='l'){
																			i++;	
																			return true;		
														}
									}
				}	
		}
		enumException e = enumException.Undefined_Symbol;
		throw new customException(e,i);
	}

	boolean Array()throws customException{
		if(i<s.length() && s.charAt(i)=='['){
			Match('[');
			eliminateSpace();
			if(i<s.length() && s.charAt(i)==']')
				return(Match(']'));
			else
				if(Elements())
					return Match(']');	
		}
		enumException e = enumException.Matching_Box_Bracket_Not_Found;
		throw new customException(e,i);
	}
	
	boolean Elements()throws customException{
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

	public void Validator(String s) throws customException{
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
			else{
				enumException e1 = enumException.Starting_Bracket_Not_Found; 
					throw new customException(e1, i);					
			}
			if(flag==true)
				eliminateSpace();
			if(i<s.length() || flag==false){
				enumException e = enumException.Unexpected_End_of_Line; 
					throw new customException(e, i);	
			}		
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
			}
			catch (customException e){}
			catch(IOException e){e.printStackTrace();}
			finally {
				if(br != null)
					br.close();
			}
	}		
		
}
