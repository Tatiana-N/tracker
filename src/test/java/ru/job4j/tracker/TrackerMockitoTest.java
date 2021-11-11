package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackerMockitoTest {
	private final String ln = System.lineSeparator();
	
	@Test
	public void whenFindAll() {
		Tracker tracker = new Tracker();
		StubOutput output = new StubOutput();
		ShowAllItemsAction showAllItemsAction = new ShowAllItemsAction(output);
		
		Input input = mock(Input.class);
		
		showAllItemsAction.execute(input, tracker);
		assertThat(output.toString(), is("=== Show all items ===" + ln + "Хранилище еще не содержит заявок." + ln));
		
	}
	
	@Test
	public void whenFindByName() {
		Tracker tracker = new Tracker();
		
		StubOutput output = new StubOutput();
		ShowByNameAction showByNameAction = new ShowByNameAction(output);
		Item item = new Item("Item");
		tracker.add(item);
		
		Input input = mock(Input.class);
		when(input.askStr(any(String.class))).thenReturn("Item");
		
		showByNameAction.execute(input, tracker);
		
		assertThat(output.toString(), is("=== Find items by name ===" + ln + item + ln));
		
	}
	
	@Test
	public void whenFindById() {
		Tracker tracker = new Tracker();
		
		StubOutput output = new StubOutput();
		ShowByIdAction showByIdAction = new ShowByIdAction(output);
		Item item = new Item("Item");
		tracker.add(item);
		
		Input input = mock(Input.class);
		when(input.askInt(any(String.class))).thenReturn(1);
		
		showByIdAction.execute(input, tracker);
		
		assertThat(output.toString(), is("=== Find item by id ===" + ln + item + ln));
		
	}
	
	@Test
	public void whenReplace() {
		Output out = new StubOutput();
		Tracker tracker = new Tracker();
		tracker.add(new Item("Replaced item"));
		String replacedName = "New item name";
		ReplaceAction rep = new ReplaceAction(out);
		
		Input input = mock(Input.class);
		when(input.askInt(any(String.class))).thenReturn(1);
		when(input.askStr(any(String.class))).thenReturn(replacedName);
		
		rep.execute(input, tracker);
		
		assertThat(out.toString(), is("=== Edit item ===" + ln + "Заявка изменена успешно." + ln));
		assertThat(tracker.findAll().get(0).getName(), is(replacedName));
	}
	
	@Test
	public void whenDelete() {
		Output out = new StubOutput();
		Tracker tracker = new Tracker();
		tracker.add(new Item("Delete item"));
		DeleteAction deleteAction = new DeleteAction(out);
		
		Input input = mock(Input.class);
		when(input.askInt(any(String.class))).thenReturn(1);
		deleteAction.execute(input, tracker);
		
		assertThat(out.toString(), is("=== Delete item ===" + ln + "Заявка удалена успешно." + ln));
		assertEquals(0, tracker.findAll().size());
	}
}