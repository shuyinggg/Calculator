package datastructures;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//import
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 * This class should contain all the tests you implement to verify that
 * your 'delete' method behaves as specified.
 *
 * This test _extends_ your TestDoubleLinkedList class. This means that when
 * you run this test, not only will your tests run, all of the ones in
 * TestDoubleLinkedList will also run.
 *
 * This also means that you can use any helper methods defined within
 * TestDoubleLinkedList here. In particular, you may find using the
 * 'assertListMatches' and 'makeBasicList' helper methods to be useful.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteFunctionality extends TestDoubleLinkedList {
    @Test(timeout=SECOND)
    public void testExample() {
        // Feel free to modify or delete this dummy test.
        assertTrue(true);
     }
    
    @Test(timeout=SECOND)
    public void basicTestDeleteDecrementsSize() {
        IList<String> list = makeBasicList(); //{ a,b,c}
        int initSize = list.size();
        list.delete(1);
        
        this.assertListMatches(new String[] {"a", "c"}, list);
        assertEquals(initSize - 1, list.size());
    }
    
    @Test(timeout=SECOND)
    public void testAddAndDeleteBasic() {
        IList<String> list = makeBasicList();
        list.add("a");
        list.delete(3);
        this.assertListMatches(new String[] {"a", "b", "c"}, list);
    }
    
    @Test(timeout=SECOND)
    public void testAlternatingAddAndDelete() {
        int itr = 1000;
        
        IList<String> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < itr; i++) {
            String in = "" + i;
            list.add(in);
            assertEquals(1, list.size());
            
            String out = list.delete(0);
            assertEquals(in, out);
            assertEquals(0, list.size());
        }
    }
    
    
    @Test(timeout=SECOND)
    public void deleteOnEmptyListThrowsException() {
        IList<String> list = makeBasicList();
        list.delete(0);
        list.delete(0);
        list.delete(0);
        assertEquals(list.size(), 0);
        try {
            list.delete(0);
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            //do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testGetOutOfBoundsThrowsException() {
        IList<String> list = this.makeBasicList();
        try {
            list.delete(-1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            //do nothing
        } 
    }
    
    @Test(timeout=SECOND)
    public void testDeleteFromFrontandEnd(){
        IList<String> list = this.makeBasicList();
        list.delete(0);
        this.assertListMatches(new String[] {"b", "c"}, list);
        list.delete(1);
        this.assertListMatches(new String[] {"b"}, list);
    }
    
    
    
}
