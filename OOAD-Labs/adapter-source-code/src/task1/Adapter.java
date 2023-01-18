package task1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter implements FileOperateInterfaceV2{

    private FileOperateInterfaceV1 adaptee;

    public Adapter(FileOperateInterfaceV1 adaptee) {
        this.adaptee = adaptee;
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
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Override
    public void writeByRoom(List<StaffModel> list) {
        list.sort(Comparator.comparing(StaffModel::getRoom));
        adaptee.writeStaffFile(list);
    }
}
