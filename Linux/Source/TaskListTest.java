//package pack1;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//public class TaskListTest {
//
//	@Test
//	public void testDefaultConstructor() {
//		//I don't know how to test this shit
//	}
//
//	@Test
//	public void testAdd() {
//		TaskList list1 = new TaskList();
//		Task task1 = new Task("Task1","Date1",true);
//		list1.add(task1);
//		assertTrue(list1.getTasks().contains(task1));
//	}
//	
//	@Test
//	public void testRemove() {
//		TaskList list1 = new TaskList();
//		Task task1 = new Task("Task1","Date1",true);
//		Task task2 = new Task("Task2","Date2",false);
//		list1.add(task1);
//		list1.add(task2);
//		list1.remove(task1);
//		assertTrue(list1.getTasks().contains(task2));
//		assertFalse(list1.getTasks().contains(task1));
//		}
//	
//	@Test
//	public void testClear() {
//		TaskList list1 = new TaskList();
//		Task task1 = new Task("Task1","Date1",true);
//		Task task2 = new Task("Task2","Date2",false);
//		list1.add(task1);
//		list1.add(task2);
//		list1.clear();
//		assertFalse(list1.getTasks().contains(task1));
//		assertFalse(list1.getTasks().contains(task2));
//	}
//	
//	@Test
//	public void testSaveLoad() {
//		TaskList list1 = new TaskList();
//		Task task1 = new Task("Task1","Date1",true);
//		Task task2 = new Task("Task2","Date2",false);
//		list1.add(task1);
//		//System.out.println(list1.getSize());
//		list1.add(task2);
//		//System.out.println(list1.getSize());
//		list1.save();
//		//System.out.println(list1.getSize());
//		String task1S = task1.toString();
//		String task2S = task2.toString();
//		list1.clear();
//		assertEquals(list1.getSize(),0);
//		assertFalse(list1.getTasks().contains(task1));
//		assertFalse(list1.getTasks().contains(task2));
//		list1.load();
//		//System.out.println(list1.getSize());
//		//System.out.println(list1.getTask(0));
//		assertTrue(list1.getTask(0).toString().equals(task1S));
//		assertTrue(list1.getTask(1).toString().equals(task2S));
//		//assertEquals(list1.getSize(),2);
//		System.out.println(list1.getSize());
//	}
//	
//}
