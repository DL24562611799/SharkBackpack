package sharks.customization.sharkbackpack.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public abstract class PlayerEvents {

    public static class Release extends BaseEvent {

        private static final HandlerList handlers = new HandlerList();

        public Release(Player who) {
            super(who);
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
