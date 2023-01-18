package task1;

import java.util.List;

public interface FileOperateInterfaceV1 {
    List<StaffModel> readStaffFile();

    void printStaffFile(List<StaffModel> list);

    void writeStaffFile(List<StaffModel> list);
}
