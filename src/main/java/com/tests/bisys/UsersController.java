package com.tests.bisys;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UsersController {
	
	private static final Logger LOGGER = Logger.getLogger(UsersController.class.getName());
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalError extends RuntimeException {
		
		InternalError(String message) {
			super(message);
		}
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFound extends RuntimeException {
		
		NotFound(String message) {
			super(message);
		}
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public class NoContent extends RuntimeException {
	}

    private final AtomicLong idFactory = new AtomicLong(0);
    private final Map<Long, User> database = new ConcurrentHashMap<Long, User>();
    
    private long getNextId() {
    	
    	long id = idFactory.updateAndGet(x -> x >= 0 ? x + 1 : x);   	
    	if (id <= 0) {
    		
    		final String errorMessage = "Server is out of valid IDs.";
    		
    		LOGGER.warning(errorMessage);
    		throw new InternalError(errorMessage);
    	}
    	
    	return id;
    }

    @PostMapping
    public User createUser(
    		@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters.")
    		@RequestParam(value="name", required = true) String name,
    		
    		@PositiveOrZero(message = "Age must be a positive integer or zero.")
    		@RequestParam(value="age", required = true) int age,
    		
    		@Positive(message = "Height must be a positive number.")
    		@RequestParam(value="height", required = true) double height,
    		
    		@RequestParam(value="sex", required = true) boolean sex,
    		
    		@RequestParam(value="favcolor", required = false) List<
    			@Size(min = 3, max = 16, message = "Color must be between 3 and 16 characters.") 
    			String> favouriteColors) {
    	
    	long id = getNextId();
    	
    	User instance = new User(id, name, age, height, sex, favouriteColors);
    	
    	database.put(id, instance);
    	
    	LOGGER.info(String.format("New user has been created, id: %d, name: %s.", id, name));
    	
        return instance;
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
    	
    	User user = database.get(id);
    	if (user == null) {
    		LOGGER.warning("User with id " + id + " was requested, but not found.");
    		throw new NotFound("User not found.");
    	}
    	
    	LOGGER.info("User with id " + id + " was requested and returned.");
    	
    	return user;
    }
    
    @GetMapping("/")
    public UsersWrapper getUsers() {	
    	
    	LOGGER.info("All users were returned.");
    	
    	return new UsersWrapper(database.values());
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
    	
    	User user = database.remove(id);
    	if (user == null) {
    		LOGGER.warning("User with id " + id + " was requested to be removed, but not found.");
    		throw new NotFound("User not found.");
    	}
    	
    	LOGGER.info("User with id " + id + " was removed.");
    	
    	throw new NoContent();
    }
}