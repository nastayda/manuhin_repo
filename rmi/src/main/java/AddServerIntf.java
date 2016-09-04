import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Юрий on 04.09.2016.
 */
public interface AddServerIntf extends Remote {
  double add (double dl,  double d2)   throws RemoteException;
}

