import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Юрий on 04.09.2016.
 */
public class AddServerImpl extends UnicastRemoteObject
        implements AddServerIntf {

  public AddServerImpl()   throws RemoteException {
  }
  public double add (double d1, double d2) throws RemoteException {
    return d1 + d2 ;
  }
}

