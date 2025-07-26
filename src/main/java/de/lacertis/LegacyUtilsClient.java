package de.lacertis;

import de.lacertis.client.config.LegacyModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Locale;

public class LegacyUtilsClient implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("LegacyUtils");
    private static final String BASE_DOMAIN = "pvplegacy.net";
    private boolean activeForServer = false;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(LegacyModConfig.class, Toml4jConfigSerializer::new);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            String host = extractHost(handler.getConnection().getAddress());
            if (isWhitelisted(host) && !activeForServer) {
                try {
                    initForServer();
                    activeForServer = true;
                } catch (Exception e) {
                    LOGGER.error("Failed to initialize LoreUtils for server", e);
                }
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (activeForServer) {
                uninitForServer();
                activeForServer = false;
            }
        });
    }

    private String extractHost(SocketAddress address) {
        if (address instanceof InetSocketAddress) {
            return ((InetSocketAddress) address).getHostString().toLowerCase(Locale.ROOT);
        }
        return "";
    }

    private boolean isWhitelisted(String host) {
        return host.equals(BASE_DOMAIN) || host.endsWith("." + BASE_DOMAIN);
    }

    private void initForServer() {
        LOGGER.info("Initializing LegacyUtils for server...");

    }

    private void uninitForServer() {
        LOGGER.info("Uninitializing LegacyUtils for server...");

    }

}
