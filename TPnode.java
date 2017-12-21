public class TPnode{
  boolean expanded;
  String phrase;
  TPnode parent;
  TPnode left;
  TPnode right;
  
  public TPnode(String s){
    this.expanded = false;
    this.phrase = s;
    this.left = null;
    this.right = null;
    this.parent = null;
  }
}
  
  
  