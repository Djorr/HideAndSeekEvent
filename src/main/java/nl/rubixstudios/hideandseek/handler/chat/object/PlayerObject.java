package nl.rubixstudios.hideandseek.handler.chat.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.hideandseek.handler.chat.ChatType;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerObject {

    private final UUID playerId;
    @Setter private ChatType chatType;

    public PlayerObject(UUID playerId) {
        this.playerId = playerId;
    }
}
