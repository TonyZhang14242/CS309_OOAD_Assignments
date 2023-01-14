package officialtests;

import dependency_injection.BeanFactory;
import dependency_injection.BeanFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testclass.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BeanFactoryOfficialTest {
    private BeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        this.beanFactory = new BeanFactoryImpl();
        beanFactory.loadInjectProperties(new File("official-inject.properties"));
        beanFactory.loadValueProperties(new File("official-value.properties"));
    }

    @Test
    public void testOfficial01() {
        A instance = beanFactory.createInstance(A.class);
        assertNotNull(instance);
    }

    @Test
    public void testOfficial02() {
        D instance = beanFactory.createInstance(D.class);
        assertEquals(10, instance.getVal());
    }

    @Test
    public void testOfficial03() {
        B instance = beanFactory.createInstance(B.class);
        assertNotNull(instance);
        assertNotNull(instance.getCDep());
        assertNotNull(instance.getDDep());
        assertEquals(instance.getDDep().getVal(),10);
    }

    @Test
    public void testOfficial04() {
        G instance = beanFactory.createInstance(G.class);
        assertNotNull(instance);
        assertNotNull(instance.getCDep());
        assertNotNull(instance.getDDep());
        assertEquals(instance.getDDep().getVal(),10);
    }

    @Test
    public void testOfficial05() {
        H instance = beanFactory.createInstance(H.class);
        assertNotNull(instance);
        assertEquals(instance.getHomo(),114514);
        assertFalse(instance.isMagic());
        assertEquals(instance.getLyric(),"never gonna give you up");
    }

    @Test
    public void testOfficial06() {
        I instance = beanFactory.createInstance(I.class);
        assertNotNull(instance);
        assertEquals(3,instance.getRogue3Software().size());
        assertEquals(1,instance.getMikuSet().size());
        assertEquals(1,instance.getSwindle().size());
        assertEquals(1,instance.getWork().size());
    }

    @Test
    public void testOfficial07() {
        E instance = beanFactory.createInstance(E.class);
        assertNotNull(instance);
        assertTrue(instance instanceof EImpl);
    }

    @Test
    public void testOfficial08() {
        F instance = beanFactory.createInstance(F.class);
        assertNotNull(instance);
        assertTrue(instance instanceof FEnhanced);
    }

    @Test
    public void testOfficial09() {
        K instance = beanFactory.createInstance(K.class);
        assertNotNull(instance);
        assertNotNull(instance.getEDep());
        assertNotNull(instance.getFDep());
        assertTrue(instance.getEDep() instanceof EImpl);
        assertTrue(instance.getFDep() instanceof FEnhanced);
    }

    @Test
    public void testOfficial10() {
        L instance = beanFactory.createInstance(L.class);
        assertNotNull(instance);
        assertNotNull(instance.getBDep());
        assertTrue(instance.isBool());
    }

    @Test
    public void testOfficial11() {
        J instance = beanFactory.createInstance(J.class);
        assertNotNull(instance);
        assertTrue(instance instanceof JImpl);
        assertEquals(34, instance.getInt());
        assertEquals(((JImpl) instance).getList().toString(),"[114514, 1919810, 33445566]");
        assertEquals(((JImpl) instance).getMap().get(44),"55");
        assertEquals(((JImpl) instance).getBoolArray().length,3);
        assertEquals(((JImpl) instance).getMap().size(),2);
    }

    @Test
    public void testOfficial12(){
        M instance = beanFactory.createInstance(M.class);
        assertTrue(instance.getJ() instanceof JImpl);
        assertEquals(3, ((JImpl)instance.getJ()).getList().size());
        assertEquals(2, instance.getList().size());
        assertEquals("notest", instance.getS());
    }

    @Test
    public void testOfficial13(){
        N instance = beanFactory.createInstance(N.class);
        assertNotNull(instance);
        assertNotNull(instance.getHomo());
        assertNotNull(instance.getAeroplane());
        assertNotNull(instance.getRa2());
        assertNotNull(instance.getOnWork());
    }

    @Test
    public void testOfficial14(){
        N instance = beanFactory.createInstance(N.class);
        assertNotNull(instance);

        assertArrayEquals(new int[]{114514, 1919810, 33445566}, instance.getHomo());
        assertArrayEquals(new int[]{33344456,55566678,777888910}, instance.getAeroplane());
        assertArrayEquals(new String[]{"Lantian28","WM=HBK08","HY=moon3"}, instance.getRa2());
        assertArrayEquals(new boolean[]{false, true,true,true,true,true,false}, instance.getOnWork());
    }

    @Test
    public void testOfficial15(){
        Q instance = beanFactory.createInstance(Q.class);
        assertNotNull(instance);
        assertNotNull(instance.getHomo());
        assertNotNull(instance.getAeroplane());
        assertNotNull(instance.getRa2());
        assertNotNull(instance.getOnWork());
    }

    @Test
    public void testOfficial16(){
        Q instance = beanFactory.createInstance(Q.class);
        assertNotNull(instance);
        assertArrayEquals(new int[]{114514, 1919810, 33445566}, instance.getHomo());
        assertArrayEquals(new int[]{33344456,55566678,777888910}, instance.getAeroplane());
        assertArrayEquals(new String[]{"Lantian28","WM=HBK08","HY=moon3"}, instance.getRa2());
        assertArrayEquals(new boolean[]{false, true,true,true,true,true,false}, instance.getOnWork());
    }


    @Test
    public void testOfficial17(){
        O instance = beanFactory.createInstance(O.class);
        assertNotNull(instance);
        assertNotNull(instance.getEmptyBooleanArray());
        assertNotNull(instance.getEmptyIntArray());
        assertNotNull(instance.getEmptyStringArray());
        assertNotNull(instance.getEmptyIntegerList());
        assertNotNull(instance.getEmptyBooleanSet());
        assertNotNull(instance.getEmptyMap());
    }

    @Test
    public void testOfficial18(){
        O instance = beanFactory.createInstance(O.class);
        assertNotNull(instance);

        assertEquals(0, instance.getEmptyInt());
        assertFalse(instance.isEmptyBoolean());
        assertTrue(instance.getEmptyString().isEmpty());
        assertTrue(instance.getEmptyMap().isEmpty());
        assertTrue(instance.getEmptyIntegerList().isEmpty());
        assertTrue(instance.getEmptyBooleanSet().isEmpty());
        assertEquals(0, instance.getEmptyBooleanArray().length);
        assertEquals(0, instance.getEmptyStringArray().length);
        assertEquals(0, instance.getEmptyIntArray().length);
    }

    @Test
    public void testOfficial19(){
        P instance = beanFactory.createInstance(P.class);
        assertNotNull(instance);
        assertNotNull(instance.getEmptyBooleanArray());
        assertNotNull(instance.getEmptyIntArray());
        assertNotNull(instance.getEmptyStringArray());
        assertNotNull(instance.getEmptyIntegerList());
        assertNotNull(instance.getEmptyBooleanSet());
        assertNotNull(instance.getEmptyMap());

        assertEquals(0, instance.getEmptyInt());
        assertFalse(instance.isEmptyBoolean());
        assertTrue(instance.getEmptyString().isEmpty());
        assertTrue(instance.getEmptyMap().isEmpty());
        assertTrue(instance.getEmptyIntegerList().isEmpty());
        assertTrue(instance.getEmptyBooleanSet().isEmpty());
        assertEquals(0, instance.getEmptyBooleanArray().length);
        assertEquals(0, instance.getEmptyStringArray().length);
        assertEquals(0, instance.getEmptyIntArray().length);
    }

    @Test
    public void testOfficial20(){
        P instance = beanFactory.createInstance(P.class);
        assertNotNull(instance);

        assertEquals(0, instance.getEmptyInt());
        assertFalse(instance.isEmptyBoolean());
        assertTrue(instance.getEmptyString().isEmpty());
        assertTrue(instance.getEmptyMap().isEmpty());
        assertTrue(instance.getEmptyIntegerList().isEmpty());
        assertTrue(instance.getEmptyBooleanSet().isEmpty());
        assertEquals(0, instance.getEmptyBooleanArray().length);
        assertEquals(0, instance.getEmptyStringArray().length);
        assertEquals(0, instance.getEmptyIntArray().length);
    }
}