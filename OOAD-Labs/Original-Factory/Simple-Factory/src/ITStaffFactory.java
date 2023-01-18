public class ITStaffFactory {
    public ITStaff createITStaff(int op){
        return switch (op) {
            case 1 -> new ITManager();
            case 2 -> new Developer();
            case 3 -> new Tester();
            default -> null;
        };
    }
}
