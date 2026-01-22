package es.upm.grise.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.ExistingUserException;
import es.upm.grise.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.upm.grise.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {
	
	private SubscriptionService service;
	private User user;
	
	@BeforeEach
	public void setUp() {
		service = new SubscriptionService();
		user = new User();
	}
	
	
	@Test
	public void testAddSubscriberValidUser() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		user.setEmail("test@example.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		
		service.addSubscriber(user);
		
		assertTrue(service.getSubscribers().contains(user));
	}
	
	
	@Test
	public void testAddSubscriberNullUser() {
		assertThrows(NullUserException.class, () -> {
			service.addSubscriber(null);
		});
	}
	
	
	@Test
	public void testAddSubscriberExistingUser() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		user.setEmail("test@example.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		
		service.addSubscriber(user);
		
		assertThrows(ExistingUserException.class, () -> {
			service.addSubscriber(user);
		});
	}
	
	
	@Test
	public void testAddSubscriberLocalUserWithEmail() {
		user.setEmail("test@example.com");
		user.setDelivery(Delivery.LOCAL);
		
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			service.addSubscriber(user);
		});
	}
	
	
	@Test
	public void testAddSubscriberLocalUserWithoutEmail() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		user.setEmail(null);
		user.setDelivery(Delivery.LOCAL);
		
		service.addSubscriber(user);
		
		assertTrue(service.getSubscribers().contains(user));
	}
	
	
	@Test
	public void testSubscriptionServiceInitialState() {
		assertTrue(service.getSubscribers().isEmpty());
	}
	
}
