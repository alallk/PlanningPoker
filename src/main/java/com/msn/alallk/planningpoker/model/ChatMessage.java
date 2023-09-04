package com.msn.alallk.planningpoker.model;

import com.msn.alallk.planningpoker.enums.MessageType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;

}
