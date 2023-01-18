public class ITStaffFactory {
    public static ITStaff createITManager(){
        return new ITManager();
    }

    public static ITStaff createDeveloper(){
        return new Developer();
    }

    public static ITStaff createTester(){
        return new Tester();
    }


    public ITStaff createITStaff(int op){
        return switch (op) {
            case 1 -> new ITManager();
            case 2 -> new Developer();
            case 3 -> new Tester();
            default -> null;
        };
    }
}
