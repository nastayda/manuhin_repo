import java.rmi.Naming;

/**
 * Created by Юрий on 04.09.2016.
 */
public class AddClient {
  public static void main(String args[]) {
    try {
      String addServerURL = "rmi://" + args[0] + "/AddServer";
      AddServerIntf addServerIntf =
              (AddServerIntf) Naming.lookup(addServerURL);
      System.out.println("The first number is: " + args[1]);
      double dl = Double.valueOf(args[1]).doubleValue();
      System.out.println("The second number is: " + args[2]);
      double d2 = Double.valueOf(args[2]).doubleValue();
      System.out.println("The sum is: " + addServerIntf.add(dl, d2));
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}

