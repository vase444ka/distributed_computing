package lab4;

import com.entities.City;
import com.entities.Country;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServer extends Remote {
    public Country countryFindById(Long id) throws RemoteException;

    public City cityFindByName(String name) throws RemoteException;

    public Country countryFindByName(String name) throws RemoteException;

    public boolean cityUpdate(City city) throws RemoteException;

    public boolean countryUpdate(Country country) throws RemoteException;

    public boolean cityInsert(City city) throws RemoteException;

    public boolean countryInsert(Country country) throws RemoteException;

    public boolean countryDelete(Country country) throws RemoteException;

    public boolean cityDelete(City city) throws RemoteException;

    public List<Country> countryAll() throws RemoteException;

    public List<City> cityAll() throws RemoteException;

    public List<City> cityFindByCountryId(Long idc) throws RemoteException;
}
