package lab4;

import com.db.CityDAO;
import com.db.CountryDAO;
import com.entities.City;
import com.entities.Country;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMIServerLab4 extends UnicastRemoteObject implements RMIServer {

    protected RMIServerLab4() throws RemoteException {
        super();
    }

    @Override
    public Country countryFindById(Long id) throws RemoteException {
        return CountryDAO.findById(id);
    }

    @Override
    public City cityFindByName(String name) throws RemoteException {
        return CityDAO.findByName(name);
    }

    @Override
    public Country countryFindByName(String name) throws RemoteException {
        return CountryDAO.findByName(name);
    }

    @Override
    public boolean cityUpdate(City city) throws RemoteException {
        return CityDAO.update(city);
    }

    @Override
    public boolean countryUpdate(Country country) throws RemoteException {
        return CountryDAO.update(country);
    }

    @Override
    public boolean cityInsert(City city) throws RemoteException {
        return CityDAO.insert(city);
    }

    @Override
    public boolean countryInsert(Country country) throws RemoteException {
        return CountryDAO.insert(country);
    }

    @Override
    public boolean countryDelete(Country country) throws RemoteException {
        return CountryDAO.delete(country);
    }

    @Override
    public boolean cityDelete(City city) throws RemoteException {
        return CityDAO.delete(city);
    }

    @Override
    public List<Country> countryAll() throws RemoteException {
        return CountryDAO.findAll();
    }

    @Override
    public List<City> cityAll() throws RemoteException {
        return CityDAO.findAll();
    }

    @Override
    public List<City> cityFindByCountryId(Long countryId) throws RemoteException {
        return CityDAO.findByCountryId(countryId);
    }

    public static void main(String[] args) throws RemoteException {
        var backend = new RMIServerLab4();
        Registry r = LocateRegistry.createRegistry(8085);
        r.rebind("worldmap24", backend);
    }
}
