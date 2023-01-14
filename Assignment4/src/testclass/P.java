package testclass;

import dependency_injection.Inject;
import dependency_injection.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class P {
    private boolean emptyBoolean;

    private int emptyInt;

    private String emptyString;

    private boolean[] emptyBooleanArray;

    private int[] emptyIntArray;

    private String[] emptyStringArray;

    private List<Integer> emptyIntegerList;

    private Set<Boolean> emptyBooleanSet;

    private Map<Integer, Boolean> emptyMap;

    @Inject
    public P(@Value(value = "no")boolean emptyBoolean,
             @Value(value = "RA2=HBK08")int emptyInt,
             @Value(value = "")String emptyString,
             @Value(value = "[QQQKKKJ10-KKKAAA34]", delimiter = "-")boolean[] emptyBooleanArray,
             @Value(value = "[66677788899933-111222333444555]", delimiter = "-")int[] emptyIntArray,
             @Value(value = "[]")String[] emptyStringArray,
             @Value(value = "[66677788899933-111222333444555]", delimiter = "-")List<Integer> emptyIntegerList,
             @Value(value = "{RA2=HBK08, Lantian28}")Set<Boolean> emptyBooleanSet,
             @Value(value = "{3333336666669999:treu,11111122222233333:fales}")Map<Integer, Boolean> emptyMap) {
        this.emptyBoolean = emptyBoolean;
        this.emptyInt = emptyInt;
        this.emptyString = emptyString;
        this.emptyBooleanArray = emptyBooleanArray;
        this.emptyIntArray = emptyIntArray;
        this.emptyStringArray = emptyStringArray;
        this.emptyIntegerList = emptyIntegerList;
        this.emptyBooleanSet = emptyBooleanSet;
        this.emptyMap = emptyMap;
    }

    public boolean isEmptyBoolean() {
        return emptyBoolean;
    }

    public int getEmptyInt() {
        return emptyInt;
    }

    public String getEmptyString() {
        return emptyString;
    }

    public boolean[] getEmptyBooleanArray() {
        return emptyBooleanArray;
    }

    public int[] getEmptyIntArray() {
        return emptyIntArray;
    }

    public String[] getEmptyStringArray() {
        return emptyStringArray;
    }

    public List<Integer> getEmptyIntegerList() {
        return emptyIntegerList;
    }

    public Set<Boolean> getEmptyBooleanSet() {
        return emptyBooleanSet;
    }

    public Map<Integer, Boolean> getEmptyMap() {
        return emptyMap;
    }
}
