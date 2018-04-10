package datastructures;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//import
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;


import static org.junit.Assert.assertTrue;


/**
 * This file should contain any tests that check and make sure your
 * delete method is efficient.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteStress extends TestDoubleLinkedList {
    @Test(timeout=SECOND)
    public void testExample() {
        // Feel free to modify or delete this dummy test.
        assertTrue(true);
        assertEquals(3, 3);
    }
    
    @Test(timeout=15 * SECOND)
    public void testAddAndDeleteFromEndIsEfficient() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20000; i++) {
            list.add(i);
        }
        
        for (int i = 0; i < 20000; i++){
            list.add(1);
            list.delete(20000);
        }
    }
    
    @Test(timeout=15 * SECOND)
    public void testDeleteNearEndIsEfficient() {
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 7654321;
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        
       
        for (int i = 0; i < cap - 1; i++){
            list.delete(list.size() - 2);
        }
        assertEquals(list.size(), 1);
        this.assertListMatches(new Integer[] {7654320}, list);
    }
   
    
}
