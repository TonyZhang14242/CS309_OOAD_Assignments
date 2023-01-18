import dao.ComputerDao;
import dao.DaoFactory;
import dao.StaffDao;

import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory {
    public ComputerDao computerDao;
    public StaffDao staffDao;
    public Properties properties = PropertiesReader.readProperties("./src/resources.properties");
    @Override
    public ComputerDao createComputerDao() {
        try {
            String clz = properties.getProperty("ComputerDao");
            Class<?> clzz = Class.forName("dao."+clz);
            computerDao = (ComputerDao)clzz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return computerDao;
    }

    @Override
    public StaffDao createStaffDao() {
        try {
            String clz = properties.getProperty("StaffDao");
            Class<?> clzz = Class.forName("dao."+clz);
            staffDao = (StaffDao)clzz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return staffDao;

    }
}
