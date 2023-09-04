package com.msn.alallk.planningpoker.config;

import com.msn.alallk.planningpoker.enums.MessageType;
import com.msn.alallk.planningpoker.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent){
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        var username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
        if(!username.isEmpty()){
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVER)
                    .sender(username)
                    .build();
            sendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
