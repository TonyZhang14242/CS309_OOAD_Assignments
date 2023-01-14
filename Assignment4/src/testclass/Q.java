package testclass;

import dependency_injection.Inject;
import dependency_injection.Value;

public class Q {

    private int[] homo;


    private int[] aeroplane;


    private String[] ra2;


    private boolean[] onWork;

    @Inject
    public Q(@Value(value = "homo")int[] homo,
             @Value(value = "[33344456-55566678-777888910-JJJQQQKA-AAA22234]", delimiter = "-")int[] aeroplane,
             @Value(value = "[Lantian28,WM=HBK08,HY=moon3]")String[] ra2,
             @Value(value = "onWork", delimiter = "=")boolean[] onWork) {
        this.homo = homo;
        this.aeroplane = aeroplane;
        this.ra2 = ra2;
        this.onWork = onWork;
    }

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
