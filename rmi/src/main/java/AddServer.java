import java.rmi.Naming;

/**
 * Created by Юрий on 04.09.2016.
 */
public class AddServer {
  public static void main (String args[]){
    try {
      AddServerImpl addServerImpl = new AddServerImpl();
      Naming.rebind("AddServer", addServerImpl);
    }
    catch(Exception e) {
      System.out.println("Exception; " + e);
    }
  }
}

