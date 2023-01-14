package testclass;

import dependency_injection.Value;

public class N {

    @Value(value = "homo")
    private int[] homo;

    @Value(value = "[33344456-55566678-777888910-JJJQQQKA-AAA22234]", delimiter = "-")
    private int[] aeroplane;

    @Value(value = "[Lantian28,WM=HBK08,HY=moon3]")
    private String[] ra2;

    @Value(value = "onWork", delimiter = "=")
    private boolean[] onWork;


    public String[] getRa2() {
        return ra2;
    }

    public int[] getAeroplane() {
        return aeroplane;
    }

    public int[] getHomo() {
        return homo;
    }

    public boolean[] getOnWork() {
        return onWork;
    }
}
