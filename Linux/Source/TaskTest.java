///**
// * 
// */
//package pack1;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
///**
// * @author Frank
// *
// */
//public class TaskTest {
//
//	@Test
//	public void testDefaultConstructor() {
//		Task task1 = new Task("Code","1/1/200",false);
//		Task task2 = new Task("Code","1/1/200",false);
//		Task task3 = task2;
//		Task task4 = new Task("Hi","2/2/3000",true);
//		assertEquals(task1.getTaskName(),"Code");
//		assertEquals(task1.getDate(),"1/1/200");
//		assertEquals(task1.isImportant(),false);
//		assertEquals(task2.getTaskName(),task1.getTaskName());
//		assertNotEquals(task1.isImportant(),task4.isImportant());
//		assertEquals(task1.getDate(),task3.getDate());
//	}
//	
//	@Test
//	public void testEquals() {
//		Task task1 = new Task("Code","1/1/200",false);
//		Task task2 = new Task("Code","1/1/200",true);
//		Task task3 = task2;
//		Task task4 = new Task("Hi","2/2/3000",true);
//		assertTrue(task2.equals(task3));
//		assertFalse(task1.equals(task2));
//		assertFalse(task3.equals(task4));
//		assertTrue(task4.equals(task4));
//	}
//	
//	@Test
//	public void testToString() {
//		Task task1 = new Task("Code","1/1/200",false);
//		assertTrue(task1.toString().equals("Code|1/1/200|false"));
//	}
//
//}
