package com.example.chat;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class ChatController {

	private final ChatRepository chatRepository;
	
	@GetMapping(value="/")
	public Mono<ServerResponse> index(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index");
	}
	
    //여러 번 응답을 받는다.
    @CrossOrigin
	@GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)//sse 프로토콜 사용
	public Flux<Chat> getMsg(@PathVariable String sender, @PathVariable String receiver) {
		System.out.println("msg 호출됨");
		return chatRepository.mFindBySender(sender, receiver)
				//.repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(1)))
				.subscribeOn(Schedulers.boundedElastic());
	}

    @CrossOrigin
	@GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Chat> findByRoomNum(@PathVariable Integer roomNum) {
		return chatRepository.mFindByRoomNum(roomNum)
				.subscribeOn(Schedulers.boundedElastic());
	}
	
    //한번만 응답을 받는다.
    @CrossOrigin //자바스크립트 요청이 가능하게 열어준다.
	@PostMapping("/chat")
	public Mono<Chat> setMsg(@RequestBody Chat chat){
		chat.setCreatedAt(LocalDateTime.now());
		return chatRepository.save(chat);
	}
}