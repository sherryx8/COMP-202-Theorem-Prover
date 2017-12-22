import java.io.*;
import java.util.*;

//package TheoremProver.COMP-202-Theorem-Prover;

public class PropositionalParser{
  
  //helper function to determine if a character is an operator
  public boolean isOperator(char c){
    if (c == 'v' || c == '^' || c == '>'){
      return true;
    }
    return false;
  }
  
  //helper function to determine if a string is a proposition
  public boolean isProposition(String g){
    char c = g.charAt(0);
    if ((c == 'p' || c == 'q' || c == 'r')&&(g.length() ==1)){
      return true;
    }
    return false;  
  }
  
  //helper function to determine if string is a negation
  public boolean isNegation(String g){
    //must follow format "- frmla"
    if (g.charAt(0) == '-'){
      return (parse(g.substring(1)) > 0);
    }
    else{
      return false;
    }
  }
  
  //helper function to determine string is a binary formula
  public boolean isBinary(String g){
    //must follow format "(fmla BC fmla)"
    if((g.charAt(0) != '(') || (g.charAt(g.length()-1) != ')')){
      return false;
    }
    int operator = findOperator(g);
    if(operator < 0){
      return false;
    }
    
    //split into part A and B
    String a = g.substring(1, operator);
    String b = g.substring(operator+1, g.length()-1);
    //check that a and b are formulas
    return (parse(a)>0) && (parse(b)>0);
  }
  
  //helper function to find middle operator location
   public int findOperator(String g){
    //find operator, operator must be at correct parans depth
    int operator = -1;
    int depth = 0;
    for(int i = 0; i < g.length(); i++){
      if(g.charAt(i) == '('){
        depth++;
      }
      if(g.charAt(i) == ')'){
        depth--;
      }
      if((isOperator(g.charAt(i))) && (depth == 1)){
        operator = i;
        break;
      }
    }
    
    //no operator found in correct location
    if((operator <= 1) || (operator >= g.length()-2)){
      operator = -1;
    }
    
    return operator; 
  }
  
  
  //returns 1 if a proposition, 2 if neg, 3 if binary, otherwise 0 
  public int parse(String g){
    //if string is empty/null, it is not a formula
    if((g == null) || (g.equals(""))){
      return 0;
    }
    //System.out.println("currently checking if " + g + " is a formula");
    int curr = 0;
    char c = g.charAt(curr); 
    
    
    //if string is a proposition
    if (isProposition(g)){
      return 1;
    }
    //if string is binary
    else if (isBinary(g)){
      return 3;
    }
    //if string is a negation
    else if (isNegation(g)){
      return 2;
    }
    //if is not a negation , binary formula or proposition it is not valid
    else{
      //System.out.println(g + " is not a formula");
      return 0;
    }
  }
  
  //given a binary formula, find first half
  public String splitBinaryFormulaA(String s){
    int o = findOperator(s);
    return s.substring(1, o);
    
  }
  
  //given a binary formula, find second half
  public String splitBinaryFormulaB(String s){
    int o = findOperator(s);
    return s.substring(o+1, s.length()-1);
  }
  
  //given formula, use parse to determine string result 
  public String getresult(String s){
    int x = parse(s);
    String res = " is not a formula";
    switch(x)
    {
      case(0): res = s + " is not a formula"; break;
      case(1): res = s + " is a proposition"; break;
      case(2): res = s + " is a negation"; break;
      case(3): res = s + " is a binary formula"; break;
      default: res = s + " is not a formula"; break;
    }
    //if it is a binary formula, find the two parts as well
    if (x == 3){
      String a = splitBinaryFormulaA(s);
      String b = splitBinaryFormulaB(s);
      res += " (First half is: " + a + " Second half is: " + b + ")";
    }
    return res;
  }
  
  
  
  public static void main(String[] args){
    PropositionalParser p = new PropositionalParser();
    
    if(args.length > 0){ //if command line argumenst are given
      for(int i = 0; i < args.length; i++){
        int m = p.parse(args[i]);
        System.out.println(p.getresult(args[i]));
      }
      
    }
    else{
      try{ //else use input.txt file
        File input = new File("input.txt");
        //File output = new File("output.txt");
        Scanner inputScanner = new Scanner(input);
        PrintWriter printWriter = new PrintWriter(new FileWriter("output.txt"));
        
        while(inputScanner.hasNext()){
          String g = inputScanner.nextLine();
          String result = p.getresult(g);
          printWriter.println(result);
          System.out.println(result);
        }
        printWriter.close();
        inputScanner.close();
      }
      catch(Exception e){ //use defaults if input file cannot be found
        System.out.println(e);
        System.out.println(" input file not found, using defaults:");
        String[] defaults = {"(pvq)", "((p>q)v-p)", "-(p^q)v-(p>q)", "((p^--q)>(-qv-p))", "---p", "((pvq)>(-p>--q))", "-(p>(q>p))", "(pv-q)", "-(pv-p)", "((p^--q)>(-qv-p))", "-pvq"};
        for (int j = 0; j < defaults.length; j++){
          System.out.println(p.getresult(defaults[j]));
        }
      }
    }
    return;
  }
}

