package anfegagra.sender.controller;

import anfegagra.sender.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.Queue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

	private final Queue queue;
	private final JmsTemplate jmsTemplate;

	public MessageController(Queue queue, JmsTemplate jmsTemplate) {
		this.queue = queue;
		this.jmsTemplate = jmsTemplate;
	}

	@GetMapping("/message")
	public ResponseEntity<String> publish(@RequestBody MessageDto messageDto)
		throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		jmsTemplate.convertAndSend(queue, objectMapper.writeValueAsString(messageDto));
		return new ResponseEntity(messageDto, HttpStatus.OK);
	}
}
