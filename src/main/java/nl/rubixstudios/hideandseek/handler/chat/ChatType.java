package nl.rubixstudios.hideandseek.handler.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Getter
@AllArgsConstructor
public enum ChatType {

    SPECTATOR("spectator"),
    HIDERS("hider"),
    SEEKERS("seeker"),
    STAFF("staff");

    private final String identifier;

    private static final Map<String, ChatType> BY_NAME;

    public static ChatType getByName(String name) { return BY_NAME.get(name); }

    static {
        BY_NAME = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (ChatType chatType : values()) {
            BY_NAME.put(chatType.identifier, chatType);
        }
    }

}
