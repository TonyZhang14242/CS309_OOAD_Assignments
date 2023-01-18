package task2;

import java.util.Comparator;
import java.util.List;

public class Adapter implements FileOperateInterfaceV2 {

    private FileOperateInterfaceV1 adaptee;

    private ManageStaffInterface adaptee2;


    public Adapter(FileOperateInterfaceV1 adaptee, ManageStaffInterface adaptee2) {
        this.adaptee = adaptee;
        this.adaptee2 = adaptee2;
    }

    @Override
    public List<StaffModel> readAllStaff() {
        return adaptee.readStaffFile();
    }

    @Override
    public void listAllStaff(List<StaffModel> list) {
        adaptee.printStaffFile(list);
    }

    @Override
    public void writeByName(List<StaffModel> list) {
        list.sort(Comparator.comparing(StaffModel::getName));
        adaptee.writeStaffFile(list);
    }

    @Override
    public void writeByRoom(List<StaffModel> list) {
        list.sort(Comparator.comparing(StaffModel::getRoom));
        adaptee.writeStaffFile(list);
    }

    @Override
    public void addNewStaff(List<StaffModel> list) {
        adaptee2.addingStaff(list);
        adaptee.writeStaffFile(list);
    }

    @Override
    public void removeStaffByName(List<StaffModel> list) {
        adaptee2.removeStaff(list);
        adaptee.writeStaffFile(list);
    }
}
